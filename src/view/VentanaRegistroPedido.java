package view;

import controller.ControladorPedidos;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private final JTextField txtIdentificador;
    private final JTextField txtDireccion;
    private final JComboBox cmbTipo;

    private final ControladorPedidos controladorPedidos;

    public VentanaRegistroPedido(ControladorPedidos controladorPedidos) {
        this.controladorPedidos = controladorPedidos;

        setTitle("Registro de Pedidos");
        setSize(350, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15,15,15,15));

        cmbTipo = new JComboBox<>(TipoPedido.values());

        txtIdentificador = new JTextField();
        txtDireccion = new JTextField();

        panelCampos.add(new JLabel("ID: "));
        panelCampos.add(txtIdentificador);

        panelCampos.add(new JLabel("Direccion: "));
        panelCampos.add(txtDireccion);

        panelCampos.add(new JLabel("TipoPedido: "));
        panelCampos.add(cmbTipo);

        JPanel panelboton = new JPanel();
        panelboton.setBorder(new EmptyBorder(0,15,15,15));

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarPedido());

        panelboton.add(btnGuardar);

        add(panelCampos, BorderLayout.NORTH);
        add(panelboton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public void guardarPedido() {
        String txtId = validarTexto(txtIdentificador.getText(), "Ingresa un ID");
        if(txtId == null) return;

        int id;
        try {
            id = Integer.parseInt(txtId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de ID inválido. Solo numeros.",
                    "Formato Inválido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String direccion = validarTexto(txtDireccion.getText(), "Ingresa una Dirección");
        if(direccion == null) return;

        TipoPedido tipoSeleccionado = (TipoPedido) cmbTipo.getSelectedItem();

        double distancia = controladorPedidos.generarNumeroAleatorio();
        double peso = controladorPedidos.generarNumeroAleatorio();

        Pedido pedido = switch(tipoSeleccionado) {
            case COMIDA -> new PedidoComida(id, direccion, distancia, true);
            case ENCOMIENDA -> new PedidoEncomienda(id, direccion, distancia, peso, true);
            case EXPRESS -> new PedidoExpress(id, direccion, distancia, true);
        };

        controladorPedidos.agregarPedido(pedido);
        JOptionPane.showMessageDialog(this, "Pedido agregado correctamente.");
        dispose();
    }

    private String validarTexto(String texto, String error) {
        if(texto == null || texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    error,
                    "Campo Vacio",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return texto.trim();
    }
}