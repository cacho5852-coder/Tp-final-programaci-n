package excepciones;

/** Datos inválidos de un cliente (código, nombre, teléfono o zona) o código duplicado. */
public class ClienteInvalidoException extends Exception {
  public ClienteInvalidoException(String mensaje) {
     super(mensaje);
     } 
 }