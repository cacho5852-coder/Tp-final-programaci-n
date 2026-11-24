package algoritmos;

import java.util.Collection;
import java.util.List;

import modelo.Cliente;
import modelo.Pedido;

public class Busqueda {
    private Busqueda() {
    }

    public static Cliente buscarClientePorNombre(Collection<Cliente> clientes, String nombre) {
        if (nombre == null) {
            return null;
        }

        String buscado = nombre.trim();
        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(buscado)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Búsqueda binaria de un pedido por código.
     * La lista tiene que estar ordenada por código de menor a mayor.
     *
     * @return el pedido, o null si no está
     */
    public static Pedido buscarPedidoPorCodigo(List<Pedido> ordenados, int codigo) {
        int desde = 0;
        int hasta = ordenados.size() - 1;

        while (desde <= hasta) {
            int medio = (desde + hasta) / 2;
            int actual = ordenados.get(medio).getCodigo();

            if (actual == codigo) {
                return ordenados.get(medio);
            } else if (actual < codigo) {
                desde = medio + 1;
            } else {
                hasta = medio - 1;
            }
        }
        return null;
    }
}