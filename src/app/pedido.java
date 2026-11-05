package app;

public class pedido {
    private int codigo;
    private String cliente;
    private String destino;
    private float peso;
    private float distancia;
    private int fecha;
    private int hora;
    private String tipo;

    // Constructor vacío
    public pedido() {
    }

    // Constructor completo
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

    // --- Getters ---
    public int getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDestino() {
        return destino;
    }

    public float getPeso() {
        return peso;
    }

    public float getDistancia() {
        return distancia;
    }

    public int getFecha() {
        return fecha;
    }

    public int getHora() {
        return hora;
    }

    public String getTipo() {
        return tipo;
    }

    // --- Setters ---
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
