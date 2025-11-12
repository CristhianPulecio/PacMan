/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import javax.swing.SwingUtilities;
import udistrital.avanzada.parcial2.PacMan.usuario.vista.VistaUser;

/**
 * Clase {@code ControlVistaUser}
 *
 * <p>Actúa como intermediario entre la vista del usuario ({@link VistaUser})
 * y la lógica de control en el cliente Pac-Man.</p>
 *
 * <p>Se encarga de:</p>
 * <ul>
 *   <li>Registrar los listeners de los botones direccionales y el botón 
 * “Salir”.</li>
 *   <li>Ejecutar las acciones asignadas mediante {@code Runnable} cuando se 
 *       presionan los botones.</li>
 *   <li>Actualizar mensajes en la interfaz de manera segura 
 * (hilo de Swing).</li>
 * </ul>
 *
 * <p>No contiene lógica de juego, sockets ni validaciones de usuario.</p>
 *
 * <p>Principio aplicado: <b>Modelo-Vista-Controlador (MVC)</b> — separa la
 * interacción de usuario (Vista) de la lógica de red o juego.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class ControlVistaUser {

    /** Referencia a la vista principal del usuario. */
    private final VistaUser vista;

    // Acciones configurables para cada botón
    private Runnable accionArriba;
    private Runnable accionAbajo;
    private Runnable accionIzquierda;
    private Runnable accionDerecha;
    private Runnable accionSalir;

    /** Constructor: inicializa la vista y configura los eventos de 
     * interacción. */
    public ControlVistaUser() {
        this.vista = new VistaUser();
        configurarEventos();
    }

    /**
     * Asocia los botones de la vista con sus respectivas acciones Runnable.
     */
    private void configurarEventos() {

        vista.getBtnArriba().addActionListener(e -> {
            if (accionArriba != null) accionArriba.run();
        });

        vista.getBtnAbajo().addActionListener(e -> {
            if (accionAbajo != null) accionAbajo.run();
        });

        vista.getBtnIzquierda().addActionListener(e -> {
            if (accionIzquierda != null) accionIzquierda.run();
        });

        vista.getBtnDerecha().addActionListener(e -> {
            if (accionDerecha != null) accionDerecha.run();
        });

        vista.getBtnSalir().addActionListener(e -> {
            if (accionSalir != null) accionSalir.run();
        });
    }

    /**
     * Muestra un mensaje en la vista de forma segura, 
     * ejecutando la actualización en el hilo de eventos de Swing.
     *
     * @param respuesta texto que se mostrará en la interfaz del usuario.
     */
    public void mostrarMensaje(String respuesta) {
        SwingUtilities.invokeLater(() -> {
            vista.mostrarMensaje(respuesta);
        });
    }

    // ============================================================
    // SETTERS PARA LOS EVENTOS (PERFORMERS)
    // ============================================================

    /** Asigna la acción para el botón "Arriba". */
    public void setOnArriba(Runnable r) { this.accionArriba = r; }

    /** Asigna la acción para el botón "Abajo". */
    public void setOnAbajo(Runnable r) { this.accionAbajo = r; }

    /** Asigna la acción para el botón "Izquierda". */
    public void setOnIzquierda(Runnable r) { this.accionIzquierda = r; }

    /** Asigna la acción para el botón "Derecha". */
    public void setOnDerecha(Runnable r) { this.accionDerecha = r; }

    /** Asigna la acción para el botón "Salir". */
    public void setOnSalir(Runnable r) { this.accionSalir = r; }

    /** @return instancia de la vista principal del usuario. */
    public VistaUser getVista() {
        return vista;
    }
}


