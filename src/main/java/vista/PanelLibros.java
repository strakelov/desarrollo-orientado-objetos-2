package vista;

import dao.LibroDAO;
import dao.impl.LibroDAOImpl;
import modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelLibros extends JPanel {

    private JTextField txtId, txtTitulo, txtAutor, txtIsbn, txtEditorial, txtIdCategoria, txtStock;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private final LibroDAO libroDAO = new LibroDAOImpl();

    public PanelLibros() {
        iniciarUI();
        cargarTabla();
    }

    private void iniciarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel superior: Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 10, 10));

        panelFormulario.add(new JLabel("ID (lectura):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        panelFormulario.add(txtEditorial);

        panelFormulario.add(new JLabel("ID Categoría:"));
        txtIdCategoria = new JTextField();
        panelFormulario.add(txtIdCategoria);

        panelFormulario.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);

        add(panelFormulario, BorderLayout.NORTH);

        // Panel central: Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "ISBN", "Editorial", "ID Cat", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaLibros = new JTable(modeloTabla);
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        // Panel inferior: Botones
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

        btnRegistrar.addActionListener(e -> registrarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaLibros.getSelectedRow() != -1) {
                cargarDatosEnFormulario();
            }
        });
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Libro> libros = libroDAO.listarTodos();
        for (Libro libro : libros) {
            modeloTabla.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getIdCategoria(),
                    libro.getStock()
            });
        }
    }

    private void cargarDatosEnFormulario() {
        int fila = tablaLibros.getSelectedRow();
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtTitulo.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtAutor.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtIsbn.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtEditorial.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtIdCategoria.setText(modeloTabla.getValueAt(fila, 5).toString());
        txtStock.setText(modeloTabla.getValueAt(fila, 6).toString());
    }

    private void registrarLibro() {
        try {
            Libro libro = leerDatosFormulario(0);
            if (libroDAO.registrar(libro)) {
                JOptionPane.showMessageDialog(this, "Libro registrado exitosamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar el libro.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Asegúrese de ingresar números válidos en ID Categoría y Stock.",
                    "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarLibro() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla a actualizar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            Libro libro = leerDatosFormulario(id);
            if (libroDAO.actualizar(libro)) {
                JOptionPane.showMessageDialog(this, "Libro actualizado exitosamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar el libro.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Verifiqua los formatos numéricos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla a eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este libro?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            if (libroDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Libro eliminado.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar el libro.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Libro leerDatosFormulario(int id) {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String isbn = txtIsbn.getText().trim();
        String editorial = txtEditorial.getText().trim();
        int idCategoria = Integer.parseInt(txtIdCategoria.getText().trim());
        int stock = Integer.parseInt(txtStock.getText().trim());

        return new Libro(id, titulo, autor, isbn, editorial, idCategoria, stock);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtIdCategoria.setText("");
        txtStock.setText("");
        tablaLibros.clearSelection();
    }
}