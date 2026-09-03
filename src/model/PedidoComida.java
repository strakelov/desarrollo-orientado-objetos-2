package model;

public class PedidoComida extends Pedido {
    private boolean tieneMochila;

    private final int TIEMPO_BASE_MINUTOS = 15;
    private final int MULTIPLICADOR_DISTANCIA = 2;

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm, boolean tieneMochila) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.tieneMochila = tieneMochila;
    }

    @Override
    public String getTipoPedido() {
        return "Pedido Comida";
    }

    @Override
    public String calcularTiempoEntrega() {
        int tiempo = (int) Math.round(TIEMPO_BASE_MINUTOS + (MULTIPLICADOR_DISTANCIA * distanciaKm));
        return "Tiempo estimado de entrega: " + tiempo + " minutos.";
    }

    public boolean isTieneMochila() {return tieneMochila;}
    public void setTieneMochila(boolean tieneMochila) {this.tieneMochila = tieneMochila;}

    @Override
    public String toString() {
        return super.toString() + " - PedidoComida{" +
                "tieneMochila=" + tieneMochila +
                '}';
    }
}
