/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.IOException;
import javax.swing.Timer;

/**
 * Controlador principal del juego Pac-Man para un solo jugador.
 * 
 * <p>Encargado de coordinar las interacciones entre la vista 
 * (ControlVistaServer)
 * y la capa de datos (ControlConexion). Gestiona movimientos, detección de 
 * frutas, conteo del tiempo, puntaje, y registro del resultado final.</p>
 * 
 * <p>Este controlador no gestiona la comunicación por sockets, 
 * ni el flujo del servidor general. Se ejecuta de forma independiente por 
 * jugador.</p>
 *
 * author USER
 */
public class ControlJuego {

    /** Controlador de la vista asociada al juego individual */
    private ControlVistaServer controlVista;
    /** Controlador de conexiones para acceder a BD y archivo aleatorio */
    private final ControlConexion controlConexion;

    /** Estado textual del último movimiento ejecutado */
    private String estado;
    /** Nombre del jugador actual */
    private String usuario;

    /** Puntaje acumulado durante la partida */
    private int puntaje;
    /** Contador de frutas recolectadas */
    private int frutasEncontradas;

    /** Número total de frutas que deben recolectarse para terminar el juego */
    private final int TOTAL_FRUTAS = 4;

    /** Tiempo en milisegundos desde el inicio de la partida */
    private long tiempoInicio;
    /** Tiempo final (registrado al terminar) */
    private long tiempoFinal;

    /** Temporizador para actualizar el tiempo en pantalla cada segundo */
    private Timer timer;

    /** Callback opcional a ejecutar cuando el juego termina */
    private Runnable onJuegoTerminado;

    /**
     * Constructor principal que inicializa el entorno del juego para el jugador.
     *
     * @param controlConexion referencia al controlador de conexión general.
     * @param usuario nombre del jugador.
     */
    public ControlJuego(ControlConexion controlConexion, String usuario) {
        this.controlConexion = controlConexion;
        this.usuario = usuario;

        // Crear la vista y ubicar el Pac-Man en el centro
        this.controlVista = new ControlVistaServer(1280, 720);
        this.tiempoInicio = System.currentTimeMillis();

        controlVista.ubicarPacManEnCentro();

        // Iniciar temporizador de tiempo de juego
        iniciarTimer();

        // Registrar acción del botón "Salir"
        controlVista.setCallbackSalida(() -> salir());
    }

    /* ============================================================
       TIMER PRINCIPAL DEL JUEGO
       ============================================================ */

    /**
     * Inicia el temporizador que actualiza el tiempo de juego en pantalla
     * cada segundo.
     */
    private void iniciarTimer() {
        timer = new Timer(1000, e -> {
            long ahora = System.currentTimeMillis();
            long transcurrido = ahora - tiempoInicio;
            controlVista.setTiempo(transcurrido);
        });
        timer.start();
    }

    /* ============================================================
       MOVIMIENTOS DEL PERSONAJE
       ============================================================ */

    /** Mueve el personaje hacia arriba, verificando si hay una fruta. */
    public void moverArriba() {
        if (controlVista.moverArriba()) {
            estado = "MOVIMIENTO HACIA ARRIBA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /** Mueve el personaje hacia abajo, verificando si hay una fruta. */
    public void moverAbajo() {
        if (controlVista.moverAbajo()) {
            estado = "MOVIMIENTO HACIA ABAJO HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /** Mueve el personaje hacia la izquierda, verificando si hay una fruta. */
    public void moverIzquierda() {
        if (controlVista.moverIzquierda()) {
            estado = "MOVIMIENTO A LA IZQUIERDA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /** Mueve el personaje hacia la derecha, verificando si hay una fruta. */
    public void moverDerecha() {
        if (controlVista.moverDerecha()) {
            estado = "MOVIMIENTO A LA DERECHA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }


    /**
     * Verifica si Pac-Man ha tocado una fruta.
     * Si es así, incrementa el puntaje, actualiza la vista y detiene el juego
     * si se recolectaron todas las frutas.
     */
    private void verificarFruta() {
        int valorFruta = controlVista.detectarFruta();
        if (valorFruta > 0) {
            puntaje += valorFruta;
            frutasEncontradas++;

            // Actualizar puntaje en la vista
            controlVista.setPuntaje(puntaje);

            // Detener el cronómetro al terminar el juego
            if (juegoTerminado()) {
                timer.stop();
            }
        }
    }

    /**
     * Determina si el juego ha finalizado (todas las frutas recolectadas).
     * @return true si se alcanzó el total de frutas, false en caso contrario.
     */
    public boolean juegoTerminado() {
        return frutasEncontradas >= TOTAL_FRUTAS;
    }

    /** @return mensaje de estado del último movimiento ejecutado. */
    public String getEstadoMovimiento() {
        return estado;
    }

    /** @return puntaje acumulado por el jugador. */
    public int getPuntajeFinal() {
        return puntaje;
    }

    /**
     * Calcula el tiempo total de la partida.
     * @return duración en milisegundos.
     */
    public long getTiempoTotal() {
        tiempoFinal = System.currentTimeMillis();
        return tiempoFinal - tiempoInicio;
    }


    /**
     * Método invocado al presionar el botón "Salir".
     * Detiene el temporizador, actualiza la vista, guarda el resultado final
     * en el archivo aleatorio y ejecuta el callback si existe.
     */
    private void salir() {
        try {
            // Detener el timer si está activo
            if (timer != null) {
                timer.stop();
            }

            int puntajeFinal = getPuntajeFinal();
            long tiempoTotal = getTiempoTotal();

            // Refrescar datos finales en la interfaz
            controlVista.setPuntaje(puntajeFinal);
            controlVista.setTiempo(tiempoTotal);

            // Registrar resultado en el archivo aleatorio
            controlConexion.guardarResultadoEnAleatorio(usuario, puntajeFinal, 
                    tiempoTotal);

            // Ejecutar callback externo si existe
            if (onJuegoTerminado != null) {
                onJuegoTerminado.run();
            }

        } catch (IOException e) {
            // No se imprime nada en caso de error de escritura
        }
    }

    /**
     * Asigna una acción a ejecutar cuando el juego termine.
     * 
     * @param r acción a ejecutar (por ejemplo, notificar al servidor).
     */
    public void setOnJuegoTerminado(Runnable r) {
        this.onJuegoTerminado = r;
    }
}




