package modelo;

/**
 * Clase base de los vehículos. Es abstracta porque no existe un vehículo "genérico",
 * siempre es bicicleta, moto o camioneta.
 */
public abstract class Vehiculo {

    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    /** Crea un vehículo sin datos (se completan con los setters). */
    public Vehiculo() {
    }

    /** Crea un vehículo con todos sus datos; pasan por los setters para validarlos. */
    public Vehiculo(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    /** Devuelve el tiempo de entrega en minutos. Cada vehículo lo calcula a su manera. */
    public abstract double calcularTiempoEntrega(double km);

    /** Devuelve true si el peso del pedido entra en la capacidad del vehículo. */
    public boolean puedeTransportar(Pedido pedido) {
        return pedido.getPesoKg() <= capacidadKg;
    }

    // tiempo de viaje sin el extra de cada vehículo
    protected double calcularTiempoBase(double km) {
        return (km / velocidadKmH) * 60;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("La patente no puede estar vacía.");
        }
        this.patente = patente.trim().toUpperCase();
    }

    public double getCapacidadKg() {
        return capacidadKg;
    }

    public void setCapacidadKg(double capacidadKg) {
        if (capacidadKg <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero: " + capacidadKg);
        }
        this.capacidadKg = capacidadKg;
    }

    public double getVelocidadKmH() {
        return velocidadKmH;
    }

    public void setVelocidadKmH(double velocidadKmH) {
        if (velocidadKmH <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser mayor que cero: " + velocidadKmH);
        }
        this.velocidadKmH = velocidadKmH;
    }

    public double getCostoPorKm() {
        return costoPorKm;
    }

    public void setCostoPorKm(double costoPorKm) {
        if (costoPorKm <= 0) {
            throw new IllegalArgumentException("El costo por km debe ser mayor que cero: " + costoPorKm);
        }
        this.costoPorKm = costoPorKm;
    }

    // dos vehículos son iguales si tienen la misma patente (así contains() funciona en las listas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo otro = (Vehiculo) o;
        return patente != null && patente.equals(otro.patente);
    }

    @Override
    public int hashCode() {
        return patente == null ? 0 : patente.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [patente=" + patente + ", capacidad=" + capacidadKg
                + " kg, velocidad=" + velocidadKmH + " km/h, costo/km=$" + costoPorKm + "]";
    }
}
    