package modelo;

import excepciones.ClienteInvalidoException;

public class Cliente {
    private int codigo;
    private String nombre;
    private String telefono;
    private String zona;

/** Constructor vacio */
    public Cliente() {

    }
/** constructor completo */
    public Cliente(int codigo, String nombre, String telefono, String zona)
    throws ClienteInvalidoException  {
        setCodigo(codigo);
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
    }


  /** @throws ClienteInvalidoException si el código no es mayor que cero */
public void setCodigo(int codigo) throws ClienteInvalidoException {
    if (codigo <= 0) {
        throw new ClienteInvalidoException("El código debe ser mayor que cero.");
    }
    this.codigo = codigo;
}

/** @throws ClienteInvalidoException si el nombre está vacío */
public void setNombre(String nombre) throws ClienteInvalidoException {
    if (nombre == null || nombre.isBlank()) {
        throw new ClienteInvalidoException("El nombre no puede estar vacío.");
    }
    this.nombre = nombre.trim();
}

/** @throws ClienteInvalidoException si el teléfono está vacío */
public void setTelefono(String telefono) throws ClienteInvalidoException {
    if (telefono == null || telefono.isBlank()) {
        throw new ClienteInvalidoException("El teléfono no puede estar vacío.");
    }
    this.telefono = telefono.trim();
}

/** @throws ClienteInvalidoException si la zona está vacía */
public void setZona(String zona) throws ClienteInvalidoException {
    if (zona == null || zona.isBlank()) {
        throw new ClienteInvalidoException("La zona no puede estar vacía.");
    }
    this.zona = zona.trim();
}

  @Override
public String toString() {
    return "Cliente [" + codigo + "] " + nombre + " - tel: " + telefono + " - zona: " + zona;
}
}