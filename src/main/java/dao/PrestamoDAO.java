package dao;

import modelo.Prestamo;

import java.util.List;

public interface PrestamoDAO {
    boolean registrarPrestamo(Prestamo prestamo);
    boolean registrarDevolucion(int idPrestamo, int idLibro);
    List<Prestamo> listarTodos();
    List<Prestamo> listarPorEstudiante(int idEstudiante);
}
