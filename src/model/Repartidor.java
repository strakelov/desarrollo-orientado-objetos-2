package model;

import java.util.List;
import java.util.Random;

public class Repartidor implements Runnable {
    private final String nombre;
    private final List<Pedido> pedidos;

    private static final Random random = new Random();

    public Repartidor(String nombre, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.pedidos = pedidos;
    }

    @Override
    public void run() {
        for(Pedido pedido : pedidos) {
            try {
                Thread.sleep(random.nextInt(10) * 2000);
                System.out.println("Pedido realizado. ID: #" + pedido.getIdPedido());
                Thread.sleep(random.nextInt(7) * 2000);
                System.out.println("[Repartidor: " + nombre + "] " + pedido.getTipoPedido() + " #" + pedido.getIdPedido() + " en camino.");
                Thread.sleep(random.nextInt(5) * 2000);
                System.out.println(pedido.despachar());
            } catch (InterruptedException e) {
                System.out.println("Entrega interrumpida del pedido #" + pedido.getIdPedido());
            }
        }
    }
}