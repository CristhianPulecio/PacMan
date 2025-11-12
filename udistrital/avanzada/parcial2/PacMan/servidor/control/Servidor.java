/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import udistrital.avanzada.parcial2.PacMan.servidor.vista.VistaArchivosServer;
import udistrital.avanzada.parcial2.PacMan.servidor.vista.VistaEspera;

/**
 * Clase {@code Servidor}
 * 
 * <p>Controlador principal del servidor del juego Pac-Man.
 * Es responsable de crear el servidor, aceptar conexiones entrantes,
 * validar usuarios contra la base de datos y lanzar un hilo
 * independiente ({@link ThreadServidor}) por cada cliente autenticado.</p>
 * 
 * <p>Además, se comunica con {@link ControlVistaEspera} para
 * actualizar la interfaz del servidor 
 * (estado, usuarios conectados, errores, etc.).</p>
 * 
 * <p>Aplica los principios de diseño:</p>
 * <ul>
 *   <li><b>SRP (Single Responsibility):</b> Solo controla la creación y gestión 
 * del servidor.</li>
 *   <li><b>Bajo acoplamiento:</b> Utiliza DAOs a través de 
 * {@link ControlConexion} para la gestión de datos.</li>
 *   <li><b>MVC:</b> Funciona como controlador, manteniendo separada la lógica 
 * de red, la vista y los datos.</li>
 * </ul>
 *
 * @author 
 * Cristhian Pulecio
 */
public class Servidor {

    /** Puerto TCP utilizado por el servidor para aceptar clientes */
    private final int puerto;

    /** Controlador de conexión general (maneja BD, .properties y archivo 
     * aleatorio) */
    private final ControlConexion controlConexion;

    /** Controlador de la vista de espera del servidor */
    private final ControlVistaEspera controlVistaEspera;

    /**
     * Constructor principal del servidor.
     * 
     * <p>Responsabilidades del constructor:</p>
     * <ol>
     *   <li>Abrir los selectores de archivos (.properties y .dat).</li>
     *   <li>Crear los controladores de conexión y vista.</li>
     *   <li>Registrar usuarios desde el archivo .properties en la base de 
     * datos.</li>
     *   <li>Leer el puerto configurado en el archivo .properties.</li>
     *   <li>Iniciar el servidor y su ciclo de escucha de clientes.</li>
     * </ol>
     * 
     * @throws SQLException si ocurre un error en la base de datos.
     * @throws IOException si ocurre un error en la lectura de archivos o red.
     */
    public Servidor() throws SQLException, IOException {

        // 1. Abrir JFileChoosers para elegir archivos
        VistaArchivosServer selector = new VistaArchivosServer();

        File archivoProperties = selector.getArchivoProperties();
        File archivoAleatorio = selector.getArchivoAleatorio();

        // 2. Crear controlador de conexión (inicializa DAOs y conexiones)
        this.controlConexion = new ControlConexion(archivoProperties, 
                archivoAleatorio);

        // 3. Registrar usuarios del archivo .properties en la base de datos
        controlConexion.registrarUsuariosDesdeProperties();

        // 4. Crear control de la vista de espera
        this.controlVistaEspera = new ControlVistaEspera();

        // 5. Configurar el botón "Salir" de la vista de espera
        controlVistaEspera.setOnSalir(() -> mostrarMejorJugador());

        // 6. Obtener el puerto configurado desde el archivo properties
        int puertoLeido = Integer.parseInt(controlConexion.getPropertiesDAO().
                obtenerPuerto());
        this.puerto = puertoLeido;

        // 7. Iniciar el servidor en escucha
        iniciarServidor();
    }

    /**
     * Inicia el servidor y queda en escucha constante para aceptar nuevos 
     * clientes.
     * 
     * <p>Cada cliente validado genera un nuevo hilo {@link ThreadServidor}
     * que se ejecuta de forma independiente, permitiendo la conexión 
     * concurrente.</p>
     */
    public void iniciarServidor() {

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {

            // El servidor se levantó correctamente
            controlVistaEspera.mostrarServidorLevantado();

            while (true) {
                // Esperar conexiones de clientes
                Socket socketCliente = serverSocket.accept();

                // Validar credenciales
                String usuario = validarCliente(socketCliente);

                if (usuario != null) {
                    // Mostrar en vista y crear hilo de atención
                    controlVistaEspera.mostrarUsuarioConectado(usuario);
                    ThreadServidor hilo = new ThreadServidor(socketCliente, 
                            controlConexion, usuario);
                    hilo.start();
                } else {
                    socketCliente.close();
                }
            }

        } catch (IOException e) {
            // Mostrar error en la vista
            controlVistaEspera.mostrarError();
        }
    }

    /**
     * Valida las credenciales (usuario y contraseña) recibidas desde el cliente.
     * 
     * <p>Usa streams UTF para comunicación bidireccional:
     * el cliente envía usuario/contraseña, y el servidor responde con "OK" o 
     * "DENEGADO".</p>
     * 
     * @param socketCliente socket del cliente con la conexión abierta.
     * @return nombre del usuario si la autenticación es correcta, o 
     * {@code null} si es inválida.
     */
    private String validarCliente(Socket socketCliente) {
        try {
            DataInputStream entrada = new DataInputStream(socketCliente.
                    getInputStream());
            DataOutputStream salida = new DataOutputStream(socketCliente.
                    getOutputStream());

            String usuario = entrada.readUTF();
            String contrasena = entrada.readUTF();

            boolean valido = controlConexion.validarUsuario(usuario, 
                    contrasena);

            if (valido) {
                salida.writeUTF("OK");
                salida.flush();
                return usuario;
            } else {
                salida.writeUTF("DENEGADO");
                salida.flush();
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Muestra en la vista el mejor jugador almacenado en el archivo aleatorio.
     * 
     * <p>Se ejecuta al presionar el botón “Salir” en la interfaz del 
     * servidor.</p>
     * 
     * <p>Selecciona al jugador con mayor puntaje y, en caso de empate, el de 
     * menor tiempo.</p>
     */
    private void mostrarMejorJugador() {
        try {
            var mejor = controlConexion.getAleatorioDAO().obtenerMejorJugador();

            if (mejor != null) {
                String usuario = (String) mejor.get("usuario");
                int puntaje = (int) mejor.get("puntaje");
                long tiempo = (long) mejor.get("tiempo");

                controlVistaEspera.mostrarMensaje(
                    "Mejor jugador: " + usuario +
                    " | Puntaje: " + puntaje +
                    " | Tiempo: " + tiempo + " ms"
                );
            } else {
                controlVistaEspera.mostrarMensaje("No hay registros en el "
                        + "archivo aleatorio.");
            }

        } catch (IOException e) {
            controlVistaEspera.mostrarMensaje("Error leyendo archivo "
                    + "aleatorio.");
        }
    }

}



