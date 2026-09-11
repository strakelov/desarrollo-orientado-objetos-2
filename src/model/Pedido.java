package model;

public abstract class Pedido implements Despachable, Cancelable, Rastreable {
    protected int idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;
    protected Estado estado = Estado.PENDIENTE;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    public final String mostrarResumen() {
        return getTipoPedido() + " #" + idPedido + "\n" +
                "Direccion: " + direccionEntrega + "\n" +
                "Distancia: " + distanciaKm + " km" + "\n" +
                calcularTiempoEntrega();
    }

    @Override
    public String despachar() {
        return getTipoPedido() + " despachado correctamente.\n";
    }

    @Override
    public String cancelar() {
        return "Cancelando " + getTipoPedido() + " #" + getIdPedido() + "\n" +
                "-> " + getTipoPedido() + " cancelado correctamente.\n\n";
    }

    @Override
    public String verHistorial() {
        return "- " + getTipoPedido() + " #" + getIdPedido() + " - " + getEstado();
    }

    public abstract String getTipoPedido();
    public abstract String calcularTiempoEntrega();

    public int getIdPedido() {return idPedido;}
    public void setIdPedido(int idPedido) {this.idPedido = idPedido;}

    public String getDireccionEntrega() {return direccionEntrega;}
    public void setDireccionEntrega(String direccionEntrega) {this.direccionEntrega = direccionEntrega;}

    public double getDistanciaKm() {return distanciaKm;}
    public void setDistanciaKm(double distanciaKm) {this.distanciaKm = distanciaKm;}

    public Estado getEstado() {return estado;}
    public void setEstado(Estado estado) {this.estado = estado;}

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", distanciaKm=" + distanciaKm +
                ", estado='" + estado + '\'' +
                '}';
    }
}
