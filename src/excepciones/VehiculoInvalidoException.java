package excepciones;

/** Vehículo nulo, patente duplicada o vehículo en uso por un repartidor. */
public class VehiculoInvalidoException extends Exception {
    public VehiculoInvalidoException(String mensaje) {
        super(mensaje);
    }
}