package app;

import controller.ControladorPedidos;
import model.*;
import view.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        ControladorPedidos controlador = new ControladorPedidos();

        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);
        });
    }
}