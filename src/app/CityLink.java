package app;

import algoritmos.Busqueda;
import algoritmos.Ordenamiento;
import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteInvalidoException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import excepciones.PedidoNoEncontradoException;
import excepciones.RepartidorNoEncontradoException;
import excepciones.SinOperacionesException;
import excepciones.SinPedidosPendientesException;
import excepciones.VehiculoInvalidoException;
import excepciones.VehiculoNoEncontradoException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Cliente;
import modelo.EstadoPedido;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Vehiculo;

/**
 * El gestor de CityLink.
 * Guarda clientes, pedidos y repartidores en árboles ordenados por su clave,
 * los vehículos ordenados por patente y los pedidos sin atender en una cola (FIFO).
 */
public class CityLink {

    private final TreeMap<Integer, Cliente> clientes = new TreeMap<>();
    private final TreeMap<String, Vehiculo> vehiculos = new TreeMap<>();
    private final TreeMap<Integer, Repartidor> repartidores = new TreeMap<>();
    private final TreeMap<Integer, Pedido> pedidos = new TreeMap<>();
     private final Deque<Pedido> pendientes = new LinkedList<>(); // pedidos sin atender, por orden de llegada
    private final Deque<Operacion> historial = new ArrayDeque<>(); // pila para deshacer (la última queda arriba)

    /** Operaciones que se pueden deshacer. */
    private enum Tipo { REGISTRO_PEDIDO, ATENCION_PEDIDO, ENTREGA_PEDIDO }

    /** Registro de una operación hecha, con lo necesario para deshacerla. */
    private static class Operacion {
        final Tipo tipo;
        final Pedido pedido;
        final Repartidor repartidor; // null en el registro de pedido

        Operacion(Tipo tipo, Pedido pedido, Repartidor repartidor) {
            this.tipo = tipo;
            this.pedido = pedido;
            this.repartidor = repartidor;
        }
    }

    /**
     * Constructor: inicia el gestor y carga clientes de prueba.
     */
    public CityLink() {
        try {
            cargarDatosDePrueba();
        } catch (ClienteInvalidoException | VehiculoInvalidoException | PedidoInvalidoException e) {
            throw new IllegalStateException(
                "Datos de prueba inválidos: " + e.getMessage(), e
            );
        }
    }

    /**
     * Agrega un cliente nuevo.
     *
     * @throws ClienteInvalidoException si el cliente es nulo
     * o ya existe otro con ese código.
     */
    public void registrarCliente(Cliente c) throws ClienteInvalidoException {
        if (c == null) {
            throw new ClienteInvalidoException("El cliente no puede ser nulo.");
        }

        if (clientes.containsKey(c.getCodigo())) {
            throw new ClienteInvalidoException(
                "Ya existe un cliente con código " + c.getCodigo()
            );
        }

        clientes.put(c.getCodigo(), c);
    }

    /**
     * Busca un cliente por su código.
     *
     * @throws ClienteNoEncontradoException si no existe ese código.
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        Cliente c = clientes.get(codigo);

        if (c == null) {
            throw new ClienteNoEncontradoException(
                "No existe un cliente con código " + codigo
            );
        }

        return c;
    }

    /**
     * Busca un cliente por su nombre recorriendo los clientes.
     *
     * @throws ClienteNoEncontradoException si no existe ese nombre.
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        Cliente c = Busqueda.buscarClientePorNombre(clientes.values(), nombre);

        if (c == null) {
            throw new ClienteNoEncontradoException(
                "No existe un cliente llamado " + nombre
            );
        }

        return c;
    }

    /**
     * Elimina un cliente por su código.
     *
     * @throws ClienteNoEncontradoException si no existe ese código.
     */
    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {
        if (clientes.remove(codigo) == null) {
            throw new ClienteNoEncontradoException(
                "No existe un cliente con código " + codigo
            );
        }
    }

    /**
     * Devuelve todos los clientes ordenados por código.
     */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }

    /**
     * Agrega un vehículo nuevo.
     *
     * @throws VehiculoInvalidoException si el vehículo es nulo o ya existe esa patente.
     */
    public void registrarVehiculo(Vehiculo v) throws VehiculoInvalidoException {
        if (v == null) {
            throw new VehiculoInvalidoException("El vehículo no puede ser nulo.");
        }

        if (vehiculos.containsKey(v.getPatente())) {
            throw new VehiculoInvalidoException(
                "Ya existe un vehículo con patente " + v.getPatente()
            );
        }

        vehiculos.put(v.getPatente(), v);
    }

    /**
     * Busca un vehículo por su patente (sin importar mayúsculas ni espacios).
     *
     * @throws VehiculoNoEncontradoException si no existe esa patente.
     */
    public Vehiculo buscarVehiculo(String patente) throws VehiculoNoEncontradoException {
        Vehiculo v = patente == null ? null : vehiculos.get(patente.trim().toUpperCase());

        if (v == null) {
            throw new VehiculoNoEncontradoException(
                "No existe un vehículo con patente " + patente
            );
        }

        return v;
    }

    /**
     * Elimina un vehículo por su patente.
     *
     * @throws VehiculoNoEncontradoException si no existe esa patente.
     * @throws VehiculoInvalidoException     si lo está usando algún repartidor.
     */
    public void eliminarVehiculo(String patente)
            throws VehiculoNoEncontradoException, VehiculoInvalidoException {
        Vehiculo v = buscarVehiculo(patente);

        for (Repartidor r : repartidores.values()) {
            if (r.getVehiculo().equals(v)) {
                throw new VehiculoInvalidoException(
                    "El vehículo " + v.getPatente() + " lo usa el repartidor " + r.getLegajo()
                );
            }
        }

        vehiculos.remove(v.getPatente());
    }

    /** Devuelve todos los vehículos ordenados por patente. */
    public List<Vehiculo> listarVehiculos() {
        return new ArrayList<>(vehiculos.values());
    }

    // ------------------------- Repartidores -------------------------

    /**
     * Agrega un repartidor nuevo.
     *
     * @throws IllegalArgumentException si es nulo o ya existe ese legajo.
     */
    public void registrarRepartidor(Repartidor r) {
        if (r == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }

        if (repartidores.containsKey(r.getLegajo())) {
            throw new IllegalArgumentException(
                "Ya existe un repartidor con legajo " + r.getLegajo()
            );
        }

        repartidores.put(r.getLegajo(), r);
    }

    /**
     * Busca un repartidor por su legajo.
     *
     * @throws RepartidorNoEncontradoException si no existe ese legajo.
     */
    public Repartidor buscarRepartidor(int legajo) throws RepartidorNoEncontradoException {
        Repartidor r = repartidores.get(legajo);

        if (r == null) {
            throw new RepartidorNoEncontradoException(
                "No existe un repartidor con legajo " + legajo
            );
        }

        return r;
    }

    /** Devuelve todos los repartidores ordenados por legajo. */
    public List<Repartidor> listarRepartidores() {
        return new ArrayList<>(repartidores.values());
    }

    // ------------------------- Pedidos y entregas -------------------------

    /**
     * Registra un pedido nuevo: queda PENDIENTE y se pone al final de la cola.
     *
     * @throws PedidoInvalidoException si el pedido es nulo o ya existe ese código.
     */
    public void registrarPedido(Pedido p) throws PedidoInvalidoException {
        if (p == null) {
            throw new PedidoInvalidoException("El pedido no puede ser nulo.");
        }

        if (pedidos.containsKey(p.getCodigo())) {
            throw new PedidoInvalidoException(
                "Ya existe un pedido con código " + p.getCodigo()
            );
        }

        pedidos.put(p.getCodigo(), p);
        pendientes.add(p);
                pedidos.put(p.getCodigo(), p);
        pendientes.add(p);
        historial.push(new Operacion(Tipo.REGISTRO_PEDIDO, p, null));
    }

    /**
     * Busca un pedido por su código.
     *
     * @throws PedidoNoEncontradoException si no existe ese código.
     */
    public Pedido buscarPedido(int codigo) throws PedidoNoEncontradoException {
        Pedido p = pedidos.get(codigo);

        if (p == null) {
            throw new PedidoNoEncontradoException(
                "No existe un pedido con código " + codigo
            );
        }

        return p;
    }

    /** Devuelve todos los pedidos ordenados por código. */
    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos.values());
    }

    /** Devuelve los pedidos EN_CAMINO (los que están esperando su entrega). */
    public List<Pedido> listarPedidosEnCamino() {
        List<Pedido> lista = new ArrayList<>();
        for (Pedido p : pedidos.values()) {
            if (p.getEstado() == EstadoPedido.EN_CAMINO) {
                lista.add(p);
            }
        }
        return lista;
    }

    /**
     * Devuelve el próximo pedido a atender (el más antiguo) sin sacarlo de la cola.
     *
     * @throws SinPedidosPendientesException si no hay pedidos pendientes.
     */
    public Pedido verPedidoPendiente() throws SinPedidosPendientesException {
        Pedido p = pendientes.peek();

        if (p == null) {
            throw new SinPedidosPendientesException();
        }

        return p;
    }

    /**
     * Atiende el próximo pedido pendiente: se lo asigna al repartidor y lo pasa a EN_CAMINO.
     * Si no se puede asignar, el pedido sigue primero en la cola.
     *
     * @return el pedido atendido
     * @throws SinPedidosPendientesException   si no hay pedidos pendientes.
     * @throws RepartidorNoEncontradoException si no existe ese legajo.
     * @throws CapacidadExcedidaException      si el vehículo no puede llevar el pedido.
     * @throws AlmacenamientoLlenoException    si el repartidor ya tiene el máximo de pedidos.
     * @throws PedidoInvalidoException         si el pedido no es válido para asignar.
     */
    public Pedido atenderPedido(int legajo)
            throws SinPedidosPendientesException, RepartidorNoEncontradoException,
                   CapacidadExcedidaException, AlmacenamientoLlenoException,
                   PedidoInvalidoException {
        Pedido p = verPedidoPendiente();
        Repartidor r = buscarRepartidor(legajo);

        r.asignarPedido(p);
        p.cambiarEstado(EstadoPedido.EN_CAMINO);
        pendientes.poll();
                pendientes.poll();
        historial.push(new Operacion(Tipo.ATENCION_PEDIDO, p, r));
        return p;
    }

    /**
     * Registra la entrega de un pedido que lleva un repartidor (pasa a ENTREGADO).
     *
     * @return el pedido entregado
     * @throws RepartidorNoEncontradoException si no existe ese legajo.
     * @throws PedidoNoEncontradoException     si no existe ese código de pedido.
     * @throws PedidoInvalidoException         si el pedido no es de ese repartidor.
     * @throws excepciones.EstadoInvalidoException si el pedido no estaba EN_CAMINO.
     */
    public Pedido registrarEntrega(int legajo, int codigoPedido)
            throws RepartidorNoEncontradoException, PedidoNoEncontradoException,
                   PedidoInvalidoException {
        Repartidor r = buscarRepartidor(legajo);
        Pedido p = buscarPedido(codigoPedido);

               r.registrarEntrega(p);
        historial.push(new Operacion(Tipo.ENTREGA_PEDIDO, p, r));
        return p;
    }

    /**
     * Deshace la última operación sobre pedidos (registro, atención o entrega).
     *
     * @return una descripción de lo que se deshizo
     * @throws SinOperacionesException si no hay nada para deshacer.
     */
    public String deshacerUltimaOperacion() throws SinOperacionesException {
        if (historial.isEmpty()) {
            throw new SinOperacionesException();
        }

        Operacion op = historial.pop();
        Pedido p = op.pedido;

        switch (op.tipo) {
            case REGISTRO_PEDIDO -> {
                pedidos.remove(p.getCodigo());
                pendientes.remove(p);
                return "Se deshizo el registro del pedido " + p.getCodigo() + ".";
            }
            case ATENCION_PEDIDO -> {
                op.repartidor.quitarUltimoPedido();
                p.setVehiculoAsignado(null);
                p.restaurarEstado(EstadoPedido.PENDIENTE);
                pendientes.addFirst(p); // vuelve a ser el próximo a atender
                return "Se deshizo la atención del pedido " + p.getCodigo()
                        + ": vuelve a estar PENDIENTE.";
            }
            default -> {
                p.restaurarEstado(EstadoPedido.EN_CAMINO);
                return "Se deshizo la entrega del pedido " + p.getCodigo()
                        + ": vuelve a estar EN_CAMINO.";
            }
        }
    }

    /** Devuelve los pedidos ordenados de menor a mayor según el criterio. */
    public List<Pedido> listarPedidosOrdenados(Comparator<Pedido> criterio) {
        List<Pedido> lista = listarPedidos();
        Ordenamiento.ordenar(lista, criterio);
        return lista;
    }

    /**
     * Busca un pedido por código con búsqueda binaria
     * (los pedidos ya se guardan ordenados por código).
     *
     * @throws PedidoNoEncontradoException si no existe ese código.
     */
    public Pedido buscarPedidoBinario(int codigo) throws PedidoNoEncontradoException {
        Pedido p = Busqueda.buscarPedidoPorCodigo(listarPedidos(), codigo);

        if (p == null) {
            throw new PedidoNoEncontradoException("No existe un pedido con código " + codigo);
        }

        return p;
    }

    /** Devuelve los pedidos de un cliente (por nombre, sin importar mayúsculas). */
    public List<Pedido> buscarPedidosPorCliente(String nombre) {
        List<Pedido> lista = new ArrayList<>();
        String buscado = nombre == null ? "" : nombre.trim();
        for (Pedido p : pedidos.values()) {
            if (p.getCliente().getNombre().equalsIgnoreCase(buscado)) {
                lista.add(p);
            }
        }
        return lista;
    }

    /** Devuelve los pedidos asignados a un repartidor, ordenados por código. */
    public List<Pedido> pedidosDeRepartidor(int legajo) throws RepartidorNoEncontradoException {
        Repartidor r = buscarRepartidor(legajo);
        List<Pedido> lista = new ArrayList<>();
        for (int i = 0; i < r.getCantidadPedidos(); i++) {
            lista.add(r.getPedido(i));
        }
        Ordenamiento.ordenar(lista, Comparator.comparingInt(Pedido::getCodigo));
        return lista;
    }

    /**
     * Intercala los pedidos de dos repartidores en una sola lista ordenada por código.
     *
     * @throws RepartidorNoEncontradoException si alguno de los legajos no existe.
     */
    public List<Pedido> intercalarPedidos(int legajoA, int legajoB)
            throws RepartidorNoEncontradoException {
        return Ordenamiento.intercalar(
                pedidosDeRepartidor(legajoA),
                pedidosDeRepartidor(legajoB),
                Comparator.comparingInt(Pedido::getCodigo));
    }
    

    /**
     * Carga datos de prueba para iniciar el programa.
     */
    private void cargarDatosDePrueba()
            throws ClienteInvalidoException, VehiculoInvalidoException, PedidoInvalidoException {
        registrarCliente(new Cliente(1, "Ana Pérez", "2657-111111", "Centro"));
        registrarCliente(new Cliente(2, "Luis Gómez", "2657-222222", "Norte"));
        registrarCliente(new Cliente(3, "María Díaz", "2657-333333", "Sur"));
        registrarCliente(new Cliente(4, "Carlos Ruiz", "2657-444444", "Este"));
        registrarCliente(new Cliente(5, "Sofía Luna", "2657-555555", "Oeste"));

        Vehiculo bici = new Bicicleta("BICI01");
        Vehiculo moto = new Moto("MOT123");
        Vehiculo camioneta = new Camioneta("CAM456");
        registrarVehiculo(bici);
        registrarVehiculo(moto);
        registrarVehiculo(camioneta);

        registrarRepartidor(new Repartidor(1, "Juan Torres", bici));
        registrarRepartidor(new Repartidor(2, "Laura Vega", moto));
        registrarRepartidor(new Repartidor(3, "Pedro Sosa", camioneta));

        registrarPedido(new PedidoNormal(1, buscarClienteDePrueba(1), "San Martín 120", 4, 3));
        registrarPedido(new PedidoUrgente(2, buscarClienteDePrueba(2), "Belgrano 450", 12, 6));
        registrarPedido(new PedidoFragil(3, buscarClienteDePrueba(3), "Rivadavia 88", 8, 5));
                historial.clear(); // los datos de prueba no se pueden deshacer
    }

    // atajo para los datos de prueba: el cliente siempre existe
    private Cliente buscarClienteDePrueba(int codigo) {
        return clientes.get(codigo);
    }
}