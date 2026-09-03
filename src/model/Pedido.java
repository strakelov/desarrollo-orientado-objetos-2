package model;

public abstract class Pedido implements Despachable, Cancelable {
    protected int idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;
    protected String estado = "Pendiente";

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
    public void despachar() {
        System.out.println(getTipoPedido() + " despachado correctamente.\n");
        this.estado = "Despachado";
    }

    @Override
    public void cancelar() {
        System.out.println("Cancelando " + getTipoPedido() + " #" + getIdPedido());
        System.out.println("-> " + getTipoPedido() + " cancelado correctamente.\n");
        this.estado = "Cancelado";
    }

    public abstract String getTipoPedido();
    public abstract String calcularTiempoEntrega();

    public int getIdPedido() {return idPedido;}
    public void setIdPedido(int idPedido) {this.idPedido = idPedido;}

    public String getDireccionEntrega() {return direccionEntrega;}
    public void setDireccionEntrega(String direccionEntrega) {this.direccionEntrega = direccionEntrega;}

    public double getDistanciaKm() {return distanciaKm;}
    public void setDistanciaKm(double distanciaKm) {this.distanciaKm = distanciaKm;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

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
