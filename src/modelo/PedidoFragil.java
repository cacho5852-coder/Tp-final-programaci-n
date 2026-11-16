package modelo;

import excepciones.PedidoInvalidoException;

/** Pedido con recargo fijo de $800. */
public class PedidoFragil extends Pedido {

    public PedidoFragil() {
        super();
    }

    public PedidoFragil(int codigo, Cliente cliente, String destino,
              double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        return costoSinRecargo(vehiculo) + 800;
    }
}
