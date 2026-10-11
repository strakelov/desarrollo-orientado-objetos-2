package dao;

import modelo.Libro;

import java.util.List;

public interface LibroDAO {
    boolean registrar(Libro libro);
    List<Libro> listarTodos();
    boolean actualizar(Libro libro);
    boolean eliminar(int id);
}