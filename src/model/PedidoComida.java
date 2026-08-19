package model;

public class PedidoComida extends Pedido {
    private boolean tieneMochila;

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm, boolean tieneMochila) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.tieneMochila = tieneMochila;
    }

    @Override
    public String getTipoPedido() {
        return "Pedido Comida";
    }

    @Override
    public void calcularTiempoEntrega() {
        int tiempo = (int) Math.round(15 + (2 * distanciaKm));
        System.out.println("Tiempo estimado de entrega: " + tiempo + " minutos.\n");
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

    public boolean isTieneMochila() {
        return tieneMochila;
    }

    public void setTieneMochila(boolean tieneMochila) {
        this.tieneMochila = tieneMochila;
    }

    @Override
    public String toString() {
        return "PedidoComida{" +
                "tieneMochila=" + tieneMochila +
                '}';
    }
}
