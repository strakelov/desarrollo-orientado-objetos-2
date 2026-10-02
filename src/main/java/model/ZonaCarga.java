package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ZonaCarga {
    private final BlockingQueue<Pedido> colaPedidos;

    public ZonaCarga(int capacidadMaxima) {
        this.colaPedidos = new ArrayBlockingQueue<>(capacidadMaxima);
    }

    public synchronized void agregarPedido(Pedido p) {
        if (!colaPedidos.offer(p)) {
            System.out.println("Capacidad máx alcanzada. Rechazando pedido #" + p.getId());
            return;
        }

        System.out.println("Pedido #" + p.getId() + " agregado a la zona de carga.");
    }

    public synchronized Pedido retirarPedido() {
        try {
            return colaPedidos.poll(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}