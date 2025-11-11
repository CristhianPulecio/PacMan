/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import udistrital.avanzada.parcial2.PacMan.servidor.vista.VistaArchivosServer;

/**
 * Clase controladora responsable de: - Crear el servidor. - Escuchar conexiones
 * entrantes. - Validar al usuario contra la base de datos. - Lanzar un hilo
 * ThreadServidor para cada cliente validado.
 *
 * No contiene impresiones en consola ni lógica de vista.
 *
 * Principios aplicados: - SRP (una sola responsabilidad). - Bajo acoplamiento
 * (usa DAOs para BD y propiedades). - MVC (esto es CONTROL, no vista ni
 * modelo).
 *
 * author USER
 */
public class Servidor {

    /**
     * Puerto usado para escuchar solicitudes de clientes.
     */
    private final int puerto;

    private final ControlConexion controlConexion;

    /**
     * Construye el servidor a partir de las propiedades cargadas.
     *
     * 
     * @throws SQLException si ocurre un error con la BD
     */
    public Servidor() throws SQLException, IOException {

        // 1. Abrir JFileChoosers para elegir archivos
        VistaArchivosServer selector = new VistaArchivosServer();

        File archivoProperties = selector.getArchivoProperties();
        File archivoAleatorio = selector.getArchivoAleatorio();

        // 2. Crear controlador de conexión (genera todos los DAOs)
        this.controlConexion = new ControlConexion(archivoProperties, archivoAleatorio);

        // 3. Insertar usuarios del .properties en la base de datos
        controlConexion.registrarUsuariosDesdeProperties();

        // 4. Obtener puerto desde el properties
        int puertoLeido = Integer.parseInt(controlConexion.getPropertiesDAO().obtenerPuerto());
        this.puerto = puertoLeido;

        // 5. Iniciar el servidor (serverSockets)
        iniciarServidor();
    }

    /**
     * Inicia el servidor, quedando en escucha permanente para nuevos clientes.
     * Cada cliente validado recibe un hilo ThreadServidor.
     */
    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {

            while (true) {
                Socket socketCliente = serverSocket.accept();

                String usuario = validarCliente(socketCliente);

                if (usuario != null) {
                    // PASA EL USUARIO Y EL CONTROL DE CONEXIÓN
                    ThreadServidor hilo = new ThreadServidor(socketCliente, controlConexion, usuario);
                    hilo.start();
                } else {
                    socketCliente.close();
                }
            }

        } catch (IOException e) {
            // Sin impresión
        }
    }

    /**
     * Valida usuario y contraseña enviados por el cliente.
     *
     * @param socketCliente socket por donde llegan las credenciales
     * @return true si las credenciales son correctas, false en caso contrario
     */
    private String validarCliente(Socket socketCliente) {
        try {
            var entrada = socketCliente.getInputStream();
            var salida = socketCliente.getOutputStream();

            byte[] buffer = new byte[256];

            int lenUsuario = entrada.read(buffer);
            String usuario = new String(buffer, 0, lenUsuario).trim();

            int lenPass = entrada.read(buffer);
            String contrasena = new String(buffer, 0, lenPass).trim();

            boolean valido = controlConexion.validarUsuario(usuario, contrasena);

            if (valido) {
                salida.write("OK".getBytes());
                return usuario;
            } else {
                salida.write("DENEGADO".getBytes());
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

}


