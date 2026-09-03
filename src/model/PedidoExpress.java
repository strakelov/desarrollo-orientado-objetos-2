package model;

public class PedidoExpress extends Pedido {
    private boolean repartidorDisponible;

    private final int TIEMPO_BASE_MINUTOS = 10;
    private final int INCREMENTO_TIEMPO = 5;
    private final double DISTANCIA_LIMITE = 5.0;

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm, boolean repartidorDisponible) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.repartidorDisponible = repartidorDisponible;
    }

    @Override
    public String getTipoPedido() {
        return "Pedido Express";
    }

    @Override
    public String calcularTiempoEntrega() {
        int tiempo = TIEMPO_BASE_MINUTOS;
        if (distanciaKm > DISTANCIA_LIMITE) tiempo += INCREMENTO_TIEMPO;
        return "Tiempo estimado de entrega: " + tiempo + " minutos.";
    }

    @Override
    public String toString() {
        return super.toString() + " - PedidoExpress{" +
                "repartidorDisponible=" + repartidorDisponible +
                '}';
    }
}
