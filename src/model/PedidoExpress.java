package model;

public class PedidoExpress extends Pedido {
    private boolean repartidorDisponible;

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm, boolean repartidorDisponible) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.repartidorDisponible = repartidorDisponible;
    }

    @Override
    public String getTipoPedido() {
        return "Pedido Express";
    }

    @Override
    public void calcularTiempoEntrega() {
        int tiempo = 10;
        if (distanciaKm > 5) tiempo += 5;
        System.out.println("Tiempo estimado de entrega: " + tiempo + " minutos.\n");
    }

    @Override
    public String asignarRepartidor() {
        if(!repartidorDisponible) {
            return "El repartidor express no está diponible";
        }

        return "El pedido express va en camino a " + direccionEntrega;
    }

    @Override
    public String asignarRepartidor(String nombreRepartidor) {
        if(!repartidorDisponible) {
            return "El repartidor express " + nombreRepartidor + " no está disponible.";
        }

        return "El repartidor express " + nombreRepartidor + " va en camino";
    }

    public boolean isRepartidorDisponible() {
        return repartidorDisponible;
    }

    public void setRepartidorDisponible(boolean repartidorDisponible) {
        this.repartidorDisponible = repartidorDisponible;
    }

    @Override
    public String toString() {
        return "PedidoExpress{" +
                "repartidorDisponible=" + repartidorDisponible +
                '}';
    }
}
