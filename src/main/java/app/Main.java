package app;

import controller.ControladorPedidos;
import util.ConexionBD;
import view.VentanaPrincipal;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = ConexionBD.conectar()) {
            System.out.println("Conexión a base de datos exitosa!");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            return;
        }

        ControladorPedidos controlador = new ControladorPedidos();
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);
        });
    }
}