package view;

import controller.ControladorPedidos;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class PanelPedidos extends JPanel {

    private final JTextField txtDireccion;
    private final JComboBox<TipoPedido> cmbTipo;
    private final JTable tblPedidos;
    private final DefaultTableModel modeloTabla;
    private final ControladorPedidos controlador;

    public PanelPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));

        // Formulario
        JPanel panelForm = new JPanel(new FlowLayout());
        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField(15);
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Tipo:"));
        cmbTipo = new JComboBox<>(TipoPedido.values());
        panelForm.add(cmbTipo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        panelForm.add(btnGuardar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizar());
        panelForm.add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminar());
        panelForm.add(btnEliminar);

        add(panelForm, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPedidos = new JTable(modeloTabla);
        add(new JScrollPane(tblPedidos), BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        for (Pedido p : controlador.getPedidoDAOImpl().listarTodos()) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getDireccion(), p.getTipo(), p.getEstado()});
        }
    }

    private void guardar() {
        String dir = txtDireccion.getText().trim();
        if (dir.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingresa una dirección válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoPedido tipo = (TipoPedido) cmbTipo.getSelectedItem();
        Pedido pedido = switch (tipo) {
            case COMIDA -> new PedidoComida(0, dir, 5.0, true);
            case ENCOMIENDA -> new PedidoEncomienda(0, dir, 10.0, 2.5, true);
            case EXPRESS -> new PedidoExpress(0, dir, 3.0, true);
        };

        controlador.getPedidoDAOImpl().registrar(pedido);
        txtDireccion.setText("");
        cargarDatos();
    }

    private void actualizar() {
        int fila = tblPedidos.getSelectedRow();
        if(fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un pedido de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tblPedidos.getValueAt(fila, 0);
        String direccion = txtDireccion.getText().trim();
        TipoPedido tipo = (TipoPedido) cmbTipo.getSelectedItem();

        if(!direccion.isEmpty() || tipo != null) {
            Random random = new Random(id);
            double distancia = 5.0 + random.nextInt(20);

            Pedido pedidoEditado = switch(tipo) {
                case COMIDA -> new PedidoComida(id, direccion, distancia, true);
                case ENCOMIENDA -> new PedidoEncomienda(id, direccion, distancia, 2.5, true);
                case EXPRESS -> new PedidoExpress(id, direccion, distancia, true);
            };

            controlador.getPedidoDAOImpl().actualizar(pedidoEditado);
            cargarDatos();
            txtDireccion.setText("");
        }
    }

    private void eliminar() {
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un pedido de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        controlador.getPedidoDAOImpl().eliminar(id);
        cargarDatos();
    }
}