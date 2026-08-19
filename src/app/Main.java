package app;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Pedido> pedidos = new ArrayList<>();

        pedidos.add(new PedidoComida(2392, "Av. San Lorenzo #239", 24, true));
        pedidos.add(new PedidoEncomienda(3290, "Av. Falsa #432", 25, 42, true));

        pedidos.add(new PedidoExpress(2039, "Av. San Juan #234", 36,false));

        System.out.println("\n===== PRUEBA DE POLIMORFISMO (Patron de Diseño) =====");
        for(Pedido pedido : pedidos) {
            pedido.mostrarResumen();
        }
    }
}