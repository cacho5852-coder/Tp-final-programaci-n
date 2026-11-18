package modelo;

/** Camioneta: suma 10 minutos por carga y descarga. */
public class Camioneta extends Vehiculo {

    public static final double CAPACIDAD_DEFECTO = 500;
    public static final double VELOCIDAD_DEFECTO = 30;
    public static final double COSTO_KM_DEFECTO = 300;
    public static final double TIEMPO_EXTRA_MIN = 10;

    /** Crea la camioneta con los valores de referencia (la patente se carga después). */
    public Camioneta() {
        super();
        setCapacidadKg(CAPACIDAD_DEFECTO);
        setVelocidadKmH(VELOCIDAD_DEFECTO);
        setCostoPorKm(COSTO_KM_DEFECTO);
    }

    /** Crea la camioneta con los valores de referencia y la patente indicada. */
    public Camioneta(String patente) {
        super(patente, CAPACIDAD_DEFECTO, VELOCIDAD_DEFECTO, COSTO_KM_DEFECTO);
    }

    /** Crea la camioneta con valores propios. */
    public Camioneta(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoBase(km) + TIEMPO_EXTRA_MIN;
    }
}