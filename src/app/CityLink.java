package app;

import algoritmos.Busqueda;
import excepciones.ClienteInvalidoException;
import excepciones.ClienteNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import modelo.Cliente;

/** El gestor de CityLink. Guarda los clientes en un árbol ordenado por código. */
public class CityLink {

    private final TreeMap<Integer, Cliente> clientes = new TreeMap<>();

    /** Arranca el gestor ya con algunos clientes de prueba cargados. */
    public CityLink() {
        cargarDatosDePrueba();
    }


    /**
     * Agrega un cliente nuevo.
     *
     * @throws ClienteInvalidoException si el cliente es nulo o ya hay otro con ese código
     */
    public void registrarCliente(Cliente c) throws ClienteInvalidoException {
        if (c == null) {
            throw new ClienteInvalidoException("El cliente no puede ser nulo.");
        }
        if (clientes.containsKey(c.getCodigo())) {
            throw new ClienteInvalidoException("Ya existe un cliente con código " + c.getCodigo());
        }
        clientes.put(c.getCodigo(), c);
    }

    /**
     * Busca un cliente por su código (lo resuelve el árbol).
     *
     * @throws ClienteNoEncontradoException si no hay ninguno con ese código
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        Cliente c = clientes.get(codigo);
        if (c == null) {
            throw new ClienteNoEncontradoException("No existe un cliente con código " + codigo);
        }
        return c;
    }

    /**
     * Busca un cliente por su nombre, recorriendo la lista de uno en uno.
     *
     * @throws ClienteNoEncontradoException si no hay ninguno con ese nombre
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        Cliente c = Busqueda.buscarClientePorNombre(clientes.values(), nombre);
        if (c == null) {
            throw new ClienteNoEncontradoException("No existe un cliente llamado " + nombre);
        }
        return c;
    }

    /**
     * Borra un cliente.
     *
     * @throws ClienteNoEncontradoException si no hay ninguno con ese código
     */
    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {
        if (clientes.remove(codigo) == null) {
            throw new ClienteNoEncontradoException("No existe un cliente con código " + codigo);
        }
    }

    /** @return todos los clientes, ya ordenados por código */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }

    public CityLink() {
    try {
        cargarDatosDePrueba();
    } catch (ClienteInvalidoException e) {
        throw new IllegalStateException("Datos de prueba inválidos: " + e.getMessage(), e);
    }
}      private void cargarDatosDePrueba() throws ClienteInvalidoException {
        registrarCliente(new Cliente(1, "Ana Pérez", "2657-111111", "Centro"));
     
        registrarCliente(new Cliente(2, "Luis Gómez", "2657-222222", "Norte"));
      
        registrarCliente(new Cliente(3, "María Díaz", "2657-333333", "Sur"));
       
        registrarCliente(new Cliente(4, "Carlos Ruiz", "2657-444444", "Este"));
     
        registrarCliente(new Cliente(5, "Sofía Luna", "2657-555555", "Oeste"));
    }
}