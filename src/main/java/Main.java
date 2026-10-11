import util.ConexionBD;
import vista.Login;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            if(conn != null && !conn.isClosed()) {
                System.out.println("Conexión a base de datos exitosa.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar la base de datos: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            Login ventanaLogin = new Login();
            ventanaLogin.setVisible(true);
        });
    }
}