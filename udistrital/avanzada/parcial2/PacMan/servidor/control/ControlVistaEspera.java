/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import udistrital.avanzada.parcial2.PacMan.servidor.vista.VistaEspera;

/**
 * ControlVistaEspera
 * 
 * <p>Clase controladora asociada a la vista principal del servidor
 * (pantalla de espera de clientes). Su responsabilidad es manejar los eventos
 * de la interfaz y delegar las acciones correspondientes, sin incluir lógica
 * del servidor ni de conexión.</p>
 * 
 * <p>En particular, gestiona el botón "Salir", cuyo evento se asocia
 * a una acción (Runnable) configurable desde la capa superior.</p>
 * 
 * <p>Aplica los principios de <b>MVC</b> (Modelo-Vista-Controlador),
 * manteniendo la vista libre de lógica de negocio y asegurando bajo acoplamiento.</p>
 *
 * @author Cristhian Pulecio
 */
public class ControlVistaEspera {

    /** Vista asociada al panel de espera del servidor */
    private final VistaEspera vista;

    /** Acción a ejecutar al presionar el botón "Salir" */
    private Runnable accionSalir;

    /**
     * Constructor principal.
     * 
     * <p>Inicializa la vista y configura los listeners
     * para los eventos de interfaz (botón "Salir").</p>
     */
    public ControlVistaEspera() {
        this.vista = new VistaEspera();
        configurarEventos();
    }

    /**
     * Configura los eventos de los botones de la vista.
     * 
     * <p>En este caso, el botón "Salir" ejecuta la acción
     * definida mediante {@link #setOnSalir(Runnable)}.</p>
     */
    private void configurarEventos() {
        vista.getBtnSalir().addActionListener(e -> {
            if (accionSalir != null) accionSalir.run();
        });
    }

    /**
     * Muestra en la vista un mensaje indicando que el servidor
     * fue levantado exitosamente.
     */
    public void mostrarServidorLevantado() {
        vista.mostrarServidorLevantado();
    }

    /**
     * Muestra el nombre del usuario que se conectó correctamente.
     * 
     * @param usuario nombre del usuario conectado.
     */
    public void mostrarUsuarioConectado(String usuario) {
        vista.mostrarUsuarioConectado(usuario);
    }

    /**
     * Muestra un mensaje de error general en la vista
     * cuando el servidor no pudo iniciarse correctamente.
     */
    public void mostrarError() {
        vista.mostrarError();
    }

    /**
     * Muestra un mensaje arbitrario en la interfaz del servidor.
     * 
     * @param msg texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String msg) {
        vista.mostrarMensaje(msg);
    }

    /**
     * Asigna la acción a ejecutar al presionar el botón "Salir".
     * 
     * <p>Generalmente, esta acción consulta al {@link Servidor}
     * para mostrar el mejor jugador o cerrar el programa.</p>
     * 
     * @param r acción a ejecutar.
     */
    public void setOnSalir(Runnable r) {
        this.accionSalir = r;
    }
}


