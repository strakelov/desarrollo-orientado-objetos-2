package view;

import controller.ControladorPedidos;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal(ControladorPedidos controlador) {
        setTitle("SpeedFast - Sistema de Gestión");
        setSize(750, 350);
        setMinimumSize(new Dimension(700, 250));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();

        PanelEntregas panelEntregas = new PanelEntregas(controlador);

        pestanas.addTab("Gestión Repartidores", new PanelRepartidores(controlador));
        pestanas.addTab("Gestión Pedidos", new PanelPedidos(controlador));
        pestanas.addTab("Gestión Entregas", panelEntregas);

        pestanas.addChangeListener(e -> {
            if(pestanas.getSelectedIndex() == 2) {
                panelEntregas.cargarCombos();
            }
        });

        add(pestanas, BorderLayout.CENTER);
    }
}