package modelo;

/** Objetos que generan un importe (costo de envío o comisión). */
public interface Facturable {
    double calcularImporte();
}