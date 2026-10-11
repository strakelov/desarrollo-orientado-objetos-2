package dao;

import java.util.List;

public interface ReporteDAO {
    List<String[]> obtenerLibrosMasPrestados();
    List<String[]> obtenerHistorialEstudiante(int idEstudiante);
    List<String[]> obtenerLibrosEnPrestamo();
}