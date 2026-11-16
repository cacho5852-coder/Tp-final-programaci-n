package modelo;

import excepciones.PedidoInvalidoException;

/** Pedido sin recargo. */
public class PedidoNormal extends Pedido {

    public PedidoNormal() {
        super();
    }

    public PedidoNormal(int codigo, Cliente cliente, String destino,
              double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        return costoSinRecargo(vehiculo);
    }
}