package modelo;

/** Moto: suma 5 minutos por estacionamiento. */
public class Moto extends Vehiculo {

    public static final double CAPACIDAD_DEFECTO = 30;
    public static final double VELOCIDAD_DEFECTO = 40;
    public static final double COSTO_KM_DEFECTO = 120;
    public static final double TIEMPO_EXTRA_MIN = 5;

    /** Crea la moto con los valores de referencia (la patente se carga después). */
    public Moto() {
        super();
        setCapacidadKg(CAPACIDAD_DEFECTO);
        setVelocidadKmH(VELOCIDAD_DEFECTO);
        setCostoPorKm(COSTO_KM_DEFECTO);
    }

    /** Crea la moto con los valores de referencia y la patente indicada. */
    public Moto(String patente) {
        super(patente, CAPACIDAD_DEFECTO, VELOCIDAD_DEFECTO, COSTO_KM_DEFECTO);
    }

    /** Crea la moto con valores propios. */
    public Moto(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoBase(km) + TIEMPO_EXTRA_MIN;
    }
}