package vista;

import modelo.Estudiante;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final Usuario usuario;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        iniciarUI();
    }

    private void iniciarUI() {
        setTitle("Sistema de Biblioteca - Usuario: " + usuario.getNombre());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();

        pestanas.addTab("Préstamos y Devoluciones", new PanelPrestamos(usuario));
        pestanas.addTab("Gestión de Libros", new PanelLibros());
        pestanas.addTab("Gestión de Estudiantes", new PanelEstudiantes());
        pestanas.addTab("Reportes", new PanelReportes());

        add(pestanas, BorderLayout.CENTER);

        if(usuario instanceof Estudiante) {
            pestanas.setEnabledAt(1, false);
            pestanas.setEnabledAt(2, false);
            pestanas.setEnabledAt(3, false);
        }
    }
}
