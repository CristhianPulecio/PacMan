/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Clase {@code VistaEspera}
 *
 * <p>Interfaz gráfica minimalista que muestra el estado actual del servidor
 * Pac-Man mientras espera o atiende a los clientes.</p>
 *
 * <p>Incluye un botón "Salir" que puede asociarse a un evento desde el
 * controlador para realizar acciones adicionales, como mostrar 
 * estadísticas.</p>
 *
 * <p>Responsabilidad: mostrar mensajes de estado y permitir la interacción
 * básica sin incluir ninguna lógica de control.</p>
 *
 * @author 
 * Nicolás Arias
 */
public class VistaEspera {

    /** Ventana principal del servidor. */
    private final JFrame ventana;

    /** Etiqueta central para mostrar el estado del servidor. */
    private final JLabel lblEstado;

    /** Botón que permite salir o mostrar información adicional. */
    private final JButton btnSalir;

    /** Constructor: inicializa y configura la ventana de estado del servidor. */
    public VistaEspera() {
        ventana = new JFrame("Estado del Servidor");
        lblEstado = new JLabel("Iniciando servidor...", SwingConstants.CENTER);
        btnSalir = new JButton("Salir");

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnSalir);

        ventana.setLayout(new BorderLayout());
        ventana.add(lblEstado, BorderLayout.CENTER);
        ventana.add(panelBoton, BorderLayout.SOUTH);

        ventana.setSize(400, 150);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }


    /** Muestra el mensaje cuando el servidor se levanta correctamente. */
    public void mostrarServidorLevantado() {
        lblEstado.setText("Servidor levantado, esperando usuarios...");
    }

    /** Muestra un mensaje de error si el servidor no pudo iniciarse. */
    public void mostrarError() {
        lblEstado.setText("ERROR: No se pudo levantar el servidor.");
    }

    /**
     * Muestra el mensaje cuando un usuario se conecta correctamente.
     * @param usuario nombre del usuario conectado.
     */
    public void mostrarUsuarioConectado(String usuario) {
        lblEstado.setText("Usuario conectado: " + usuario);
    }

    /**
     * Muestra un mensaje genérico en la etiqueta central.
     * @param msg mensaje a mostrar.
     */
    public void mostrarMensaje(String msg) {
        lblEstado.setText(msg);
    }

    /** @return el botón "Salir" para asociar eventos desde el controlador. */
    public JButton getBtnSalir() {
        return btnSalir;
    }
}



