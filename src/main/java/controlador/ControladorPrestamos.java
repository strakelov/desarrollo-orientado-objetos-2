package controlador;

import dao.PrestamoDAO;
import dao.impl.PrestamoDAOImpl;
import modelo.Prestamo;

import javax.swing.*;
import java.util.function.Consumer;

public class ControladorPrestamos {

    private final PrestamoDAO prestamoDAO;

    /**
     * Controlador que gestiona la lógica de negocio de los préstamos y devoluciones.
     * Aísla la interfaz gráfica de las operaciones DAO.
     */
    public ControladorPrestamos() {
        this.prestamoDAO = new PrestamoDAOImpl();
    }

    /**
     * Registra un nuevo préstamo en la BD ejecutando la operación en un hilo secundario.
     * @param prestamo Objeto instanciado con los datos recolectados en la vista.
     * @param alFinalizar Interfaz que recibe un booleano indicando exito (true) o fracaso (false).
     *                    Se asegura de delegar la tarea visual al hilo principal.
     */
    public void registrarPrestamoAsincrono(Prestamo prestamo, Consumer<Boolean> alFinalizar) {
        Thread hiloPrestamo = new Thread(() -> {
            boolean exito = prestamoDAO.registrarPrestamo(prestamo);

            if (alFinalizar != null) {
                SwingUtilities.invokeLater(() -> alFinalizar.accept(exito));
            }
        });

        hiloPrestamo.start();
    }

    /**
     * Registra la devolución de un libro actualizando su estado y stock asincronicamente.
     * @param idPrestamo  Identificador del prestamo a finalizar.
     * @param idLibro     Identificador del libro para reponer stock.
     * @param alFinalizar Interfaz para manejar el resultado de la lista en el hilo principal.
     */
    public void registrarDevolucionAsincrona(int idPrestamo, int idLibro, Consumer<Boolean> alFinalizar) {
        Thread hiloDevolucion = new Thread(() -> {
            boolean exito = prestamoDAO.registrarDevolucion(idPrestamo, idLibro);

            if (alFinalizar != null) {
                SwingUtilities.invokeLater(() -> alFinalizar.accept(exito));
            }
        });
        hiloDevolucion.start();
    }
}