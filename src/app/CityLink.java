package app;

import modelo.Cliente;
import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;

public class CityLink {
    private static final int MAX_CLIENTES = 50;

    private Cliente[] clientes = new Cliente[MAX_CLIENTES];
    private int cantClientes = 0;

    public CityLink() {
        cargarDatosDePrueba();
    }

    public void registrarCliente(Cliente c) throws AlmacenamientoLlenoException {
        if (cantClientes == MAX_CLIENTES) {
            throw new AlmacenamientoLlenoException("No hay lugar más disponible");
        }
        if (existeCodigo(c.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un cliente con código " + c.getCodigo());
        }
        clientes[cantClientes] = c;
        cantClientes++;
    }

    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No existe un cliente con código " + codigo);
    }

    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No existe un cliente llamado " + nombre);
    }


    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                for (int j = i; j < cantClientes - 1; j++) {
                    clientes[j] = clientes[j + 1];
                }
                clientes[cantClientes - 1] = null;
                cantClientes--;
                return;
            }
        }
        throw new ClienteNoEncontradoException("No existe un cliente con código " + codigo);
    }

    private boolean existeCodigo(int codigo) {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    private void cargarDatosDePrueba() {
        try {
            registrarCliente(new Cliente(1, "Ana Pérez", "2657-111111", "Centro"));
            registrarCliente(new Cliente(2, "Luis Gómez", "2657-222222", "Norte"));
            registrarCliente(new Cliente(3, "María Díaz", "2657-333333", "Sur"));
            registrarCliente(new Cliente(4, "Carlos Ruiz", "2657-444444", "Este"));
            registrarCliente(new Cliente(5, "Sofía Luna", "2657-555555", "Oeste"));
        } catch (AlmacenamientoLlenoException e) {
            System.out.println("Error al cargar datos de prueba: " + e.getMessage());
        }
    }
}