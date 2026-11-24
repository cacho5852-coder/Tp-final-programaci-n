package excepciones;

/** No existe un pedido con el código buscado. */
public class PedidoNoEncontradoException extends Exception {
    public PedidoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
