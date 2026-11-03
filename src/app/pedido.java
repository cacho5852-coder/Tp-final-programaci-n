

public class pedido {
 int codigo;
 String cliente;
 String destino;
 float peso;
 float distancia;
 int fecha;
 int hora;
 String tipo;

    public pedido() {
    }

    public pedido(int codigo, String cliente, String destino, float peso, float distancia, int fecha, int hora, String tipo) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.destino = destino;
        this.peso = peso;
        this.distancia = distancia;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
    }
}
