package excepciones;

/**
 * Excepcion que se lanza cuando no hay pedidos pendientes
 * para procesar en el sistema.
 */
public class SinPedidosPendientesException extends Exception {

    public SinPedidosPendientesException() {
        super("No hay pedidos pendientes para procesar.");
    }

    public SinPedidosPendientesException(String mensaje) {
        super(mensaje);
    }
}