package dao;

import model.Entrega;

import java.util.List;

public interface EntregaDAO {
    void registrar(Entrega entrega);
    List<Entrega> listarTodos();
}
