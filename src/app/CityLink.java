import modelo.cliente;
import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;


public class CityLink {
 private static final int MAX_CLIENTES = 50;   


 private  Cliente[] clientes = new Cliente[MAX_CLIENTES];
 private int cantClientes = 0;

 public void registrarCliente(Cliente c) throws AlmacenamientoLlenoException {
 if (cantClientes == MAX_CLIENTES) {
        throw new AlmacenamientoLlenoException("No hay lugar mas disponible");
    }
 clientes[cantClientes] = c;
 cantClientes++;
}
public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException{
 for (int i = 0; i < cantClientes; i++){
    if (clientes[i].getCodigo() == codigo){
        return clientes[i];
    }
 }
throw new ClienteNoEncontradoException("no existe un cliente con codigo "+ codigo);
}
public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
 for (int i = 0; i < cantClientes; i++){
    if (clientes[i].getNombre().equalsIgnoreCase(nombre)){
        return clientes[i];
    }
 }

throw new ClienteNoEncontradoException("no existe un cliente llamado "+ nombre);
 }
  }