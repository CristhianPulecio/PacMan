/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ConexionSocket:
 * - Mantiene únicamente la conexión al socket.
 * - Ofrece acceso al InputStream y OutputStream crudos.
 * - No crea DataInputStream ni DataOutputStream.
 * - No valida, no procesa datos, no imprime.
 *
 * author USER
 */
public class ConexionSocketServer {

    private final Socket socket;

    public ConexionSocketServer(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void cerrar() {
        try { socket.close(); } catch (Exception e) {}
    }
}

