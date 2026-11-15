package modelo;

/**
 * Clase abstracta raíz de la jerarquía de vehículos de CityLink.
 * Define los datos comunes y obliga a cada subtipo a calcular su tiempo de entrega.
 */
public abstract class Vehiculo {

    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

/** Constructor vacío. Los atributos deben completarse con los setters. */
    public Vehiculo() {
   }

/**
     * Constructor completo.
     * @param patente patente del vehículo (no vacía)
     * @param capacidadKg capacidad máxima en kg (mayor que 0)
     * @param velocidadKmH velocidad promedio en km/h (mayor que 0)
     * @param costoPorKm costo por km (mayor que 0)
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public Vehiculo(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    /**
     * Calcula el tiempo estimado de entrega en minutos (incluye el tiempo extra propio del vehículo).
     * @param km distancia del pedido en km
     * @return tiempo en minutos
     */
    public abstract double calcularTiempoEntrega(double km);

/**
     * Indica si el vehículo puede transportar el pedido (el peso no supera la capacidad).
     * Las subclases pueden redefinirlo para agregar restricciones.
     * @param pedido pedido a evaluar
     * @return true si puede transportarlo
     */
    public boolean puedeTransportar(Pedido pedido) {
        return pedido.getPesoKg() <= capacidadKg;
    }

    /**
     * Tiempo de viaje sin extras: (distancia / velocidad) * 60.
     * @param km distancia en km
     * @return minutos de viaje
     */
    protected double calcularTiempoBase(double km) {
        return (km / velocidadKmH) * 60;
    }

    public String getPatente() { return patente; }

    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("La patente no puede estar vacía.");
        }
        this.patente = patente.trim().toUpperCase();
    }

    public double getCapacidadKg() { return capacidadKg; }

    public void setCapacidadKg(double capacidadKg) {
        if (capacidadKg <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero: " + capacidadKg);
        }
        this.capacidadKg = capacidadKg;
    }

    public double getVelocidadKmH() { return velocidadKmH; }

    public void setVelocidadKmH(double velocidadKmH) {
        if (velocidadKmH <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser mayor que cero: " + velocidadKmH);
        }
        this.velocidadKmH = velocidadKmH;
    }

    public double getCostoPorKm() { return costoPorKm; }

    public void setCostoPorKm(double costoPorKm) {
        if (costoPorKm <= 0) {
            throw new IllegalArgumentException("El costo por km debe ser mayor que cero: " + costoPorKm);
        }
        this.costoPorKm = costoPorKm;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [patente=" + patente + ", capacidad=" + capacidadKg
                + " kg, velocidad=" + velocidadKmH + " km/h, costo/km=$" + costoPorKm + "]";
    }
}

    