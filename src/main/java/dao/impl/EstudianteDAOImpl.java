package dao.impl;

import dao.EstudianteDAO;
import modelo.Estudiante;
import modelo.Rut;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    @Override
    public boolean registrar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, estudiante.getNombre());
                stmt.setString(2, estudiante.getRut().getNumero());
                stmt.setString(3, estudiante.getCurso());
                stmt.setString(4, estudiante.getCorreo());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar estudiante: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Estudiante> listarTodos() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        Estudiante estudiante = new Estudiante(
                                rs.getInt("id"),
                                new Rut(rs.getString("rut")),
                                rs.getString("nombre"),
                                rs.getString("correo"),
                                rs.getString("curso")
                        );
                        estudiantes.add(estudiante);
                    } catch (Exception e) {
                        System.err.println("Error al procesar el RUT del estudiante con ID " + rs.getInt("id") + ": " + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }

    @Override
    public boolean actualizar(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre = ?, rut = ?, curso = ?, correo = ? WHERE id = ?";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, estudiante.getNombre());
                stmt.setString(2, estudiante.getRut().getNumero());
                stmt.setString(3, estudiante.getCurso());
                stmt.setString(4, estudiante.getCorreo());
                stmt.setInt(5, estudiante.getId());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String foreignKey = "DELETE FROM prestamos WHERE id_estudiante = ?";
        String primaryKey = "DELETE FROM estudiantes WHERE id = ?";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();

            try (PreparedStatement stmt1 = conn.prepareStatement(foreignKey)) {
                stmt1.setInt(1, id);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(primaryKey)) {
                stmt2.setInt(1, id);
                stmt2.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }
}