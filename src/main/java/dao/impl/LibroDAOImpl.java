package dao.impl;

import dao.LibroDAO;
import modelo.Libro;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public boolean registrar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, categoria, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, libro.getTitulo());
                stmt.setString(2, libro.getAutor());
                stmt.setString(3, libro.getIsbn());
                stmt.setString(4, libro.getEditorial());
                stmt.setInt(5, libro.getIdCategoria());
                stmt.setInt(6, libro.getStock());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Libro> listarTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    Libro libro = new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("isbn"),
                            rs.getString("editorial"),
                            rs.getInt("id_categoria"),
                            rs.getInt("stock")
                    );
                    libros.add(libro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al Listar libros: " + e.getMessage());
        }
        return libros;
    }

    @Override
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, editorial = ?, id_categoria = ?, stock = ? WHERE id = ?";
        try {
            Connection conn = ConexionBD.getInstance().getConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, libro.getTitulo());
                stmt.setString(2, libro.getAutor());
                stmt.setString(3, libro.getIsbn());
                stmt.setString(4, libro.getEditorial());
                stmt.setInt(5, libro.getIdCategoria());
                stmt.setInt(6, libro.getStock());
                stmt.setInt(7, libro.getId());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String foreignKey = "DELETE FROM prestamos WHERE id_libro = ?";
        String primaryKey = "DELETE FROM libros WHERE id = ?";
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
            System.err.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
}