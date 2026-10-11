package vista;

import controlador.ControladorPrestamos;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PanelPrestamos extends JPanel {

    private final Usuario usuarioActual;
    private JTextField txtIdLibro, txtIdEstudiante;
    private JButton btnPrestar, btnDevolver;
    private JTextArea txtConsola;

    private final ControladorPrestamos controlador = new ControladorPrestamos();

    public PanelPrestamos(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        iniciarUI();
    }

    private void iniciarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));

        JLabel lblIdEstudiante = new JLabel("ID Estudiante:");
        txtIdEstudiante = new JTextField(5);
        panelFormulario.add(lblIdEstudiante);
        panelFormulario.add(txtIdEstudiante);

        panelFormulario.add(new JLabel("ID Libro:"));
        txtIdLibro = new JTextField(10);
        panelFormulario.add(txtIdLibro);

        btnPrestar = new JButton("Registrar Préstamo");
        btnDevolver = new JButton("Registrar Devolución");

        panelFormulario.add(btnPrestar);
        panelFormulario.add(btnDevolver);

        add(panelFormulario, BorderLayout.NORTH);

        // TextArea para mostrar mensajes o historial
        txtConsola = new JTextArea("Esperando acción...\n");
        txtConsola.setEditable(false);
        add(new JScrollPane(txtConsola), BorderLayout.CENTER);

        if (usuarioActual instanceof Estudiante) {
            lblIdEstudiante.setVisible(false);
            txtIdEstudiante.setVisible(false);
            btnDevolver.setEnabled(false);
        }

        btnPrestar.addActionListener(e -> registrarPrestamo());
        btnDevolver.addActionListener(e -> registrarDevolucion());
    }

    private void registrarPrestamo() {
        int idEstudiante;

        if (usuarioActual instanceof Estudiante) {
            idEstudiante = usuarioActual.getId();
        } else {
            try {
                idEstudiante = Integer.parseInt(txtIdEstudiante.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ingresa un ID de estudiante válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int idLibro;
        try {
            idLibro = Integer.parseInt(txtIdLibro.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingresa un ID de libro válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = fechaPrestamo.plusDays(7);
        Prestamo nuevoPrestamo = new Prestamo(0, idEstudiante, idLibro, fechaPrestamo, fechaDevolucion, false);

        txtConsola.append("Procesando préstamo en segundo plano...\n");
        btnPrestar.setEnabled(false);

        controlador.registrarPrestamoAsincrono(nuevoPrestamo, (exito) -> {
            btnPrestar.setEnabled(true);
            if (exito) {
                txtConsola.append("Préstamo exitoso. Devolución esperada: " + fechaDevolucion + "\n");
                JOptionPane.showMessageDialog(this, "Préstamo registrado exitosamente.");
                txtIdLibro.setText("");
            } else {
                txtConsola.append("Fallo al registrar préstamo. Verifica stock o ID.\n");
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo realizar el préstamo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void registrarDevolucion() {
        String idPrestamoStr = JOptionPane.showInputDialog(this, "Ingresa el ID del préstamo a devolver:");
        if (idPrestamoStr == null || idPrestamoStr.trim().isEmpty()) {
            return;
        }

        String idLibroStr = txtIdLibro.getText().trim();
        if (idLibroStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID del libro en el campo principal.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPrestamo;
        int idLibro;

        try {
            idPrestamo = Integer.parseInt(idPrestamoStr.trim());
            idLibro = Integer.parseInt(idLibroStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Los IDs deben ser numéricos.",
                    "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        txtConsola.append("Procesando devolución en segundo plano...\n");
        btnDevolver.setEnabled(false);

        controlador.registrarDevolucionAsincrona(idPrestamo, idLibro, (exito) -> {
            btnDevolver.setEnabled(true);
            if (exito) {
                txtConsola.append("Devolución del préstamo #" + idPrestamo + " registrada correctamente.\n");
                JOptionPane.showMessageDialog(this, "Devolución procesada con éxito.");
                txtIdLibro.setText("");
            } else {
                txtConsola.append("Error: No se pudo registrar la devolución. Verifique los IDs.\n");
                JOptionPane.showMessageDialog(
                        this,
                        "Fallo al procesar la devolución.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}