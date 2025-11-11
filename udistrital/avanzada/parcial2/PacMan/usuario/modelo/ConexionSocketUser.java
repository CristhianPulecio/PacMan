/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.modelo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ConexionSocket (Cliente)
 *
 * RESPONSABILIDAD ÚNICA:
 *  - Establecer la conexión con el servidor usando IP y puerto.
 *  - Proveer InputStream y OutputStream.
 *  - Cerrar el socket y streams.
 *
 * NO envía comandos.
 * NO recibe mensajes.
 * NO imprime.
 * NO maneja hilos.
 * 
 * Es un simple contenedor de la conexión.
 *
 * author USER
 */
public class ConexionSocketUser {

    private final String ip;
    private final int puerto;

    private Socket socket;

    /**
     * Constructor.
     *
     * @param ip dirección IP del servidor
     * @param puerto número de puerto del servidor
     */
    public ConexionSocketUser(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    /**
     * Crea la conexión con el servidor.
     *
     * @throws IOException si falla la conexión
     */
    public void conectar() throws IOException {
        socket = new Socket(ip, puerto);
    }

    /**
     * Retorna el InputStream asociado al socket.
     */
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    /**
     * Retorna el OutputStream asociado al socket.
     */
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    /**
     * Cierra el socket y sus streams.
     */
    public void cerrar() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            // No imprimir
        }
    }
}

