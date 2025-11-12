/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import udistrital.avanzada.parcial2.PacMan.usuario.vista.VistaArchivosUser;
import udistrital.avanzada.parcial2.PacMan.usuario.modelo.ConexionSocketUser;

/**
 * Clase {@code UsuarioConexion}
 *
 * <p>Encargada de orquestar toda la comunicación entre el cliente Pac-Man 
 * y el servidor. Implementa la secuencia completa desde la selección del
 * archivo de configuración hasta la comunicación con el servidor.</p>
 *
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Permitir seleccionar el archivo .properties 
 * (configuración del usuario).</li>
 *   <li>Crear y gestionar instancias de {@link ControlProperties} y 
 * {@link ControlUsuario}.</li>
 *   <li>Establecer conexión con el servidor mediante 
 * {@link ConexionSocketUser}.</li>
 *   <li>Enviar credenciales al servidor.</li>
 *   <li>Crear la interfaz gráfica del usuario 
 * ({@link ControlVistaUser}).</li>
 *   <li>Asignar acciones a los botones para enviar comandos al servidor.</li>
 *   <li>Iniciar un hilo único de lectura para recibir mensajes del 
 * servidor.</li>
 * </ul>
 *
 * <p>No contiene lógica de juego ni validaciones visuales.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class UsuarioConexion {

    /** Controlador del archivo de configuración. */
    private final ControlProperties controlProperties;

    /** Controlador de la información del usuario. */
    private final ControlUsuario controlUsuario;

    /** Manejador de la conexión de red mediante sockets. */
    private final ConexionSocketUser conexionSocket;

    /** Controlador de la vista del usuario (interfaz gráfica). */
    private final ControlVistaUser controlVista;

    /** Flujos de entrada/salida de datos. */
    private DataInputStream entrada;
    private DataOutputStream salida;

    /**
     * Constructor principal.
     * <p>Ejecuta la secuencia completa de inicialización del cliente 
     * Pac-Man.</p>
     *
     * @throws IOException si ocurre algún error durante la conexión o lectura 
     * del archivo.
     */
    public UsuarioConexion() throws IOException {

        // 1. Selección del archivo .properties
        VistaArchivosUser selector = new VistaArchivosUser();
        File archivoProperties = selector.seleccionarArchivoProperties();
        if (archivoProperties == null) {
            throw new IOException("No se seleccionó archivo properties.");
        }

        // 2. Control del archivo de configuración
        this.controlProperties = new ControlProperties(archivoProperties);

        // 3. Creación del controlador de usuario
        this.controlUsuario = new ControlUsuario(controlProperties);

        // 4. Obtención de IP y puerto desde el archivo properties
        String ip = controlProperties.getIPServidor();
        int puerto = Integer.parseInt(controlProperties.getPuertoServidor());

        // 5. Creación del socket y conexión al servidor
        this.conexionSocket = new ConexionSocketUser(ip, puerto);
        conexionSocket.conectar();

        // 6. Inicialización de streams de comunicación
        this.entrada = new DataInputStream(conexionSocket.getInputStream());
        this.salida  = new DataOutputStream(conexionSocket.getOutputStream());

        // 7. Envío inicial de credenciales al servidor
        enviarCredencialesIniciales();

        // 8. Creación y configuración de la vista del usuario
        this.controlVista = new ControlVistaUser();
        configurarEventosVista();

        // 9. Inicio del hilo lector único
        iniciarHiloLectura();
    }

    /**
     * Envía el nombre de usuario y la contraseña al servidor.
     *
     * @throws IOException si ocurre un error al escribir en el flujo de salida.
     */
    private void enviarCredencialesIniciales() throws IOException {
        String usuario = controlUsuario.getUsuario();
        String contrasena = controlUsuario.getContrasena();

        salida.writeUTF(usuario);
        salida.flush();

        salida.writeUTF(contrasena);
        salida.flush();
    }

    /**
     * Asocia los botones de la vista a los comandos que deben enviarse al
     * servidor.
     */
    private void configurarEventosVista() {
        controlVista.setOnArriba(()    -> enviar("ARRIBA"));
        controlVista.setOnAbajo(()     -> enviar("ABAJO"));
        controlVista.setOnIzquierda(() -> enviar("IZQUIERDA"));
        controlVista.setOnDerecha(()   -> enviar("DERECHA"));
        controlVista.setOnSalir(()     -> enviar("SALIR"));
    }

    /**
     * Envía un comando al servidor.
     * <p>Los botones solo envían, no esperan respuesta inmediata.</p>
     *
     * @param comando texto del comando a enviar.
     */
    private void enviar(String comando) {
        try {
            salida.writeUTF(comando);
            salida.flush();
        } catch (IOException e) {
            controlVista.mostrarMensaje("Error enviando comando.");
        }
    }


    /**
     * Inicia un hilo en segundo plano para escuchar los mensajes
     * enviados por el servidor. Este hilo se ejecuta continuamente
     * mientras la conexión esté activa.
     */
    private void iniciarHiloLectura() {

        Thread lector = new Thread(() -> {
            try {
                while (true) {
                    String respuesta = entrada.readUTF();
                    SwingUtilities.invokeLater(() -> 
                        controlVista.mostrarMensaje(respuesta)
                    );
                }
            } catch (IOException e) {
                SwingUtilities.invokeLater(() ->
                    controlVista.mostrarMensaje("Conexión cerrada.")
                );
            }
        }, "Hilo-Lector");

        lector.setDaemon(true); // No bloquea la finalización del programa
        lector.start();
    }


    /**
     * Cierra correctamente los flujos, el socket y la conexión del archivo 
     * properties.
     */
    public void cerrar() {
        try { if (entrada != null) entrada.close(); } 
        catch (Exception ignored) {}
        try { if (salida != null) salida.close(); } 
        catch (Exception ignored) {}

        if (conexionSocket != null) conexionSocket.cerrar();
        if (controlProperties != null) controlProperties.cerrar();
    }

    /** @return controlador de la vista del usuario. */
    public ControlVistaUser getControlVista() {
        return controlVista;
    }
}






