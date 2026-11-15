package excepciones;

/** Checked: el peso supera la capacidad del vehículo o se asigna un pedido frágil a una bicicleta. */
public class CapacidadExcedidaException extends Exception {

    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}