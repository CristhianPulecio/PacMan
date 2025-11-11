/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import udistrital.avanzada.parcial2.PacMan.usuario.vista.VistaArchivosUser;
import udistrital.avanzada.parcial2.PacMan.usuario.modelo.ConexionSocketUser;

/**
 * UsuarioConexion
 *
 * RESPONSABILIDADES:
 * - Seleccionar archivo .properties mediante VistaArchivosUser
 * - Crear ControlProperties
 * - Crear ControlUsuario
 * - Conectar al servidor
 * - Crear y configurar ControlVista
 * - Enviar comandos desde botones y recibir respuesta inmediata
 *
 * SIN hilos.
 * SIN impresión.
 *
 * author USER
 */
public class UsuarioConexion {

    private final ControlProperties controlProperties;
    private final ControlUsuario controlUsuario;
    private final ConexionSocketUser conexionSocket;

    private final ControlVista controlVista;

    private DataInputStream entrada;
    private DataOutputStream salida;

    /**
     * Constructor único:
     * Realiza todo el flujo de conexión y crea la vista del cliente.
     */
    public UsuarioConexion() throws IOException {

        // 1. Seleccionar archivo properties
        VistaArchivosUser selector = new VistaArchivosUser();
        File archivoProperties = selector.seleccionarArchivoProperties();

        if (archivoProperties == null) {
            throw new IOException("No se seleccionó archivo properties.");
        }

        // 2. Controlador del properties
        this.controlProperties = new ControlProperties(archivoProperties);

        // 3. Controlador de usuario (lee usuario/contraseña del archivo)
        this.controlUsuario = new ControlUsuario(controlProperties);

        // 4. Obtener IP y puerto
        String ip = controlProperties.getIPServidor();
        int puerto = Integer.parseInt(controlProperties.getPuertoServidor());

        // 5. Crear conexión de socket
        this.conexionSocket = new ConexionSocketUser(ip, puerto);
        conexionSocket.conectar();

        // 6. Streams
        this.entrada = new DataInputStream(conexionSocket.getInputStream());
        this.salida = new DataOutputStream(conexionSocket.getOutputStream());

        // 7. Enviar credenciales
        enviarCredencialesIniciales();

        // 8. Crear y configurar control de vista
        this.controlVista = new ControlVista();
        configurarEventosVista();
    }

    /**
     * Envía usuario y contraseña al servidor al conectar.
     */
    private void enviarCredencialesIniciales() throws IOException {
        String usuario = controlUsuario.getUsuario();
        String contrasena = controlUsuario.getContrasena();

        salida.write(usuario.getBytes());
        salida.flush();

        salida.write(contrasena.getBytes());
        salida.flush();
    }

    // ============================================================
    //   INTEGRACIÓN CON CONTROLVISTA (performers)
    // ============================================================

    private void configurarEventosVista() {

        controlVista.setOnArriba(() -> enviarYRecibir("ARRIBA"));
        controlVista.setOnAbajo(() -> enviarYRecibir("ABAJO"));
        controlVista.setOnIzquierda(() -> enviarYRecibir("IZQUIERDA"));
        controlVista.setOnDerecha(() -> enviarYRecibir("DERECHA"));

        controlVista.setOnSalir(() -> enviarYRecibir("SALIR"));
    }

    /**
     * Envía un comando al servidor y recibe inmediatamente la respuesta,
     * pasándola a la vista del usuario.
     */
    private void enviarYRecibir(String comando) {
        try {
            salida.writeUTF(comando);
            salida.flush();

            String respuesta = entrada.readUTF();
            controlVista.mostrarMensaje(respuesta);

        } catch (IOException e) {
            // No imprimir, solo notificar a la vista
            controlVista.mostrarMensaje("Error de conexión.");
        }
    }

    // ============================================================
    //      CIERRE
    // ============================================================

    public void cerrar() {
        try {
            if (entrada != null) entrada.close();
        } catch (Exception ignored) {}

        try {
            if (salida != null) salida.close();
        } catch (Exception ignored) {}

        if (conexionSocket != null) conexionSocket.cerrar();
        if (controlProperties != null) controlProperties.cerrar();
    }

    public ControlVista getControlVista() {
        return controlVista;
    }
}



