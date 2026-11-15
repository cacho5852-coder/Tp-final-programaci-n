package modelo;

import java.time.LocalDateTime;

public abstract class Pedido implements Rastreable, Facturable {

    private int codigo;
    private Cliente cliente;
    private String destino;
    private double pesoKg;
    private double distanciaKm;
    private LocalDateTime fechaHora;
    private EstadoPedido estado;

    // Constructor
    public Pedido(int codigo, Cliente cliente, String destino,
                  double pesoKg, double distanciaKm,
                  LocalDateTime fechaHora) {

        this.codigo = codigo;
        this.cliente = cliente;
        setDestino(destino);
        setPesoKg(pesoKg);
        setDistanciaKm(distanciaKm);
        this.fechaHora = fechaHora;
        this.estado = EstadoPedido.PENDIENTE;
    }

    // Getters
    public int getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDestino() {
        return destino;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    // Setters con validaciones
    public void setDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new PedidoInvalidoException(
                "El destino no puede estar vacío"
            );
        }

        this.destino = destino;
    }

    public void setPesoKg(double pesoKg) {
        if (pesoKg <= 0) {
            throw new PedidoInvalidoException(
                "El peso debe ser mayor a cero"
            );
        }

        this.pesoKg = pesoKg;
    }

    public void setDistanciaKm(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new PedidoInvalidoException(
                "La distancia debe ser mayor a cero"
            );
        }

        this.distanciaKm = distanciaKm;
    }

    // Cambiar estado
    public void cambiarEstado(EstadoPedido nuevoEstado) {

        if (nuevoEstado == null) {
            throw new PedidoInvalidoException(
                "El estado no puede ser nulo"
            );
        }

        switch (estado) {

            case PENDIENTE:
                if (nuevoEstado == EstadoPedido.EN_PREPARACION ||
                    nuevoEstado == EstadoPedido.CANCELADO) {
                    estado = nuevoEstado;
                    return;
                }
                break;

            case EN_PREPARACION:
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

        throw new PedidoInvalidoException(
            "No se permite cambiar de " + estado + " a " + nuevoEstado
        );
    }

    // Método abstracto
    public abstract double calcularCosto(Vehiculo vehiculo);
}