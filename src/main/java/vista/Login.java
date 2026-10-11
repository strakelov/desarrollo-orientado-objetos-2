package vista;

import dao.UsuarioDAO;
import dao.impl.UsuarioDAOImpl;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private final UsuarioDAO usuarioDAO;

    public Login() {
        this.usuarioDAO = new UsuarioDAOImpl();
        iniciarUI();
    }

    private void iniciarUI() {
        setTitle("Biblioteca Escolar - Autenticación");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCorreo = new JLabel("Correo electrónico:");
        txtCorreo = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        JPanel panelBoton = new JPanel();
        JButton btnIngresar = new JButton("Iniciar Sesión");

        panelFormulario.add(lblCorreo);
        panelFormulario.add(txtCorreo);

        panelFormulario.add(lblContrasena);
        panelFormulario.add(txtContrasena);

        panelBoton.add(btnIngresar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        btnIngresar.addActionListener(this::ejecutarLogin);
    }

    private void ejecutarLogin(ActionEvent e) {
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor complete todos los campos.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuarioAutenticado = usuarioDAO.autenticar(correo, contrasena);

        if (usuarioAutenticado != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido/a, " + usuarioAutenticado.getNombre(),
                    "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            new VentanaPrincipal(usuarioAutenticado).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Credenciales incorrectas.",
                    "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }
}