package view;

import controller.ControladorPedidos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private final ControladorPedidos controlador;

    public VentanaPrincipal(ControladorPedidos controlador) {
        this.controlador = controlador;

        setTitle("SpeedFast");
        setSize(250, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelbtn = new JPanel(new GridLayout(3, 1, 0, 10));
        panelbtn.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelbtn.add(crearBoton("Agregar Pedido", e -> agregarPedido()));
        panelbtn.add(crearBoton("Listar Pedidos", e -> listarPedidos()));
        panelbtn.add(crearBoton("Iniciar Entrega (Consola)", e -> iniciarEntrega()));

        add(panelbtn);

        setLocationRelativeTo(null);
    }

    private JButton crearBoton(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(accion);

        return boton;
    }

    public void agregarPedido() {
        VentanaRegistroPedido ventana = new VentanaRegistroPedido(controlador);
        ventana.setVisible(true);
    }

    public void listarPedidos() {
        VentanaListaPedidos ventana = new VentanaListaPedidos(controlador);
        ventana.setVisible(true);
    }

    public void iniciarEntrega() {
        JOptionPane.showMessageDialog(this,
                "Simulación de entregas iniciada",
                "Proceso en Ejecución",
                JOptionPane.INFORMATION_MESSAGE);

        controlador.iniciarEntregas();
    }
}
