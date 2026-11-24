package excepciones;

/** No existe un vehículo con la patente buscada. */
public class VehiculoNoEncontradoException extends Exception {
    public VehiculoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}