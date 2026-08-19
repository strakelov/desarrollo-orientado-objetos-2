package model;

public abstract class Pedido {
    protected int idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    public abstract String getTipoPedido();

    public final void mostrarResumen() {
        System.out.println(getTipoPedido() + " #" + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm);
        calcularTiempoEntrega();
    }

    public abstract void calcularTiempoEntrega();

    public String asignarRepartidor() {
        return "Repartidor asignado";
    }

    public String asignarRepartidor(String nombreRepartidor) {
        return "El repartidor " + nombreRepartidor + " fue asignado";
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", distanciaKm='" + distanciaKm + '\'' +
                '}';
    }
}
