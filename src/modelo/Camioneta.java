package modelo;

/** Camioneta: 500 kg, 30 km/h, $300/km. 10 minutos extra por carga y descarga. */
public class Camioneta extends Vehiculo {

    public static final double CAPACIDAD_DEFECTO = 500;
    public static final double VELOCIDAD_DEFECTO = 30;
    public static final double COSTO_KM_DEFECTO = 300;
    public static final double TIEMPO_EXTRA_MIN = 10;

    /** Constructor vacío: usa valores por defecto y patente a completar con setPatente. */
    public Camioneta() {
        super();
        setCapacidadKg(CAPACIDAD_DEFECTO);
        setVelocidadKmH(VELOCIDAD_DEFECTO);
        setCostoPorKm(COSTO_KM_DEFECTO);
    }

    /** Constructor con valores de referencia. */
    public Camioneta(String patente) {
        super(patente, CAPACIDAD_DEFECTO, VELOCIDAD_DEFECTO, COSTO_KM_DEFECTO);
    }

    /** Constructor completo, para ajustar los valores. */
    public Camioneta(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoBase(km) + TIEMPO_EXTRA_MIN;
    }
}
