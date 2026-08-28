package data;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class Gestor implements Rastreable {
    private final List<Pedido> pedidos = new ArrayList<>();
    private final List<String> historial = new ArrayList<>();

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> getListaPedidos() {
        return pedidos;
    }

    @Override
    public void verHistorial() {
        for(Pedido pedido : pedidos) {
            historial.add("- " + pedido.getTipoPedido() + " #" + pedido.getIdPedido() + " - " + pedido.getEstado());
        }

        for(String resumen : historial) {
            System.out.println(resumen);
        }
    }
}