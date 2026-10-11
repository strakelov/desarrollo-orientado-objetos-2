package dao;

import modelo.Estudiante;
import java.util.List;

public interface EstudianteDAO {
    boolean registrar(Estudiante estudiante);
    boolean actualizar(Estudiante estudiante);
    boolean eliminar(int id);
    List<Estudiante> listarTodos();
}