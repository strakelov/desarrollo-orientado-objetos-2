package app;

import data.Gestor;
import model.*;

public class Main {
    public static void main(String[] args) {
        Gestor gestor = new Gestor();

        gestor.agregarPedido(new PedidoComida(2392, "Av. San Lorenzo #239", 24, true));
        gestor.agregarPedido(new PedidoEncomienda(3290, "Av. Falsa #432", 25, 42, true));
        gestor.agregarPedido(new PedidoExpress(2039, "Av. San Juan #234", 36, false));

        System.out.println("===== RESUMEN DE PEDIDOS =====");
        Pedido pedido1 = gestor.getListaPedidos().get(0);
        System.out.println(pedido1.mostrarResumen());
        System.out.println(pedido1.asignarRepartidor() + "\n");
        pedido1.despachar();

        Pedido pedido2 = gestor.getListaPedidos().get(1);
        System.out.println(pedido2.mostrarResumen());
        System.out.println(pedido2.asignarRepartidor("Carlos") + "\n");

        Pedido pedido3 = gestor.getListaPedidos().get(2);
        System.out.println(pedido3.mostrarResumen());
        System.out.println(pedido3.asignarRepartidor("Laura") + "\n");
        pedido3.cancelar();

        System.out.println("===== HISTORIAL =====");
        gestor.verHistorial();
    }
}