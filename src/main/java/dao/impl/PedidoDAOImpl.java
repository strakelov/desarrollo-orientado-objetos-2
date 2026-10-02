package dao.impl;

import dao.PedidoDAO;
import util.ConexionBD;
import model.*;

import java.sql.*;
import java.util.*;

public class PedidoDAOImpl implements PedidoDAO {

    Random random = new Random();

    @Override
    public void registrar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo().toUpperCase());
            stmt.setString(3, pedido.getEstado().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar pedido: " + e.getMessage());
        }
    }

    @Override
    public List<Pedido> listarTodos() {
        final List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedido";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estadoStr = rs.getString("estado");

                Estado estado = Estado.valueOf(estadoStr);

                Pedido pedido = null;
                switch (tipo.toUpperCase()) {
                    case "COMIDA" -> pedido = new PedidoComida(id, direccion, random.nextInt(25), true);
                    case "ENCOMIENDA" -> pedido = new PedidoEncomienda(id, direccion, random.nextInt(40), random.nextInt(10), true);
                    case "EXPRESS" -> pedido = new PedidoExpress(id, direccion, random.nextInt(35), true);
                }

                if(pedido != null) {
                    pedido.setEstado(estado);
                    pedidos.add(pedido);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    @Override
    public void actualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo());
            stmt.setString(3, pedido.getEstado().toString());
            stmt.setInt(4, pedido.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
}