package modelo;

/** Moto: 30 kg, 40 km/h, $120/km. 5 minutos extra por estacionamiento. */
public class Moto extends Vehiculo {

    public static final double CAPACIDAD_DEFECTO = 30;
    public static final double VELOCIDAD_DEFECTO = 40;
    public static final double COSTO_KM_DEFECTO = 120;
    public static final double TIEMPO_EXTRA_MIN = 5;

    /** Constructor vacío: usa valores por defecto y patente a completar con setPatente. */
    public Moto() {
        super();
        setCapacidadKg(CAPACIDAD_DEFECTO);
        setVelocidadKmH(VELOCIDAD_DEFECTO);
        setCostoPorKm(COSTO_KM_DEFECTO);
    }

    /** Constructor con valores de referencia. */
    public Moto(String patente) {
        super(patente, CAPACIDAD_DEFECTO, VELOCIDAD_DEFECTO, COSTO_KM_DEFECTO);
    }

    /** Constructor completo, para ajustar los valores. */
    public Moto(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoBase(km) + TIEMPO_EXTRA_MIN;
    }
}

    

