package vista;

import dao.ReporteDAO;
import dao.impl.ReporteDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelReportes extends JPanel {
    private JComboBox<String> cbxTipoReporte;
    private JButton btnGenerar;
    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;

    private final ReporteDAO reporteDAO = new ReporteDAOImpl();

    public PanelReportes() {
        iniciarUI();
    }

    private void iniciarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelControles.add(new JLabel("Seleccione el reporte:"));

        cbxTipoReporte = new JComboBox<>(new String[]{
                "Libros más prestados",
                "Historial por estudiante",
                "Libros actualmente en préstamo"
        });
        panelControles.add(cbxTipoReporte);

        btnGenerar = new JButton("Generar Reporte");
        panelControles.add(btnGenerar);

        add(panelControles, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        tablaReportes = new JTable(modeloTabla);
        add(new JScrollPane(tablaReportes), BorderLayout.CENTER);

        btnGenerar.addActionListener(e -> generarReporteSeleccionado());
    }

    private void generarReporteSeleccionado() {
        int indiceSeleccionado = cbxTipoReporte.getSelectedIndex();
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);

        switch (indiceSeleccionado) {
            case 0:
                cargarReporteLibrosMasPrestados();
                break;
            case 1:
                cargarReporteHistorialEstudiante();
                break;
            case 2:
                cargarReporteLibrosEnPrestamo();
                break;
        }
    }

    private void cargarReporteLibrosMasPrestados() {
        modeloTabla.setColumnIdentifiers(new String[]{"Título del Libro", "Total de Préstamos"});
        List<String[]> datos = reporteDAO.obtenerLibrosMasPrestados();
        for (String[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    private void cargarReporteHistorialEstudiante() {
        String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del estudiante:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int idEstudiante = Integer.parseInt(idStr.trim());
            modeloTabla.setColumnIdentifiers(new String[]{"ID Préstamo", "Libro", "Fecha Préstamo", "Fecha Devolución", "Estado"});
            List<String[]> datos = reporteDAO.obtenerHistorialEstudiante(idEstudiante);

            if (datos.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontraron registros para el estudiante indicado.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (String[] fila : datos) {
                    modeloTabla.addRow(fila);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarReporteLibrosEnPrestamo() {
        modeloTabla.setColumnIdentifiers(new String[]{"ID Préstamo", "Estudiante", "Libro", "Fecha Préstamo", "Fecha Devolución"});
        List<String[]> datos = reporteDAO.obtenerLibrosEnPrestamo();
        for (String[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }
}