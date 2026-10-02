package dao.impl;

import dao.RepartidorDAO;
import util.ConexionBD;
import model.Repartidor;

import java.sql.*;
import java.util.*;

public class RepartidorDAOImpl implements RepartidorDAO {

    @Override
    public void registrar(Repartidor repartidor) {
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, repartidor.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar repartidor: " + e.getMessage());
        }
    }

    @Override
    public List<Repartidor> listarTodos() {
        final List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT * FROM repartidor";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                repartidores.add(new Repartidor(id, nombre));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar repartidores: " + e.getMessage());
        }

        return repartidores;
    }

    @Override
    public void actualizar(Repartidor repartidor) {
        String sql = "UPDATE repartidor SET nombre = ? WHERE id = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, repartidor.getNombre());
            stmt.setInt(2, repartidor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar repartidor: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM repartidor WHERE id = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar repartidor: " + e.getMessage());
        }
    }
}