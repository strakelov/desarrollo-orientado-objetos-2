package model;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ZonaCarga {
    private final BlockingQueue<Pedido> colaPedidos;
    private final int capacidadMaxima;
    private final Random random = new Random();

    public ZonaCarga(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.colaPedidos = new ArrayBlockingQueue<>(10);
    }

    public synchronized void agregarPedido(Pedido p) {
        try {
            if (colaPedidos.size() >= capacidadMaxima) {
                System.out.println("Capacidad máx alcanzada. Rechazando pedido #" + p.getIdPedido());
                return;
            }

            if(colaPedidos.offer(p, 5, TimeUnit.SECONDS)) {
                Thread.sleep(random.nextInt(3000) + 1000);
                System.out.println("Pedido #" + p.getIdPedido() + " agregado. Destino: " + p.getDireccionEntrega());
            } else {
                Thread.sleep(random.nextInt(1000) + 1000);
                System.out.println("[Pedido #" + p.getIdPedido() + " no pudo ser agregado.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Pedido #" + p.getIdPedido() + " interrumpido");
        }
    }

    public synchronized Pedido retirarPedido() {
        try {
            Pedido pedido = colaPedidos.poll(5, TimeUnit.SECONDS);
            if(pedido == null) {
                System.out.println("No hay pedidos pendientes.");
                return null;
            }

            Thread.sleep(random.nextInt(2000) + 1000);
            System.out.println("Retirando pedido #" + pedido.idPedido + "...");
            Thread.sleep(random.nextInt(4000) + 2000);
            System.out.println("Pedido #" + pedido.getIdPedido() + " en reparto.");
            return pedido;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Pedido interrumpido");
            return null;
        }
    }
}