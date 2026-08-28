package model;

public class PedidoEncomienda extends Pedido {
    private double peso;
    private boolean tieneEmbalaje;

    private final int TIEMPO_BASE_MINUTOS = 20;
    private final double MULTIPLICADOR_DISTANCIA = 1.5;
    private final double PESO_MAXIMO = 50.0;

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm, double peso, boolean tieneEmbalaje) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.peso = peso;
        this.tieneEmbalaje = tieneEmbalaje;
    }

    @Override
    public String getTipoPedido() {
        return "Pedido Encomienda";
    }

    @Override
    public String calcularTiempoEntrega() {
        int tiempo = (int) Math.round(TIEMPO_BASE_MINUTOS + (MULTIPLICADOR_DISTANCIA * distanciaKm));
        return "Tiempo estimado de entrega: " + tiempo + " minutos.";
    }

    @Override
    public String asignarRepartidor() {
        if(peso >= PESO_MAXIMO || !tieneEmbalaje) {
            return "El pedido de encomienda no tiene embalaje o el peso es muy alto.";
        }

        return "El pedido de encomienda va en camino a " + direccionEntrega;
    }

    @Override
    public String asignarRepartidor(String nombreRepartidor) {
        if(peso >= PESO_MAXIMO || !tieneEmbalaje) {
            return "El pedido de encomienda no tiene embalaje o el peso es muy alto.";
        }

        return "El repartidor de encomienda " + nombreRepartidor + " va en camino";
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isTieneEmbalaje() {
        return tieneEmbalaje;
    }

    public void setTieneEmbalaje(boolean tieneEmbalaje) {
        this.tieneEmbalaje = tieneEmbalaje;
    }

    @Override
    public String toString() {
        return super.toString() + " - PedidoEncomienda{" +
                "peso=" + peso +
                ", tieneEmbalaje=" + tieneEmbalaje +
                '}';
    }
}