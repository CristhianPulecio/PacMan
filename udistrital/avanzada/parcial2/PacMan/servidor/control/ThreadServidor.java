/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Hilo encargado de atender a un cliente individual del juego Pac-Man.
 *
 * Responsabilidades del hilo:
 * - Leer instrucciones enviadas por el cliente (ARRIBA, ABAJO, DERECHA, IZQUIERDA).
 * - Ejecutar dichas instrucciones en un ControlJuego independiente.
 * - Enviar mensajes de respuesta al cliente según sea necesario.
 *
 * NO imprime en consola.
 * NO maneja lógica de vista.
 * NO valida usuarios (esa validación ocurre en la clase Servidor).
 *
 * Cada instancia de ThreadServidor representa un cliente autenticado.
 *
 * author USER
 */
public class ThreadServidor extends Thread {

    private final Socket socketCliente;
    private DataInputStream entrada;
    private DataOutputStream salida;


    /** Controlador independiente del juego para este cliente. */
    private final ControlJuego controlJuego;
    
    private final ControlConexion controlConexion;
    
    private final String usuario;

    /**
     * Crea un hilo para manejar la comunicación con un cliente ya validado.
     *
     * @param socketCliente socket utilizado para comunicarse con el cliente
     * 
     */
    public ThreadServidor(Socket socketCliente, ControlConexion controlConexion, 
            String usuario) {
        this.socketCliente = socketCliente;
        
        this.controlConexion = controlConexion;
        
        this.usuario = usuario;

        // Cada jugador tiene su propio controlador de juego
        this.controlJuego = new ControlJuego();
    }

    @Override
    public void run() {
        try {
            entrada = new DataInputStream(socketCliente.getInputStream());
            salida = new DataOutputStream(socketCliente.getOutputStream());

            escucharMovimientos();

        } catch (IOException e) {
            // No imprimir nada
        } finally {
            cerrarConexion();
        }
    }

    /**
     * Ciclo principal:
     * Escucha continuamente direcciones enviadas por el cliente.
     * El cliente escribe cadenas: "ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA".
     */
    private void escucharMovimientos() {
        while (true) {
            try {
                String comando = entrada.readUTF().trim();

                switch (comando.toUpperCase()) {
                    case "ARRIBA":    controlJuego.moverArriba(); break;
                    case "ABAJO":     controlJuego.moverAbajo(); break;
                    case "IZQUIERDA": controlJuego.moverIzquierda(); break;
                    case "DERECHA":   controlJuego.moverDerecha(); break;
                    case "SALIR":     salida.writeUTF("FIN"); return;
                    default:
                        salida.writeUTF("COMANDO_INVALIDO");
                        continue;
                }

                salida.writeUTF(controlJuego.getEstadoMovimiento());

                if (controlJuego.juegoTerminado()) {

                    int puntaje = controlJuego.getPuntajeFinal();
                    long tiempo = controlJuego.getTiempoTotal();

                    salida.writeUTF("JUEGO_TERMINADO");
                    salida.writeInt(puntaje);
                    salida.writeLong(tiempo);

                    // GUARDAR RESULTADO EN ARCHIVO ALEATORIO
                    controlConexion.guardarResultadoEnAleatorio(usuario, puntaje, tiempo);

                    return;
                }

            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * Cierra el socket y streams.
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
