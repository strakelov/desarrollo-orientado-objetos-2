package view;

import controller.ControladorPedidos;
import model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private final DefaultTableModel modelo;
    private final JTable tblPedidos;
    private final ControladorPedidos controlador;

    public VentanaListaPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;

        setTitle("Registro de Pedidos");
        setSize(550, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelBoton = new JPanel();
        JButton btnRecargar = new JButton("Recargar");
        btnRecargar.addActionListener(e -> recargarLista());
        panelBoton.add(btnRecargar);

        String[] columnas = {"ID", "Tipo", "Dirección", "Distancia (KM)", "Estado"};

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int rows, int column) {
                return false;
            }
        };

        tblPedidos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tblPedidos);

        add(scroll);
        add(panelBoton, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        for(Pedido p : controlador.getPedidos()) {
            Object[] fila = {
                    p.getIdPedido(),
                    p.getTipoPedido(),
                    p.getDireccionEntrega(),
                    p.getDistanciaKm(),
                    p.getEstado()
            };
            modelo.addRow(fila);
        }
    }

    public void recargarLista() {
        modelo.setRowCount(0);
        cargarDatosTabla();
    }
}
