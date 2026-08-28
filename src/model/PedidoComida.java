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

    @Override
    public String asignarRepartidor() {
        if(!tieneMochila) {
            return "El repartidor de comida no tiene mochila termica.";
        }

        return "El pedido de comida va en camino a " + direccionEntrega;
    }

    @Override
    public String asignarRepartidor(String nombreRepartidor) {
        if(!tieneMochila) {
            return "El repartidor de comida " + nombreRepartidor + " no tiene mochila termica.";
        }

        return "El repartidor de comida " + nombreRepartidor + " va en camino";
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
