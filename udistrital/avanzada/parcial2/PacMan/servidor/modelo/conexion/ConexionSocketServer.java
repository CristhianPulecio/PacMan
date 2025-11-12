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
 * Clase {@code ConexionSocketServer}
 * 
 * <p>Encargada exclusivamente de gestionar la conexión de un socket 
 * entre el servidor y un cliente.</p>
 * 
 * <p>Esta clase no realiza validaciones, no procesa datos y no imprime
 * mensajes en consola. Su única responsabilidad es ofrecer acceso directo
 * a los flujos de entrada y salida del socket.</p>
 * 
 * <p>Principio aplicado: <b>Single Responsibility (SRP)</b></p>
 * 
 * <p>Uso típico:</p>
 * <pre>
 * ConexionSocketServer conexion = new ConexionSocketServer(socket);
 * InputStream in = conexion.getInputStream();
 * OutputStream out = conexion.getOutputStream();
 * </pre>
 * 
 * <p>Esta clase es utilizada por {@code ThreadServidor} para establecer
 * la comunicación entre servidor y cliente.</p>
 * 
 * @author 
 * Cristhian Pulecio
 */
public class ConexionSocketServer {

    /** Socket que representa la conexión establecida con el cliente. */
    private final Socket socket;

    /**
     * Constructor que inicializa la conexión con un socket ya aceptado.
     *
     * @param socket socket activo que será administrado por esta conexión.
     */
    public ConexionSocketServer(Socket socket) {
        this.socket = socket;
    }

    /**
     * Obtiene el flujo de entrada (InputStream) del socket.
     * Permite leer los datos enviados por el cliente.
     *
     * @return flujo de entrada asociado al socket.
     * @throws IOException si ocurre un error al obtener el flujo.
     */
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    /**
     * Obtiene el flujo de salida (OutputStream) del socket.
     * Permite enviar datos al cliente conectado.
     *
     * @return flujo de salida asociado al socket.
     * @throws IOException si ocurre un error al obtener el flujo.
     */
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    /**
     * Cierra el socket de manera segura, capturando cualquier excepción
     * sin interrumpir el flujo de ejecución.
     */
    public void cerrar() {
        try {
            socket.close();
        } catch (Exception e) {
            // Silencia cualquier error al cerrar la conexión
        }
    }
}


