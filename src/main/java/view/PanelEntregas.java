package view;

import controller.ControladorPedidos;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class PanelEntregas extends JPanel {

    private final JComboBox<Pedido> cmbPedidos;
    private final JComboBox<Repartidor> cmbRepartidores;
    private final JTable tblEntregas;
    private final DefaultTableModel modeloTabla;
    private final ControladorPedidos controlador;

    public PanelEntregas(ControladorPedidos controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));

        // Formulario
        JPanel panelForm = new JPanel(new FlowLayout());
        cmbPedidos = new JComboBox<>();
        cmbRepartidores = new JComboBox<>();

        panelForm.add(new JLabel("Pedido:"));
        panelForm.add(cmbPedidos);

        panelForm.add(new JLabel("Repartidor:"));
        panelForm.add(cmbRepartidores);

        JButton btnRegistrar = new JButton("Registrar Entrega");
        btnRegistrar.addActionListener(e -> guardar());
        panelForm.add(btnRegistrar);

        add(panelForm, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID Entrega", "ID Pedido", "ID Repartidor", "Fecha", "Hora"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblEntregas = new JTable(modeloTabla);
        add(new JScrollPane(tblEntregas), BorderLayout.CENTER);

        cargarCombos();
        cargarDatos();
    }

    public void cargarCombos() {
        cmbPedidos.removeAllItems();
        cmbRepartidores.removeAllItems();

        for (Pedido p : controlador.getPedidoDAOImpl().listarTodos()) {
            cmbPedidos.addItem(p);
        }

        for (Repartidor r : controlador.getRepartidorDAOImpl().listarTodos()) {
            cmbRepartidores.addItem(r);
        }
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        for (Entrega e : controlador.getEntregaDAOImpl().listarTodos()) {
            modeloTabla.addRow(new Object[]{e.getId(), e.getIdPedido(), e.getIdRepartidor(), e.getFecha(), e.getHora()});
        }
    }

    private void guardar() {
        Pedido pedido = (Pedido) cmbPedidos.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidores.getSelectedItem();

        if (pedido == null || repartidor == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un pedido y un repartidor.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Entrega entrega = new Entrega(0, pedido.getId(), repartidor.getId(), LocalDate.now(), LocalTime.now());
        controlador.getEntregaDAOImpl().registrar(entrega);
        cargarDatos();
    }
}