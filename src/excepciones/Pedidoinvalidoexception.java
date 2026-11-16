package excepciones;

/** Peso o distancia no positivos, destino vacío o código duplicado. */
public class PedidoInvalidoException extends Exception {
    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}