package excepciones;

/** No existe un repartidor con el legajo buscado. */
public class RepartidorNoEncontradoException extends Exception {
    public RepartidorNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}