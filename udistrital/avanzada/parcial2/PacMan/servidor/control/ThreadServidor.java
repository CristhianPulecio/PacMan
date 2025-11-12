/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion.ConexionSocketServer;

/**
 * Clase {@code ThreadServidor}
 * 
 * <p>Representa un hilo independiente que atiende a un cliente
 * autenticado del juego Pac-Man. Permite la comunicación directa
 * entre el servidor y el cliente a través de un {@link Socket}.</p>
 * 
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Leer los comandos enviados por el cliente (ARRIBA, ABAJO, IZQUIERDA, DERECHA, SALIR).</li>
 *   <li>Ejecutar los movimientos en un {@link ControlJuego} propio.</li>
 *   <li>Enviar respuestas o notificaciones al cliente según el estado del juego.</li>
 *   <li>Guardar los resultados finales en el archivo aleatorio al terminar la partida.</li>
 * </ul>
 * 
 * <p>Este hilo no valida usuarios ni gestiona interfaz gráfica.
 * Su única función es mantener la comunicación del juego en curso.</p>
 * 
 * @author 
 * Cristhian Pulecio
 */
public class ThreadServidor extends Thread {

    /** Socket asociado al cliente actual */
    private final Socket socketCliente;

    /** Flujo de entrada de datos desde el cliente */
    private DataInputStream entrada;

    /** Flujo de salida de datos hacia el cliente */
    private DataOutputStream salida;

    /** Controlador del juego individual para este cliente */
    private final ControlJuego controlJuego;

    /** Controlador general de conexiones (maneja DAOs y archivos) */
    private final ControlConexion controlConexion;

    /** Nombre del usuario autenticado en este hilo */
    private final String usuario;

    /** Manejo de conexión encapsulado del socket */
    private ConexionSocketServer conexionSocket;

    /**
     * Constructor del hilo.
     * 
     * @param socketCliente socket utilizado para comunicarse con el cliente.
     * @param controlConexion referencia al controlador de conexión global.
     * @param usuario nombre del jugador autenticado.
     */
    public ThreadServidor(Socket socketCliente, ControlConexion controlConexion, String usuario) {
        this.socketCliente = socketCliente;
        this.controlConexion = controlConexion;
        this.usuario = usuario;

        // Controlador de juego independiente
        this.controlJuego = new ControlJuego(controlConexion, usuario);

        // Callback: cuando el juego finaliza, notifica al cliente
        controlJuego.setOnJuegoTerminado(() -> {
            try {
                if (salida != null) {
                    salida.writeUTF("FIN");
                }
            } catch (IOException e) {
                // Sin manejo adicional
            }
        });
    }

    /**
     * Método principal del hilo.
     * 
     * <p>Inicializa los streams de comunicación y
     * llama al ciclo de escucha de comandos.</p>
     */
    @Override
    public void run() {
        try {
            conexionSocket = new ConexionSocketServer(socketCliente);
            entrada = new DataInputStream(conexionSocket.getInputStream());
            salida = new DataOutputStream(conexionSocket.getOutputStream());

            escucharMovimientos();

        } catch (IOException e) {
            // Error de conexión o cliente desconectado
        } finally {
            if (conexionSocket != null) conexionSocket.cerrar();
            cerrarConexion();
        }
    }

    /**
     * Escucha continuamente los comandos enviados por el cliente.
     * 
     * <p>Los comandos válidos son: ARRIBA, ABAJO, IZQUIERDA, DERECHA, SALIR.</p>
     * <p>El método se mantiene activo hasta que el cliente finaliza la sesión
     * o el juego termina.</p>
     */
    private void escucharMovimientos() {
        while (true) {
            try {
                String comando = entrada.readUTF().trim();

                switch (comando.toUpperCase()) {
                    case "ARRIBA":
                        controlJuego.moverArriba();
                        break;
                    case "ABAJO":
                        controlJuego.moverAbajo();
                        break;
                    case "IZQUIERDA":
                        controlJuego.moverIzquierda();
                        break;
                    case "DERECHA":
                        controlJuego.moverDerecha();
                        break;
                    case "SALIR":
                        salida.writeUTF("FIN");
                        return;
                    default:
                        salida.writeUTF("COMANDO INVALIDO");
                        continue;
                }

                // Enviar confirmación de movimiento
                String estado = controlJuego.getEstadoMovimiento();
                salida.writeUTF(estado);

                // Si el juego terminó, enviar puntaje y tiempo
                if (controlJuego.juegoTerminado()) {
                    int puntaje = controlJuego.getPuntajeFinal();
                    long tiempo = controlJuego.getTiempoTotal();

                    salida.writeUTF("JUEGO TERMINADO");
                    salida.writeInt(puntaje);
                    salida.writeLong(tiempo);

                    // Registrar resultado en archivo aleatorio
                    controlConexion.guardarResultadoEnAleatorio(usuario, puntaje, tiempo);
                    return;
                }

            } catch (IOException e) {
                return; // Cliente desconectado o error en comunicación
            }
        }
    }

    /**
     * Cierra de forma segura los flujos y el socket del cliente.
     */
    private void cerrarConexion() {
        try {
            if (entrada != null) entrada.close();
        } catch (IOException e) {}

        try {
            if (salida != null) salida.close();
        } catch (IOException e) {}

        try {
            if (socketCliente != null) socketCliente.close();
        } catch (IOException e) {}
    }
}

