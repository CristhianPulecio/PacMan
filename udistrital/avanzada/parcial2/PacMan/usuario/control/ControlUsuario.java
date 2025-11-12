/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import udistrital.avanzada.parcial2.PacMan.usuario.modelo.Usuario;

/**
 * Clase {@code ControlUsuario}
 *
 * <p>Encargada de construir y administrar un objeto {@link Usuario}
 * a partir de la información almacenada en el archivo .properties.</p>
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Crear un objeto {@code Usuario} usando {@code ControlProperties}.</li>
 *   <li>Proveer acceso a los datos del usuario para otras capas del 
 * cliente.</li>
 * </ul>
 *
 * <p>No maneja GUI, sockets ni impresión en consola.</p>
 *
 * <p>Principio aplicado: <b>Single Responsibility (SRP)</b>.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class ControlUsuario {

    /** Objeto de dominio que representa al usuario actual. */
    private final Usuario usuario;

    /**
     * Constructor que crea un usuario con los datos leídos desde
     * el archivo .properties.
     *
     * @param controlProperties instancia de {@code ControlProperties}
     *                          que contiene los valores cargados.
     */
    public ControlUsuario(ControlProperties controlProperties) {
        String nombre = controlProperties.getUsuario();
        String contrasena = controlProperties.getContrasena();
        this.usuario = new Usuario(nombre, contrasena);
    }

    /** @return nombre del usuario cargado desde el archivo properties. */
    public String getUsuario() {
        return usuario.getUsuario();
    }

    /** @return contraseña del usuario cargada desde el archivo properties. */
    public String getContrasena() {
        return usuario.getContrasena();
    }
}


