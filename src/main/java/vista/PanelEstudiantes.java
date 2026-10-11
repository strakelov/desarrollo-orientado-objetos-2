package vista;

import dao.EstudianteDAO;
import dao.impl.EstudianteDAOImpl;
import modelo.Estudiante;
import modelo.Rut;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelEstudiantes extends JPanel {
    private JTextField txtId, txtNombre, txtRut, txtCurso, txtCorreo;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    private final EstudianteDAO estudianteDAO = new EstudianteDAOImpl();

    public PanelEstudiantes() {
        iniciarUI();
        cargarTabla();
    }

    private void iniciarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 10, 10));

        panelFormulario.add(new JLabel("ID (Lectura):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("RUT (Ej: 12345678-K):"));
        txtRut = new JTextField();
        panelFormulario.add(txtRut);

        panelFormulario.add(new JLabel("Curso:"));
        txtCurso = new JTextField();
        panelFormulario.add(txtCurso);

        panelFormulario.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "RUT", "Curso", "Correo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEstudiantes = new JTable(modeloTabla);
        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar Campos");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarEstudiante());
        btnActualizar.addActionListener(e -> actualizarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEstudiantes.getSelectedRow() != -1) {
                cargarDatosEnFormulario();
            }
        });
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Estudiante> estudiantes = estudianteDAO.listarTodos();
        for (Estudiante est : estudiantes) {
            modeloTabla.addRow(new Object[]{
                    est.getId(),
                    est.getNombre(),
                    est.getRut().getNumero(),
                    est.getCurso(),
                    est.getCorreo()
            });
        }
    }

    private void cargarDatosEnFormulario() {
        int fila = tablaEstudiantes.getSelectedRow();
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtRut.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtCurso.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());
    }

    private void registrarEstudiante() {
        try {
            Estudiante estudiante = leerDatosFormulario(0);
            if (estudianteDAO.registrar(estudiante)) {
                JOptionPane.showMessageDialog(this, "Estudiante registrado exitosamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar el estudiante.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEstudiante() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.");
            return;
        }
        try {
            int id = Integer.parseInt(txtId.getText());
            Estudiante estudiante = leerDatosFormulario(id);
            if (estudianteDAO.actualizar(estudiante)) {
                JOptionPane.showMessageDialog(this, "Estudiante actualizado exitosamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar el estudiante.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEstudiante() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este estudiante?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            if (estudianteDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Estudiante eliminado.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar el estudiante.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Estudiante leerDatosFormulario(int id) throws Exception {
        String nombre = txtNombre.getText().trim();
        String rutStr = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || rutStr.isEmpty() || curso.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos son obligatorios.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        Rut rut = new Rut(rutStr);
        return new Estudiante(id, rut, nombre, correo, curso);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");
        tablaEstudiantes.clearSelection();
    }
}