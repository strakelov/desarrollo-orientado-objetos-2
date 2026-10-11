package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "@Gabo12142007";

    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() throws SQLException {
        try {
            this.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar la base de datos: " + e.getMessage());
        }
    }

    public static synchronized ConexionBD getInstance() throws SQLException {
        if(instancia == null || instancia.getConexion().isClosed()) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}