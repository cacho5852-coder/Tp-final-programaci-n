package modelo;

import java.time.LocalDateTime;

import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;

/**
 * Clase base abstracta que representa un pedido de envío.
 * <p>
 * Flujo de estados permitido: PENDIENTE → EN_CAMINO → ENTREGADO.
 * Un pedido solo puede cancelarse desde PENDIENTE.
 */
public abstract class Pedido implements Rastreable, Facturable {

    /** Costo base de todo envío, en pesos. */
    protected static final double COSTO_BASE = 1000;

    private final int codigo;
    private final Cliente cliente;
    private String destino;
    private double pesoKg;
    private double distanciaKm;
    private final LocalDateTime fechaHora;
    private EstadoPedido estado;
    private Vehiculo vehiculo;

    /**
     * Crea un pedido en estado PENDIENTE.
     *
     * @param codigo      código del pedido (mayor a cero)
     * @param cliente     cliente que realiza el pedido (no nulo)
     * @param destino     destino de la entrega (no vacío)
     * @param pesoKg      peso en kilogramos (finito y mayor a cero)
     * @param distanciaKm distancia en kilómetros (finita y mayor a cero)
     * @param fechaHora   fecha y hora del pedido (no nula)
     * @throws PedidoInvalidoException si algún dato es inválido
     */
    public Pedido(int codigo, Cliente cliente, String destino,
                  double pesoKg, double distanciaKm,
                  LocalDateTime fechaHora) throws PedidoInvalidoException {

        if (codigo <= 0) {
            throw new PedidoInvalidoException("El código debe ser mayor a cero");
        }
        if (cliente == null) {
            throw new PedidoInvalidoException("El pedido debe tener un cliente");
        }
        if (fechaHora == null) {
            throw new PedidoInvalidoException("La fecha y hora no pueden ser nulas");
        }

        this.codigo = codigo;
        this.cliente = cliente;
        this.fechaHora = fechaHora;
        setDestino(destino);
        setPesoKg(pesoKg);
        setDistanciaKm(distanciaKm);
        this.estado = EstadoPedido.PENDIENTE;
    }

    /**
     * Crea un pedido en estado PENDIENTE usando la fecha y hora actuales.
     *
     * @throws PedidoInvalidoException si algún dato es inválido
     */
    public Pedido(int codigo, Cliente cliente, String destino,
                  double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        this(codigo, cliente, destino, pesoKg, distanciaKm, LocalDateTime.now());
    }

    // Getters

    /** @return código del pedido */
    public int getCodigo() {
        return codigo;
    }

    /** @return cliente del pedido */
    public Cliente getCliente() {
        return cliente;
    }

    /** @return destino de la entrega */
    public String getDestino() {
        return destino;
    }

    /** @return peso en kilogramos */
    public double getPesoKg() {
        return pesoKg;
    }

    /** @return distancia en kilómetros */
    public double getDistanciaKm() {
        return distanciaKm;
    }

    /** @return fecha y hora del pedido */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /** @return estado actual del pedido */
    public EstadoPedido getEstado() {
        return estado;
    }

    /** @return vehículo asignado, o null si todavía no tiene */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    // Setters con validaciones

    /**
     * Asigna el destino.
     *
     * @throws PedidoInvalidoException si es nulo o está vacío
     */
    public final void setDestino(String destino) throws PedidoInvalidoException {
        if (destino == null || destino.trim().isEmpty()) {
            throw new PedidoInvalidoException("El destino no puede estar vacío");
        }
        this.destino = destino.trim();
    }

    /**
     * Asigna el peso.
     *
     * @throws PedidoInvalidoException si no es finito o es menor o igual a cero
     */
    public final void setPesoKg(double pesoKg) throws PedidoInvalidoException {
        if (!Double.isFinite(pesoKg) || pesoKg <= 0) {
            throw new PedidoInvalidoException("El peso debe ser mayor a cero");
        }
        this.pesoKg = pesoKg;
    }

    /**
     * Asigna la distancia.
     *
     * @throws PedidoInvalidoException si no es finita o es menor o igual a cero
     */
    public final void setDistanciaKm(double distanciaKm) throws PedidoInvalidoException {
        if (!Double.isFinite(distanciaKm) || distanciaKm <= 0) {
            throw new PedidoInvalidoException("La distancia debe ser mayor a cero");
        }
        this.distanciaKm = distanciaKm;
    }

    /**
     * Asigna el vehículo que realizará la entrega.
     *
     * @throws PedidoInvalidoException si el vehículo es nulo
     */
    public void asignarVehiculo(Vehiculo vehiculo) throws PedidoInvalidoException {
        if (vehiculo == null) {
            throw new PedidoInvalidoException("El vehículo no puede ser nulo");
        }
        this.vehiculo = vehiculo;
    }

    /**
     * Cambia el estado respetando el flujo PENDIENTE → EN_CAMINO → ENTREGADO.
     * CANCELADO solo es posible desde PENDIENTE.
     *
     * @param nuevoEstado estado destino
     * @throws EstadoInvalidoException si la transición no está permitida
     */
    public void cambiarEstado(EstadoPedido nuevoEstado) {
        if (nuevoEstado == null) {
            throw new EstadoInvalidoException("El estado no puede ser nulo");
        }

        switch (estado) {
            case PENDIENTE:
                if (nuevoEstado == EstadoPedido.EN_CAMINO ||
                    nuevoEstado == EstadoPedido.CANCELADO) {
                    estado = nuevoEstado;
                    return;
                }
                break;

            case EN_CAMINO:
                if (nuevoEstado == EstadoPedido.ENTREGADO) {
                    estado = nuevoEstado;
                    return;
                }
                break;

            default:
                break;
        }

        throw new EstadoInvalidoException(
            "No se permite cambiar de " + estado + " a " + nuevoEstado
        );
    }

    // Implementación de las interfaces

    @Override
    public String obtenerEstadoActual() {
        return "Pedido " + codigo + ": " + estado;
    }

    @Override
    public double calcularImporte() {
        if (vehiculo == null) {
            throw new IllegalStateException(
                "El pedido " + codigo + " no tiene vehículo asignado"
            );
        }
        return calcularCosto(vehiculo);
    }

    /**
     * Costo sin recargos: base + distancia por costo por km del vehículo.
     *
     * @param vehiculo vehículo que realiza la entrega (no nulo)
     * @return costo sin recargos
     */
    protected double costoSinRecargo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }
        return COSTO_BASE + distanciaKm * vehiculo.getCostoPorKm();
    }

    /**
     * Calcula el costo del pedido según su tipo y el vehículo indicado.
     *
     * @param vehiculo vehículo que realiza la entrega
     * @return costo del pedido
     */
    public abstract double calcularCosto(Vehiculo vehiculo);

    @Override
    public String toString() {
        return "Pedido{codigo=" + codigo +
               ", cliente=" + cliente +
               ", destino='" + destino + '\'' +
               ", pesoKg=" + pesoKg +
               ", distanciaKm=" + distanciaKm +
               ", fechaHora=" + fechaHora +
               ", estado=" + estado + '}';
    }
}