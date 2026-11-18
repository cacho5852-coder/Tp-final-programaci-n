package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.PedidoInvalidoException;

/**
 * Repartidor de CityLink. Tiene un vehículo y un arreglo de pedidos asignados.
 * Implementa {@link Rastreable} (descripción de su situación) y
 * {@link Facturable} (comisión por los pedidos entregados).
 */
public class Repartidor implements Rastreable, Facturable {

    /** Cantidad máxima de pedidos que puede tener asignados. */
    public static final int MAX_PEDIDOS = 20;

    /** Porcentaje de comisión sobre el importe de los pedidos entregados. */
    public static final double COMISION = 0.10;

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private final Pedido[] pedidos = new Pedido[MAX_PEDIDOS];
    private int cantidadPedidos;

    /**
     * Crea un repartidor sin pedidos asignados.
     *
     * @param legajo   legajo (mayor a cero)
     * @param nombre   nombre (no vacío)
     * @param vehiculo vehículo del repartidor (no nulo)
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);
    }

    // Getters

    /** @return legajo del repartidor */
    public int getLegajo() {
        return legajo;
    }

    /** @return nombre del repartidor */
    public String getNombre() {
        return nombre;
    }

    /** @return vehículo del repartidor */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /** @return cantidad de pedidos asignados */
    public int getCantidadPedidos() {
        return cantidadPedidos;
    }

    /**
     * Devuelve un pedido asignado por su posición.
     *
     * @param indice posición entre 0 y {@code getCantidadPedidos() - 1}
     * @return el pedido en esa posición
     * @throws IndexOutOfBoundsException si el índice no es válido
     */
    public Pedido getPedido(int indice) {
        if (indice < 0 || indice >= cantidadPedidos) {
            throw new IndexOutOfBoundsException("Índice de pedido inválido: " + indice);
        }
        return pedidos[indice];
    }

    // Setters con validaciones

    /** @throws IllegalArgumentException si el legajo no es positivo */
    public final void setLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser mayor a cero");
        }
        this.legajo = legajo;
    }

    /** @throws IllegalArgumentException si el nombre es nulo o está vacío */
    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    /** @throws IllegalArgumentException si el vehículo es nulo */
    public final void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo");
        }
        this.vehiculo = vehiculo;
    }

    /**
     * Asigna un pedido usando el vehículo del repartidor.
     *
     * @param pedido pedido a asignar
     * @throws CapacidadExcedidaException    si el vehículo no puede transportarlo
     * @throws AlmacenamientoLlenoException  si ya tiene el máximo de pedidos
     * @throws PedidoInvalidoException       si el pedido es nulo
     */
    public void asignarPedido(Pedido pedido)
            throws CapacidadExcedidaException, AlmacenamientoLlenoException,
                   PedidoInvalidoException {
        asignarPedido(pedido, vehiculo);
    }

    /**
     * Asigna un pedido usando un vehículo indicado (sobrecarga).
     *
     * @param pedido   pedido a asignar
     * @param vehiculo vehículo con el que se verifica y se entrega el pedido
     * @throws CapacidadExcedidaException    si el vehículo no puede transportarlo
     * @throws AlmacenamientoLlenoException  si ya tiene el máximo de pedidos
     * @throws PedidoInvalidoException       si el pedido o el vehículo son nulos
     */
    public void asignarPedido(Pedido pedido, Vehiculo vehiculo)
            throws CapacidadExcedidaException, AlmacenamientoLlenoException,
                   PedidoInvalidoException {
        if (pedido == null) {
            throw new PedidoInvalidoException("El pedido no puede ser nulo");
        }
        if (vehiculo == null) {
            throw new PedidoInvalidoException("El vehículo no puede ser nulo");
        }
        if (cantidadPedidos == pedidos.length) {
            throw new AlmacenamientoLlenoException(
                "El repartidor " + legajo + " ya tiene el máximo de pedidos asignados"
            );
        }
        if (!vehiculo.puedeTransportar(pedido)) {
            throw new CapacidadExcedidaException(
                "El vehículo no puede transportar el pedido " + pedido.getCodigo()
            );
        }

        pedido.asignarVehiculo(vehiculo);
        pedidos[cantidadPedidos++] = pedido;
    }

    /**
     * Registra la entrega de un pedido asignado, pasándolo a ENTREGADO.
     *
     * @param pedido pedido entregado
     * @throws PedidoInvalidoException si el pedido no está asignado a este repartidor
     * @throws excepciones.EstadoInvalidoException si el pedido no estaba EN_CAMINO
     */
    public void registrarEntrega(Pedido pedido) throws PedidoInvalidoException {
        if (pedido == null || !tienePedido(pedido)) {
            throw new PedidoInvalidoException(
                "El pedido no está asignado al repartidor " + legajo
            );
        }
        pedido.cambiarEstado(EstadoPedido.ENTREGADO);
    }

    private boolean tienePedido(Pedido pedido) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i] == pedido) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String obtenerEstadoActual() {
        return "Legajo " + legajo + ": " + cantidadPedidos + " pedidos a cargo";
    }

    /**
     * Comisión del repartidor: {@value #COMISION} del importe de los pedidos
     * que ya entregó.
     *
     * @return comisión total
     */
    @Override
    public double calcularImporte() {
        double total = 0;
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getEstado() == EstadoPedido.ENTREGADO) {
                total += pedidos[i].calcularImporte();
            }
        }
        return total * COMISION;
    }

    @Override
    public String toString() {
        return "Repartidor{legajo=" + legajo +
               ", nombre='" + nombre + '\'' +
               ", pedidos=" + cantidadPedidos + '}';
    }
}

