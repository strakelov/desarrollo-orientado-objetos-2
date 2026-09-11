package app;

import model.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ZonaCarga zonaCarga = new ZonaCarga(5);

        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {

            System.out.println("[ZONA DE CARGA INICIALIZADA]");

            zonaCarga.agregarPedido(new PedidoComida(2392, "Av. San Lorenzo #239", 24, true));
            zonaCarga.agregarPedido(new PedidoEncomienda(3290, "Av. Falsa #432", 25, 42, true));
            zonaCarga.agregarPedido(new PedidoExpress(2039, "Av. San Juan #234", 36, true));
            zonaCarga.agregarPedido(new PedidoComida(9284, "Av. San Pedro #449", 56, true));
            zonaCarga.agregarPedido(new PedidoEncomienda(2982, "Av. Diego #187", 65, 24, true));

            Repartidor repartidor1 = new Repartidor("Pedro", zonaCarga);
            Repartidor repartidor2 = new Repartidor("Sebastian", zonaCarga);
            Repartidor repartidor3 = new Repartidor("Daniel", zonaCarga);

            executor.execute(repartidor1);
            executor.execute(repartidor2);
            executor.execute(repartidor3);

            executor.shutdown();

        } catch (IllegalArgumentException e) {
            System.out.println("Proceso interrumpido durante la espera.");
            Thread.currentThread().interrupt();
        }

        System.out.println("\nTodos los pedidos han sido enviados correctamente");
    }
}