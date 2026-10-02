package view;

import controller.ControladorPedidos;
import model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelRepartidores extends JPanel {

    private final JTextField txtNombre;
    private final JTable tblRepartidores;
    private final DefaultTableModel modeloTabla;
    private final ControladorPedidos controlador;

    public PanelRepartidores(ControladorPedidos controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));

        // Formulario
        JPanel panelForm = new JPanel(new FlowLayout());
        panelForm.add(new JLabel("Nombre Repartidor:"));
        txtNombre = new JTextField(15);
        panelForm.add(txtNombre);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizar());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminar());

        panelForm.add(btnGuardar);
        panelForm.add(btnActualizar);
        panelForm.add(btnEliminar);

        add(panelForm, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRepartidores = new JTable(modeloTabla);
        add(new JScrollPane(tblRepartidores), BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        for (Repartidor r : controlador.getRepartidorDAOImpl().listarTodos()) {
            modeloTabla.addRow(new Object[]{r.getId(), r.getNombre()});
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        if(nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingresa un nombre válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controlador.getRepartidorDAOImpl().registrar(new Repartidor(0, nombre));
        txtNombre.setText("");
        cargarDatos();
    }

    private void actualizar() {
        int fila = tblRepartidores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un repartidor de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nuevoNombre = txtNombre.getText().trim();

        if (!nuevoNombre.trim().isEmpty()) {
            Repartidor repartidorEditado = new Repartidor(id, nuevoNombre.trim());
            controlador.getRepartidorDAOImpl().actualizar(repartidorEditado);
            cargarDatos();
            txtNombre.setText("");
        }
    }

    private void eliminar() {
        int fila = tblRepartidores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un repartidor de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        controlador.getRepartidorDAOImpl().eliminar(id);
        cargarDatos();
    }
}