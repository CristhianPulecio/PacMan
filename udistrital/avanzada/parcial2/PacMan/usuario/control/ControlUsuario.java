/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import udistrital.avanzada.parcial2.PacMan.usuario.modelo.Usuario;

/**
 * ControlUsuario
 *
 * RESPONSABILIDADES:
 * - Crear el objeto Usuario a partir de los datos almacenados en ControlProperties.
 * - Proveer acceso seguro al objeto Usuario para otras capas del módulo cliente.
 *
 * NO imprime nada.
 * NO crea GUI.
 * NO maneja sockets.
 *
 * author USER
 */
public class ControlUsuario {

    private final Usuario usuario;

    /**
     * Constructor:
     * Recibe ControlProperties para obtener usuario y contraseña cargados
     * desde el archivo .properties seleccionado por el usuario.
     *
     * @param controlProperties controlador encargado de leer el properties.
     */
    public ControlUsuario(ControlProperties controlProperties) {

        String nombre = controlProperties.getUsuario();
        String contrasena = controlProperties.getContrasena();

        this.usuario = new Usuario(nombre, contrasena);
    }

    /**
     * Retorna el usuario cargado desde el properties.
     */
    public String getUsuario() {
        return usuario.getUsuario();
    }

    public String getContrasena() {
        return usuario.getContrasena();
    }
}

