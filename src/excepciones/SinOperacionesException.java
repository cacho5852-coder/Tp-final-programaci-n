package excepciones;

/** No hay ninguna operación para deshacer. */
public class SinOperacionesException extends Exception {
    public SinOperacionesException() {
        super("No hay operaciones para deshacer.");
    }
}

