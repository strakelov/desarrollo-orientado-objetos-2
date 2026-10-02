package controller;

import dao.impl.*;
import model.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControladorPedidos {
    private final List<Pedido> pedidos;

    private final PedidoDAOImpl pedidoDAOImpl = new PedidoDAOImpl();
    private final RepartidorDAOImpl repartidorDAOImpl = new RepartidorDAOImpl();
    private final EntregaDAOImpl entregaDAOImpl = new EntregaDAOImpl();

    public ControladorPedidos() {
        this.pedidos = new ArrayList<>();
        cargarPedidos();
    }

    private void cargarPedidos() {
        pedidos.clear();
        pedidos.addAll(pedidoDAOImpl.listarTodos());
    }

    public void iniciarEntregas() {
        ZonaCarga zonaCarga = new ZonaCarga(pedidos.size());

        for(Pedido p : pedidos) {
            zonaCarga.agregarPedido(p);
        }

        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {

            System.out.println("[ZONA DE CARGA INICIALIZADA CON " + pedidos.size() + " PEDIDOS]");

            List<Repartidor> repartidoresBD = repartidorDAOImpl.listarTodos();

            for(Repartidor r : repartidoresBD) {
                Repartidor repartidorConZona = new Repartidor(r.getId(), r.getNombre(), zonaCarga);
                executor.execute(repartidorConZona);
            }

            executor.shutdown();

        } catch (IllegalArgumentException e) {
            System.out.println("Proceso interrumpido durante la ejecución.");
            Thread.currentThread().interrupt();
        }

        System.out.println("\nTodos los pedidos han sido enviados correctamente");
    }

    public PedidoDAOImpl getPedidoDAOImpl() {return pedidoDAOImpl;}
    public RepartidorDAOImpl getRepartidorDAOImpl() {return repartidorDAOImpl;}
    public EntregaDAOImpl getEntregaDAOImpl() {return entregaDAOImpl;}
}