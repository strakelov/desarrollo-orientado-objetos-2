package dao.impl;

import dao.UsuarioDAO;
import modelo.Bibliotecario;
import modelo.Estudiante;
import modelo.Rut;
import modelo.Usuario;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario autenticar(String correo, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, correo);
                stmt.setString(2, contrasena);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        Rut rut = new Rut(rs.getString("rut"));
                        String rol = rs.getString("rol");

                        if ("estudiante".equalsIgnoreCase(rol)) {
                            return new Estudiante(id, rut, nombre, correo, "Sin Curso");
                        } else {
                            return new Bibliotecario(id, rut, nombre, correo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en la autenticación: " + e.getMessage());
        }
        return null;
    }
}
