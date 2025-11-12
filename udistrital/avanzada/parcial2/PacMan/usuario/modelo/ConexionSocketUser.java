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
 * Clase {@code ConexionSocketUser}
 *
 * <p>Representa la conexión TCP del lado del cliente Pac-Man hacia el 
 * servidor.</p>
 *
 * <p><b>Responsabilidad única:</b></p>
 * <ul>
 *   <li>Establecer la conexión con el servidor utilizando una dirección IP y 
 * un puerto.</li>
 *   <li>Proveer acceso directo a los flujos de entrada y salida (InputStream y 
 * OutputStream).</li>
 *   <li>Encapsular el manejo básico del socket sin intervenir en la 
 * comunicación de datos.</li>
 * </ul>
 *
 * <p><b>No realiza las siguientes acciones:</b></p>
 * <ul>
 *   <li>No envía comandos.</li>
 *   <li>No recibe mensajes.</li>
 *   <li>No imprime información en consola.</li>
 *   <li>No maneja hilos de ejecución.</li>
 * </ul>
 *
 * <p>Se comporta como un contenedor simple que abstrae la creación, uso y 
 * cierre del socket.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class ConexionSocketUser {

    /** Dirección IP del servidor al que se conectará el cliente. */
    private final String ip;

    /** Puerto de red en el cual escucha el servidor. */
    private final int puerto;

    /** Objeto Socket que mantiene la conexión activa con el servidor. */
    private Socket socket;

    /**
     * Constructor de la clase.
     *
     * @param ip dirección IP del servidor.
     * @param puerto número de puerto en el cual se establecerá la conexión.
     */
    public ConexionSocketUser(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    /**
     * Establece la conexión con el servidor.
     * <p>Si la conexión falla, lanza una excepción {@link IOException}.</p>
     *
     * @throws IOException si ocurre un error al intentar conectar al servidor.
     */
    public void conectar() throws IOException {
        socket = new Socket(ip, puerto);
    }

    /**
     * Retorna el flujo de entrada del socket.
     * <p>Permite recibir datos enviados por el servidor.</p>
     *
     * @return flujo de entrada (InputStream).
     * @throws IOException si ocurre un error al obtener el flujo.
     */
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    /**
     * Retorna el flujo de salida del socket.
     * <p>Permite enviar datos hacia el servidor.</p>
     *
     * @return flujo de salida (OutputStream).
     * @throws IOException si ocurre un error al obtener el flujo.
     */
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    /**
     * Cierra la conexión del socket junto con sus flujos asociados.
     * <p>No imprime mensajes ni lanza excepciones controladas, 
     * ya que se usa en contextos de cierre seguro.</p>
     */
    public void cerrar() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            // Silencio intencional: no se imprime nada
        }
    }
}


