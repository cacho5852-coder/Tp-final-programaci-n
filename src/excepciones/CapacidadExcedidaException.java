package excepciones;

/** Se lanza cuando un pedido no puede ir en el vehículo (mucho peso o frágil en bici). */
public class CapacidadExcedidaException extends Exception {

    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}