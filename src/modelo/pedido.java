package modelo;

import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;

/**
 * Pedido de envío. Clase abstracta: cada tipo (normal, urgente, frágil)
 * define su propio cálculo de costo.
 */
public abstract class Pedido implements Rastreable, Facturable {

    public static final double COSTO_BASE = 1000;

    private int codigo;
    private Cliente cliente;
    private String destino;
    private double pesoKg;
    private double distanciaKm;
    private LocalDateTime fechaHora;
    private EstadoPedido estado;
    private Vehiculo vehiculoAsignado; // null hasta que se asigna

    /** Constructor vacío. */
    public Pedido() {
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
    }

    /** Constructor completo: valida los datos con los setters. */
    public Pedido(int codigo, Cliente cliente, String destino,
                  double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        this();
        setCodigo(codigo);
        setCliente(cliente);
        setDestino(destino);
        setPesoKg(pesoKg);
        setDistanciaKm(distanciaKm);
    }

    /** Costo del envío para el vehículo indicado. */
    public abstract double calcularCosto(Vehiculo vehiculo);

    /** Costo base + distancia por costo/km del vehículo (sin recargos). */
    protected double costoSinRecargo(Vehiculo vehiculo) {
        return COSTO_BASE + distanciaKm * vehiculo.getCostoPorKm();
    }

    /**
     * Cambia el estado validando la transición.
     * PENDIENTE -> EN_CAMINO -> ENTREGADO; CANCELADO solo desde PENDIENTE.
     * @throws EstadoInvalidoException si la transición no es válida
     */
    public void cambiarEstado(EstadoPedido nuevo) {
        boolean valida = (estado == EstadoPedido.PENDIENTE && nuevo == EstadoPedido.EN_CAMINO)
                || (estado == EstadoPedido.EN_CAMINO && nuevo == EstadoPedido.ENTREGADO)
                || (estado == EstadoPedido.PENDIENTE && nuevo == EstadoPedido.CANCELADO);
        if (!valida) {
            throw new EstadoInvalidoException(
                "Transición inválida: " + estado + " -> " + nuevo);
        }
        this.estado = nuevo;
    }

    // ---- Rastreable ----
    @Override
    public String obtenerEstadoActual() {
        return "Pedido " + codigo + ": " + estado;
    }

    // ---- Facturable ----
    /** Importe del pedido según el vehículo asignado (0 si aún no tiene). */
    @Override
    public double calcularImporte() {
        if (vehiculoAsignado == null) {
            return 0;
        }
        return calcularCosto(vehiculoAsignado);
    }

    // ---- Getters y setters ----
    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) throws PedidoInvalidoException {
        if (codigo <= 0) {
            throw new PedidoInvalidoException("El código debe ser mayor que cero.");
        }
        this.codigo = codigo;
    }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) throws PedidoInvalidoException {
        if (cliente == null) {
            throw new PedidoInvalidoException("El cliente no puede ser nulo.");
        }
        this.cliente = cliente;
    }

    public String getDestino() { return destino; }

    public void setDestino(String destino) throws PedidoInvalidoException {
        if (destino == null || destino.trim().isEmpty()) {
            throw new PedidoInvalidoException("El destino no puede estar vacío.");
        }
        this.destino = destino.trim();
    }

    public double getPesoKg() { return pesoKg; }

    public void setPesoKg(double pesoKg) throws PedidoInvalidoException {
        if (pesoKg <= 0) {
            throw new PedidoInvalidoException("El peso debe ser mayor que cero.");
        }
        this.pesoKg = pesoKg;
    }

    public double getDistanciaKm() { return distanciaKm; }

    public void setDistanciaKm(double distanciaKm) throws PedidoInvalidoException {
        if (distanciaKm <= 0) {
            throw new PedidoInvalidoException("La distancia debe ser mayor que cero.");
        }
        this.distanciaKm = distanciaKm;
    }

    public LocalDateTime getFechaHora() { return fechaHora; }

    public void setFechaHora(LocalDateTime fechaHora) throws PedidoInvalidoException {
    if (fechaHora == null) {
        throw new PedidoInvalidoException("La fecha y hora no pueden ser nulas.");
    }
    this.fechaHora = fechaHora;
    }

    public EstadoPedido getEstado() { return estado; }

    public Vehiculo getVehiculoAsignado() { return vehiculoAsignado; }

    public void setVehiculoAsignado(Vehiculo vehiculoAsignado) {
        this.vehiculoAsignado = vehiculoAsignado;
    }

    @Override
    public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    return codigo == ((Pedido) obj).codigo;
}

    @Override
    public int hashCode() {
    return Integer.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "Pedido{codigo=" + codigo
                + ", cliente=" + (cliente == null ? "-" : cliente.getNombre())
                + ", destino=" + destino
                + ", pesoKg=" + pesoKg
                + ", distanciaKm=" + distanciaKm
                + ", fechaHora=" + fechaHora
                + ", estado=" + estado + "}";
    }
}

