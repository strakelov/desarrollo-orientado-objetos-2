package app;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        List<Pedido> pedidosRepartidor1 = new ArrayList<>();
        List<Pedido> pedidosRepartidor2 = new ArrayList<>();
        List<Pedido> pedidosRepartidor3 = new ArrayList<>();

        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {

            pedidosRepartidor1.add(new PedidoComida(2392, "Av. San Lorenzo #239", 24, true));
            pedidosRepartidor1.add(new PedidoEncomienda(3290, "Av. Falsa #432", 25, 42, true));
            Repartidor repartidor1 = new Repartidor("Vicente", pedidosRepartidor1);
            executor.execute(repartidor1);

            pedidosRepartidor2.add(new PedidoExpress(2039, "Av. San Juan #234", 36, true));
            pedidosRepartidor2.add(new PedidoComida(9284, "Av. San Pedro #449", 56, true));
            Repartidor repartidor2 = new Repartidor("Sebastian", pedidosRepartidor2);
            executor.execute(repartidor2);

            pedidosRepartidor3.add(new PedidoEncomienda(2982, "Av. Diego #187", 65, 24, true));
            pedidosRepartidor3.add(new PedidoExpress(9208, "Av. Rafael #783", 59, true));
            Repartidor repartidor3 = new Repartidor("Daniel", pedidosRepartidor3);
            executor.execute(repartidor3);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Numero de hilos disponibles igual o menor a 0.");
        }
    }
}