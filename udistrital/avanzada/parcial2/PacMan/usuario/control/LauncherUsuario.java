/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import java.io.IOException;

/**
 * Clase {@code LauncherUsuario}
 *
 * <p>Punto de entrada principal del cliente Pac-Man.</p>
 *
 * <p>Su única función es iniciar el proceso de conexión al servidor
 * creando una instancia de {@link UsuarioConexion}, lo que dispara
 * toda la secuencia de inicialización del cliente.</p>
 *
 * <p>No implementa lógica adicional, impresión ni interfaz gráfica directa.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class LauncherUsuario {
    
    /**
     * Método principal (entry point).
     *
     * @param args argumentos de línea de comandos (no utilizados).
     * @throws IOException si ocurre un error en la conexión inicial.
     */
    public static void main(String[] args) throws IOException {
        // Inicia el flujo completo de conexión y GUI del usuario.
        new UsuarioConexion();
    }
}

