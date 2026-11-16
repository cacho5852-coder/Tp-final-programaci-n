package algoritmos;

import java.util.Collection;
import modelo.Cliente;


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
}