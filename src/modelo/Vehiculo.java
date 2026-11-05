package modelo;


public abstract class Vehiculo {

    
    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;


    public Vehiculo(String patente, double capacidadKg,
                    double velocidadKmH, double costoPorKm) {

        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    
    public String getPatente() {
        return patente;
    }

    
    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La patente no puede estar vacia");
        }
        this.patente = patente.trim();
    }

    
    
    public double getCapacidadKg() {
        return capacidadKg;
    }

    
    public void setCapacidadKg(double capacidadKg) {
        if (!Double.isFinite(capacidadKg) || capacidadKg <= 0) {
            throw new IllegalArgumentException(
                "La capacidad debe ser mayor a cero");
        }
        this.capacidadKg = capacidadKg;
    }

    
    public double getVelocidadKmH() {
        return velocidadKmH;
    }

    
    public void setVelocidadKmH(double velocidadKmH) {
        if (!Double.isFinite(velocidadKmH) || velocidadKmH <= 0) {
            throw new IllegalArgumentException(
                "La velocidad debe ser mayor a cero");
        }
        this.velocidadKmH = velocidadKmH;
    }

    
    public double getCostoPorKm() {
        return costoPorKm;
    }

    
    public void setCostoPorKm(double costoPorKm) {
        if (!Double.isFinite(costoPorKm) || costoPorKm < 0) {
            throw new IllegalArgumentException(
                "El costo no puede ser negativo");
        }
        this.costoPorKm = costoPorKm;
    }

    public abstract double calcularTiempoEntrega(double km);

    
    public boolean puedeTransportar(Pedido pedido) {

        if (pedido == null) {
            return false;
        }

        return pedido.getPesoKg() > 0
                && pedido.getPesoKg() <= capacidadKg;
    }

    
    @Override
    public String toString() {
        return "Patente: " + patente
             + " | Capacidad: " + capacidadKg + " kg"
             + " | Velocidad: " + velocidadKmH + " km/h"
             + " | Costo por km: $" + costoPorKm;
    }
}