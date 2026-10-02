package model;

public abstract class Pedido implements Despachable, Cancelable, Rastreable {
    protected int id;
    protected String direccion;
    protected double distanciaKm;
    protected Estado estado = Estado.PENDIENTE;

    public Pedido(int id, String direccion, double distanciaKm) {
        this.id = id;
        this.direccion = direccion;
        this.distanciaKm = distanciaKm;
    }

    public final String mostrarResumen() {
        return getTipo() + " #" + id + "\n" +
                "Direccion: " + direccion + "\n" +
                "Distancia: " + distanciaKm + " km" + "\n" +
                calcularTiempoEntrega();
    }

    @Override
    public String despachar() {
        return getTipo() + " despachado correctamente.\n";
    }

    @Override
    public String cancelar() {
        return "Cancelando " + getTipo() + " #" + getId() + "\n" +
                "-> " + getTipo() + " cancelado correctamente.\n\n";
    }

    @Override
    public String verHistorial() {
        return "- " + getTipo() + " #" + getId() + " - " + getEstado();
    }

    public abstract String getTipo();
    public abstract String calcularTiempoEntrega();

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}

    public double getDistanciaKm() {return distanciaKm;}
    public void setDistanciaKm(double distanciaKm) {this.distanciaKm = distanciaKm;}

    public Estado getEstado() {return estado;}
    public void setEstado(Estado estado) {this.estado = estado;}

    @Override
    public String toString() {
        return id + " (" + estado + "): '" + direccion + "'";
    }
}
