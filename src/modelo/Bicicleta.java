package modelo;

/** Bicicleta: 10 kg, 15 km/h, $50/km. No transporta pedidos frágiles. Sin tiempo extra. */
public class Bicicleta extends Vehiculo {

public static final double CAPACIDAD_DEFECTO = 10;
public static final double VELOCIDAD_DEFECTO = 15;
public static final double COSTO_KM_DEFECTO = 50;

    /** Constructor vacío: usa valores por defecto y patente a completar con setPatente. */
    public Bicicleta() {
        super();
        setCapacidadKg(CAPACIDAD_DEFECTO);
        setVelocidadKmH(VELOCIDAD_DEFECTO);
        setCostoPorKm(COSTO_KM_DEFECTO);
    }

    /** Constructor con valores de referencia. @param patente identificador de la bicicleta */
    public Bicicleta(String patente) {
        super(patente, CAPACIDAD_DEFECTO, VELOCIDAD_DEFECTO, COSTO_KM_DEFECTO);
    }

    /** Constructor completo, para ajustar los valores. */
    public Bicicleta(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoBase(km); // sin tiempo extra
    }

    /** Rechaza pedidos frágiles y, además, aplica la regla de capacidad. */
    @Override
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido.esFragil()) {
            return false;
        }
        return super.puedeTransportar(pedido);
    }
}

    
    

        