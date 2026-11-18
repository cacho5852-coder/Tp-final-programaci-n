package modelo;

/** Bicicleta: no lleva pedidos frágiles y no tiene tiempo extra. */
public class Bicicleta extends Vehiculo {

    public static final double CAPACIDAD_DEFECTO = 10;
    public static final double VELOCIDAD_DEFECTO = 15;
    public static final double COSTO_KM_DEFECTO = 50;

    /** Crea la bicicleta con los valores de referencia (la patente se carga después). */
    public Bicicleta() {
        super();
        setCapacidadKg(CAPACIDAD_DEFECTO);
        setVelocidadKmH(VELOCIDAD_DEFECTO);
        setCostoPorKm(COSTO_KM_DEFECTO);
    }

    /** Crea la bicicleta con los valores de referencia y la patente indicada. */
    public Bicicleta(String patente) {
        super(patente, CAPACIDAD_DEFECTO, VELOCIDAD_DEFECTO, COSTO_KM_DEFECTO);
    }

    /** Crea la bicicleta con valores propios. */
    public Bicicleta(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoBase(km); // la bici no tiene tiempo extra
    }

    /** La bici no lleva frágiles; si no es frágil, se controla el peso como siempre. */
    @Override
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido.esFragil()) {
            return false;
        }
        return super.puedeTransportar(pedido);
    }
}