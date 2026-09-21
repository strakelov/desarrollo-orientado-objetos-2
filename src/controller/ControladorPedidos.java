package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControladorPedidos {
    private List<Pedido> pedidos;

    private static final Random random = new Random();

    public ControladorPedidos() {
        this.pedidos = new ArrayList<>();
        cargarPedidos();
    }

    private void cargarPedidos() {
        pedidos.add(new PedidoComida(2392, "Av. San Lorenzo #239", 24, true));
        pedidos.add(new PedidoEncomienda(3290, "Av. Falsa #432", 25, 42, true));
        pedidos.add(new PedidoExpress(2039, "Av. San Juan #234", 36, true));
        pedidos.add(new PedidoComida(9284, "Av. San Pedro #449", 56, true));
        pedidos.add(new PedidoEncomienda(2982, "Av. Diego #187", 65, 24, true));
    }

    public void iniciarEntregas() {
        ZonaCarga zonaCarga = new ZonaCarga(pedidos.size());

        for(Pedido p : pedidos) {
            zonaCarga.agregarPedido(p);
        }

        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {

            System.out.println("[ZONA DE CARGA INICIALIZADA CON " + pedidos.size() + " PEDIDOS]");

            Repartidor repartidor1 = new Repartidor("Pedro", zonaCarga);
            Repartidor repartidor2 = new Repartidor("Sebastian", zonaCarga);
            Repartidor repartidor3 = new Repartidor("Daniel", zonaCarga);

            executor.execute(repartidor1);
            executor.execute(repartidor2);
            executor.execute(repartidor3);

            executor.shutdown();

        } catch (IllegalArgumentException e) {
            System.out.println("Proceso interrumpido durante la ejecución.");
            Thread.currentThread().interrupt();
        }

        System.out.println("\nTodos los pedidos han sido enviados correctamente");
    }

    public void agregarPedido(Pedido p) {
        pedidos.add(p);
    }

    public List<Pedido> getPedidos(){
        return pedidos;
    }

    public double generarNumeroAleatorio() {
        double numero = 5.0 + random.nextDouble() * 15.0;
        return Math.round(numero * 10.0) / 10.0;
    }
}
