package modelo;

import excepciones.PedidoInvalidoException;

/** Pedido con 50% de recargo. */
public class PedidoUrgente extends Pedido {
public static final double RECARGO = 1.5;
    public PedidoUrgente() {
        super();
    }

    public PedidoUrgente(int codigo, Cliente cliente, String destino,
              double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
       return costoSinRecargo(vehiculo) * RECARGO;
    }
    
}
