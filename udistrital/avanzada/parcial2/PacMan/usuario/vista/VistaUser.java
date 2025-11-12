/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 * Clase {@code VistaUser}
 *
 * <p>Representa la interfaz gráfica del cliente Pac-Man.</p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Mostrar los botones de control de movimiento (Arriba, Abajo, Izquierda, 
 * Derecha).</li>
 *   <li>Proveer un botón adicional para “Salir”.</li>
 *   <li>Mostrar los mensajes recibidos desde el servidor en un área de 
 * texto.</li>
 * </ul>
 *
 * <p><b>Características de diseño:</b></p>
 * <ul>
 *   <li>Usa {@link GridLayout} para organizar los botones de movimiento.</li>
 *   <li>El área de mensajes no es editable por el usuario.</li>
 *   <li>No contiene lógica del juego, solo elementos de visualización.</li>
 * </ul>
 *
 * @author 
 * Nicolás Arias
 */
public class VistaUser extends JFrame {

    private final JButton btnArriba;
    private final JButton btnAbajo;
    private final JButton btnIzquierda;
    private final JButton btnDerecha;
    private final JButton btnSalir;


    private final JTextArea areaMensajes;

    /**
     * Constructor principal.
     * <p>Configura la ventana del cliente, inicializa los botones
     * y el área de mensajes.</p>
     */
    public VistaUser() {
        super("Cliente Pac-Man");
        setSize(400, 450);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel de botones con distribución en cuadrícula
        JPanel panelBotones = new JPanel(new GridLayout(3, 3));

        btnArriba = new JButton("Arriba");
        btnAbajo = new JButton("Abajo");
        btnIzquierda = new JButton("Izquierda");
        btnDerecha = new JButton("Derecha");
        btnSalir = new JButton("Salir");

        // Disposición tipo cruceta para simular control direccional
        panelBotones.add(new JPanel());
        panelBotones.add(btnArriba);
        panelBotones.add(new JPanel());

        panelBotones.add(btnIzquierda);
        panelBotones.add(btnSalir);
        panelBotones.add(btnDerecha);

        panelBotones.add(new JPanel());
        panelBotones.add(btnAbajo);
        panelBotones.add(new JPanel());

        // Área de mensajes para mostrar respuestas del servidor
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);

        // Agregar los componentes a la ventana
        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(areaMensajes), BorderLayout.CENTER);

        setVisible(true);
    }


    public JButton getBtnArriba() { return btnArriba; }
    public JButton getBtnAbajo() { return btnAbajo; }
    public JButton getBtnIzquierda() { return btnIzquierda; }
    public JButton getBtnDerecha() { return btnDerecha; }
    public JButton getBtnSalir() { return btnSalir; }

    /**
     * Muestra un mensaje recibido del servidor en el área de texto.
     *
     * @param msg mensaje que será mostrado al usuario.
     */
    public void mostrarMensaje(String msg) {
        areaMensajes.append(msg + "\n");
    }
}


