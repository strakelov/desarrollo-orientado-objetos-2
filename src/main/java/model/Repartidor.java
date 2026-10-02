package model;

import dao.impl.EntregaDAOImpl;

import java.util.Random;

public class Repartidor implements Runnable {
    private final int id;
    private final String nombre;
    private final ZonaCarga zonaCarga;

    private final Random random = new Random();

    public Repartidor(int id, String nombre, ZonaCarga zonaCarga) {
        this.id = id;
        this.nombre = nombre;
        this.zonaCarga = zonaCarga;
    }

    public Repartidor(int id, String nombre) {
        this(id, nombre, null);
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

                EntregaDAOImpl entregaDAOImpl = new EntregaDAOImpl();
                Entrega entrega = new Entrega(
                        0,
                        pedidoRetirado.id,
                        this.id,
                        java.time.LocalDate.now(),
                        java.time.LocalTime.now()
                );
                entregaDAOImpl.registrar(entrega);
            } catch (InterruptedException e) {
                System.out.println("[Repartidor " + nombre + "] Entrega interrumpida. Cancelando ruta.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public ZonaCarga getZonaCarga() {return zonaCarga;}
    public Random getRandom() {return random;}

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}