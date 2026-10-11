package dao.impl;

import dao.ReporteDAO;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAOImpl implements ReporteDAO {

    @Override
    public List<String[]> obtenerLibrosMasPrestados() {
        List<String[]> reporte = new ArrayList<>();
        String sql = "SELECT l.titulo, COUNT(p.id_libro) AS total_prestamos " +
                "FROM prestamos p JOIN libros l ON p.id_libro = l.id " +
                "GROUP BY p.id_libro, l.titulo ORDER BY total_prestamos DESC";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reporte.add(new String[]{
                            rs.getString("titulo"),
                            String.valueOf(rs.getInt("total_prestamos"))
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte de libros más prestados: " + e.getMessage());
        }
        return reporte;
    }

    @Override
    public List<String[]> obtenerHistorialEstudiante(int idEstudiante) {
        List<String[]> reporte = new ArrayList<>();
        String sql = "SELECT p.id, l.titulo, p.fecha_prestamo, p.fecha_devolucion, p.devuelto " +
                "FROM prestamos p JOIN libros l ON p.id_libro = l.id " +
                "WHERE p.id_estudiante = ?";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idEstudiante);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String estado = rs.getBoolean("devuelto") ? "Devuelto" : "Pendiente";
                        reporte.add(new String[]{
                                String.valueOf(rs.getInt("id")),
                                rs.getString("titulo"),
                                rs.getDate("fecha_prestamo").toString(),
                                rs.getDate("fecha_devolucion").toString(),
                                estado
                        });
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte de historial de estudiante: " + e.getMessage());
        }
        return reporte;
    }

    @Override
    public List<String[]> obtenerLibrosEnPrestamo() {
        List<String[]> reporte = new ArrayList<>();
        String sql = "SELECT p.id, e.nombre, l.titulo, p.fecha_prestamo, p.fecha_devolucion " +
                "FROM prestamos p " +
                "JOIN estudiantes e ON p.id_estudiante = e.id " +
                "JOIN libros l ON p.id_libro = l.id " +
                "WHERE p.devuelto = false";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reporte.add(new String[]{
                            String.valueOf(rs.getInt("id")),
                            rs.getString("nombre"),
                            rs.getString("titulo"),
                            rs.getDate("fecha_prestamo").toString(),
                            rs.getDate("fecha_devolucion").toString()
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte de libros en préstamo: " + e.getMessage());
        }
        return reporte;
    }
}