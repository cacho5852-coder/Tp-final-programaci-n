package excepciones;

/** Se lanza cuando los datos de un pedido son inválidos (peso, distancia, destino, código repetido...). */
public class PedidoInvalidoException extends Exception {

    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}