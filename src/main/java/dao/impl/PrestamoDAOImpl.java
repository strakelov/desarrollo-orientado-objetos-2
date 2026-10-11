package dao.impl;

import dao.PrestamoDAO;
import modelo.Prestamo;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {
    public synchronized boolean descontarStock(int idLibro) {
        String sql = "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idLibro);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean registrarPrestamo(Prestamo prestamo) {
        if (!descontarStock(prestamo.getIdLibro())) {
            System.err.println("No hay stock disponible para realizar el prestamo.");
            return false;
        }

        String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, prestamo.getIdEstudiante());
                stmt.setInt(2, prestamo.getIdLibro());
                stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
                stmt.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
                stmt.setBoolean(5, prestamo.isDevuelto());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean registrarDevolucion(int idPrestamo, int idLibro) {
        String sqlUpdatePrestamo = "UPDATE prestamos SET devuelto = true WHERE id = ?";
        String sqlUpdateStock = "UPDATE libros SET stock = stock + 1 WHERE id = ?";

        try {
            Connection conn = ConexionBD.getInstance().getConexion();

            try (PreparedStatement stmt1 = conn.prepareStatement(sqlUpdatePrestamo)) {
                stmt1.setInt(1, idPrestamo);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateStock)) {
                stmt2.setInt(1, idLibro);
                stmt2.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar devolución: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Prestamo> listarTodos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Prestamo prestamo = new Prestamo(
                            rs.getInt("id"),
                            rs.getInt("id_estudiante"),
                            rs.getInt("id_libro"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion").toLocalDate(),
                            rs.getBoolean("devuelto")
                    );
                    lista.add(prestamo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Prestamo> listarPorEstudiante(int idEstudiante) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE id_estudiante = ?";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idEstudiante);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Prestamo prestamo = new Prestamo(
                                rs.getInt("id"),
                                rs.getInt("id_estudiante"),
                                rs.getInt("id_libro"),
                                rs.getDate("fecha_prestamo").toLocalDate(),
                                rs.getDate("fecha_devolucion").toLocalDate(),
                                rs.getBoolean("devuelto")
                        );
                        lista.add(prestamo);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos por estudiante: " + e.getMessage());
        }
        return lista;
    }
}