package app;


import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteInvalidoException;
import excepciones.ClienteNoEncontradoException;
import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;
import excepciones.PedidoNoEncontradoException;
import excepciones.RepartidorNoEncontradoException;
import excepciones.SinOperacionesException;
import excepciones.SinPedidosPendientesException;
import excepciones.VehiculoInvalidoException;
import excepciones.VehiculoNoEncontradoException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Vehiculo;

/** Menú de consola de CityLink: solo lectura por teclado y mensajes. */
public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final CityLink gestor = new CityLink();


public static void main(String[] args) {
    int opcion = 0;

    while (opcion != 10) {

        System.out.println("\n--- CITYLINK ---");
        System.out.println("1. Gestion de clientes");
        System.out.println("2. Gestion de repartidores y vehiculos");
        System.out.println("3. Registrar pedido");
        System.out.println("4. Atender pedido");
        System.out.println("5. Registrar entrega");
        System.out.println("6. Deshacer ultima operacion");
        System.out.println("7. Ordenar y listar pedidos");
        System.out.println("8. Buscar pedido");
        System.out.println("9. Intercalar pedidos");
        System.out.println("10. Salir");

        opcion = leerEntero("Ingrese una opcion: ");

        switch (opcion) {

            case 1 -> menuClientes();

            case 2 -> menuVehiculos();

            case 3 -> registrarPedido();

            case 4 -> atenderPedido();

            case 5 -> registrarEntrega();

            case 6 -> deshacerOperacion();

            case 7 -> ordenarPedidos();

            case 8 -> buscarPedido();

            case 9 -> intercalarPedidos();

            case 10 -> System.out.println("Hasta luego.");

            default -> System.out.println("Opcion incorrecta.");
        }
    }
}

    private static void menuClientes() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- Gestión de clientes ---");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Baja de cliente");
            System.out.println("3. Buscar por código");
            System.out.println("4. Buscar por nombre");
            System.out.println("5. Listar clientes (ordenados por código)");
            System.out.println("0. Volver");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> altaCliente();
                    case 2 -> bajaCliente();
                    case 3 -> System.out.println(gestor.buscarCliente(leerEntero("Código: ")));
                    case 4 -> buscarPorNombre();
                    case 5 -> listarClientes();
                    case 0 -> { }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (ClienteNoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (ClienteInvalidoException e) {
             System.out.println("Dato inválido: " + e.getMessage());
            } finally {
                System.out.println("(operación finalizada)");
            }
        }
    }

    private static void altaCliente() throws ClienteInvalidoException {
        int codigo = leerEntero("Código: ");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono: ");
        String tel = sc.nextLine();
        System.out.print("Zona: ");
        String zona = sc.nextLine();
        gestor.registrarCliente(new Cliente(codigo, nombre, tel, zona));
        System.out.println("Cliente registrado.");
    }

    private static void bajaCliente() throws ClienteNoEncontradoException {
        gestor.eliminarCliente(leerEntero("Código a eliminar: "));
        System.out.println("Cliente eliminado.");
    }

    private static void buscarPorNombre() throws ClienteNoEncontradoException {
        System.out.print("Nombre: ");
        System.out.println(gestor.buscarCliente(sc.nextLine()));
    }

    private static void listarClientes() {
        List<Cliente> lista = gestor.listarClientes();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente c : lista) {
            System.out.println(c);
        }
    }

    // ------------------------- Repartidores y vehículos -------------------------

    private static void menuVehiculos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- Gestión de repartidores y vehículos ---");
            System.out.println("1. Alta de vehículo");
            System.out.println("2. Baja de vehículo");
            System.out.println("3. Buscar vehículo por patente");
            System.out.println("4. Listar vehículos (ordenados por patente)");
            System.out.println("5. Alta de repartidor");
            System.out.println("6. Listar repartidores");
            System.out.println("0. Volver");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> altaVehiculo();
                    case 2 -> bajaVehiculo();
                    case 3 -> System.out.println(gestor.buscarVehiculo(leerTexto("Patente: ")));
                    case 4 -> listarVehiculos();
                    case 5 -> altaRepartidor();
                    case 6 -> listarRepartidores();
                    case 0 -> { }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (VehiculoNoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (VehiculoInvalidoException | IllegalArgumentException e) {
                System.out.println("Dato inválido: " + e.getMessage());
            } finally {
                System.out.println("(operación finalizada)");
            }
        }
    }

    private static void altaVehiculo() throws VehiculoInvalidoException {
        System.out.println("Tipo: 1. Bicicleta  2. Moto  3. Camioneta");
        int tipo = leerEntero("Tipo: ");
        String patente = leerTexto("Patente: ");
        Vehiculo v;
        switch (tipo) {
            case 1 -> v = new Bicicleta(patente);
            case 2 -> v = new Moto(patente);
            case 3 -> v = new Camioneta(patente);
            default -> {
                System.out.println("Tipo inválido.");
                return;
            }
        }
        gestor.registrarVehiculo(v);
        System.out.println("Vehículo registrado.");
    }

    private static void bajaVehiculo() throws VehiculoNoEncontradoException, VehiculoInvalidoException {
        gestor.eliminarVehiculo(leerTexto("Patente a eliminar: "));
        System.out.println("Vehículo eliminado.");
    }

    private static void listarVehiculos() {
        List<Vehiculo> lista = gestor.listarVehiculos();
        if (lista.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }
        for (Vehiculo v : lista) {
            System.out.println(v);
        }
    }

    private static void altaRepartidor() throws VehiculoNoEncontradoException {
        int legajo = leerEntero("Legajo: ");
        String nombre = leerTexto("Nombre: ");
        Vehiculo v = gestor.buscarVehiculo(leerTexto("Patente del vehículo: "));
        gestor.registrarRepartidor(new Repartidor(legajo, nombre, v));
        System.out.println("Repartidor registrado.");
    }

    private static void listarRepartidores() {
        List<Repartidor> lista = gestor.listarRepartidores();
        if (lista.isEmpty()) {
            System.out.println("No hay repartidores registrados.");
            return;
        }
        for (Repartidor r : lista) {
            System.out.println(r + " - vehículo: " + r.getVehiculo());
        }
    }

    // ------------------------- Pedidos y entregas -------------------------

    private static void registrarPedido() {
        try {
            int codigo = leerEntero("Código de pedido: ");
            Cliente cliente = gestor.buscarCliente(leerEntero("Código de cliente: "));
            System.out.println("Tipo: 1. Normal  2. Urgente  3. Frágil");
            int tipo = leerEntero("Tipo: ");
            String destino = leerTexto("Destino: ");
            double peso = leerDecimal("Peso (kg): ");
            double km = leerDecimal("Distancia (km): ");
            Pedido p;
            switch (tipo) {
                case 1 -> p = new PedidoNormal(codigo, cliente, destino, peso, km);
                case 2 -> p = new PedidoUrgente(codigo, cliente, destino, peso, km);
                case 3 -> p = new PedidoFragil(codigo, cliente, destino, peso, km);
                default -> {
                    System.out.println("Tipo inválido.");
                    return;
                }
            }
            gestor.registrarPedido(p);
            System.out.println("Pedido registrado (queda pendiente).");
        } catch (ClienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (PedidoInvalidoException e) {
            System.out.println("Dato inválido: " + e.getMessage());
        }
    }

    private static void atenderPedido() {
        try {
            Pedido p = gestor.verPedidoPendiente();
            System.out.println("Próximo pedido a atender: " + p);
            listarRepartidores();
            int legajo = leerEntero("Legajo del repartidor: ");
            gestor.atenderPedido(legajo);
            System.out.println("Pedido " + p.getCodigo() + " asignado al repartidor " + legajo
                    + ": ahora está " + p.getEstado() + ".");
        } catch (SinPedidosPendientesException | RepartidorNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (CapacidadExcedidaException | AlmacenamientoLlenoException | PedidoInvalidoException e) {
            System.out.println("No se pudo asignar (el pedido sigue pendiente): " + e.getMessage());
        }
    }

    private static void registrarEntrega() {
        List<Pedido> enCamino = gestor.listarPedidosEnCamino();
        if (enCamino.isEmpty()) {
            System.out.println("No hay pedidos en camino.");
            return;
        }
        System.out.println("Pedidos en camino:");
        for (Pedido p : enCamino) {
            System.out.println(p);
        }
        try {
            int legajo = leerEntero("Legajo del repartidor: ");
            int codigo = leerEntero("Código del pedido entregado: ");
            Pedido p = gestor.registrarEntrega(legajo, codigo);
            System.out.println("Entrega registrada. " + p.obtenerEstadoActual()
                    + " - importe: $" + p.calcularImporte());
        } catch (RepartidorNoEncontradoException | PedidoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (PedidoInvalidoException | EstadoInvalidoException e) {
            System.out.println("No se pudo registrar: " + e.getMessage());
        }
    }

    // ------------------------- Deshacer, ordenar, buscar e intercalar -------------------------

    private static void deshacerOperacion() {
        try {
            System.out.println(gestor.deshacerUltimaOperacion());
        } catch (SinOperacionesException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void ordenarPedidos() {
        System.out.println("Ordenar por: 1. Código  2. Peso  3. Distancia  4. Fecha y hora  5. Cliente");
        int criterio = leerEntero("Criterio: ");
        Comparator<Pedido> comp;
        switch (criterio) {
            case 1 -> comp = Comparator.comparingInt(Pedido::getCodigo);
            case 2 -> comp = Comparator.comparingDouble(Pedido::getPesoKg);
            case 3 -> comp = Comparator.comparingDouble(Pedido::getDistanciaKm);
            case 4 -> comp = Comparator.comparing(Pedido::getFechaHora);
            case 5 -> comp = Comparator.comparing(p -> p.getCliente().getNombre());
            default -> {
                System.out.println("Criterio inválido.");
                return;
            }
        }
        mostrarPedidos(gestor.listarPedidosOrdenados(comp));
    }

    private static void buscarPedido() {
        System.out.println("Buscar por: 1. Código  2. Nombre de cliente");
        int tipo = leerEntero("Opción: ");
        try {
            switch (tipo) {
                case 1 -> System.out.println(gestor.buscarPedidoBinario(leerEntero("Código: ")));
                case 2 -> mostrarPedidos(gestor.buscarPedidosPorCliente(leerTexto("Nombre: ")));
                default -> System.out.println("Opción inválida.");
            }
        } catch (PedidoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void intercalarPedidos() {
        listarRepartidores();
        try {
            int a = leerEntero("Legajo del primer repartidor: ");
            int b = leerEntero("Legajo del segundo repartidor: ");
            System.out.println("Pedidos del repartidor " + a + ":");
            mostrarPedidos(gestor.pedidosDeRepartidor(a));
            System.out.println("Pedidos del repartidor " + b + ":");
            mostrarPedidos(gestor.pedidosDeRepartidor(b));
            System.out.println("Intercalados (ordenados por código):");
            mostrarPedidos(gestor.intercalarPedidos(a, b));
        } catch (RepartidorNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarPedidos(List<Pedido> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos para mostrar.");
            return;
        }
        for (Pedido p : lista) {
            System.out.println(p);
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }
}