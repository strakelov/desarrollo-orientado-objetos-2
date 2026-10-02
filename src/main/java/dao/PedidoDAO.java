package dao;

import model.Pedido;

import java.util.List;

public interface PedidoDAO {
    void registrar(Pedido pedido);
    List<Pedido> listarTodos();
    void actualizar(Pedido pedido);
    void eliminar(int id);
}
