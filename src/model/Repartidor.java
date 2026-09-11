package model;

import java.util.Random;

public class Repartidor implements Runnable {
    private final String nombre;
    private final ZonaCarga zonaCarga;

    private final Random random = new Random();

    public Repartidor(String nombre, ZonaCarga zonaCarga) {
        this.nombre = nombre;
        this.zonaCarga = zonaCarga;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            Pedido pedidoRetirado = zonaCarga.retirarPedido();
            if(pedidoRetirado == null) {
                System.out.println("[Repartidor " + nombre + "] No hay pedidos pendientes.");
                break;
            }
            pedidoRetirado.setEstado(Estado.EN_REPARTO);

            try {
                Thread.sleep(random.nextInt(3000) + 1000);
                System.out.println("[Repartidor " + nombre + "] Pedido en camino...");
                Thread.sleep(random.nextInt(2000) + 1000);

                System.out.println("[Repartidor " + nombre + "] Pedido entregado correctamente.");
                pedidoRetirado.setEstado(Estado.ENTREGADO);
            } catch (InterruptedException e) {
                System.out.println("[Repartidor " + nombre + "] Entrega interrumpida. Cancelando ruta.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}