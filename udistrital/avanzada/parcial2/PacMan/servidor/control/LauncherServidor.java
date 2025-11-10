/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Clase de entrada principal de la aplicación.
 * Inicia el controlador principal del juego.
 *
 * @author
 * Cristhian Pulecio
 */
public class LauncherServidor {

    public static void main(String[] args) throws IOException, SQLException {
        // Inicia el flujo completo del sistema
        new Servidor();
    }
}

