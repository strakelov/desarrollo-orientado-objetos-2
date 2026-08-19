package model;

public class PedidoEncomienda extends Pedido {
    private double peso;
    private boolean tieneEmbalaje;

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
    public void calcularTiempoEntrega() {
        int tiempo = (int) Math.round(20 + (1.5 * distanciaKm));
        System.out.println("Tiempo estimado de entrega: " + tiempo + " minutos.\n");
    }

    @Override
    public String asignarRepartidor() {
        if(peso >= 50 || !tieneEmbalaje) {
            return "El pedido de encomienda no tiene embalaje o el peso es muy alto.";
        }

        return "El pedido de encomienda va en camino a " + direccionEntrega;
    }

    @Override
    public String asignarRepartidor(String nombreRepartidor) {
        if(peso >= 50 || !tieneEmbalaje) {
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
        return "PedidoEncomienda{" +
                "peso=" + peso +
                ", tieneEmbalaje=" + tieneEmbalaje +
                '}';
    }
}