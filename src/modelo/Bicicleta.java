package modelo;


public class Bicicleta extends Vehiculo {

    
    public Bicicleta(String patente) {
        super(patente, 10, 15, 50);
    }

    
    @Override
    public double calcularTiempoEntrega(double km) {

        if (!Double.isFinite(km) || km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser mayor a cero");
        }

        return (km / getVelocidadKmH()) * 60;
    }

    
    @Override
    public boolean puedeTransportar(Pedido pedido) {

        if (pedido instanceof PedidoFragil) {
            return false;
        }

        return super.puedeTransportar(pedido);
    }
}