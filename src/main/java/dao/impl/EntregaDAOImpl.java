package dao.impl;

import dao.EntregaDAO;
import util.ConexionBD;
import model.Entrega;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAOImpl implements EntregaDAO {

    @Override
    public void registrar(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setDate(3, Date.valueOf(entrega.getFecha()));
            stmt.setTime(4, Time.valueOf(entrega.getHora()));

            stmt.executeUpdate();
            System.out.println("Entrega registrada exitosamente en BD.");

        } catch (SQLException e) {
            System.err.println("Error al registrar entrega: " + e.getMessage());
        }
    }

    @Override
    public List<Entrega> listarTodos() {
        List<Entrega> entregas = new ArrayList<>();

        String sql = "SELECT * FROM entrega";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt("id");
                int idPedido = rs.getInt("id_pedido");
                int idRepartidor = rs.getInt("id_repartidor");

                Date sqlDate = rs.getDate("fecha");
                Time sqlTime = rs.getTime("hora");

                LocalDate fecha = (sqlDate != null) ? sqlDate.toLocalDate() : null;
                LocalTime hora = (sqlTime != null) ? sqlTime.toLocalTime() : null;

                entregas.add(new Entrega(id, idPedido, idRepartidor, fecha, hora));
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar entrega: " + e.getMessage());
        }

        return entregas;
    }
}