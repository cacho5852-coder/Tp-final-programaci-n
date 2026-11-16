package excepciones;

/** Transición de estado no permitida (unchecked). */
public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException(String mensaje) {
        super(mensaje);
    }
}