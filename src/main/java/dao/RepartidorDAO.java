package dao;

import model.Repartidor;

import java.util.List;

public interface RepartidorDAO {
    void registrar(Repartidor repartidor);
    List<Repartidor> listarTodos();
    void actualizar(Repartidor repartidor);
    void eliminar(int id);
}
