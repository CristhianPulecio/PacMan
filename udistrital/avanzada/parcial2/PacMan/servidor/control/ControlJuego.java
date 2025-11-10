/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;


/**
 * Controlador del juego para UN jugador.
 *
 * RESPONSABILIDADES:
 * - Manejar la lógica del juego Pac-Man (modelo-control).
 * - Coordinar con ControlVista para mover el Pac-Man gráficamente.
 * - Detectar límites, sumar puntaje y controlar fin del juego.
 * - Controlar tiempo de juego.
 *
 * NO maneja GUI directamente.
 * NO imprime nada.
 * NO usa sockets (eso lo hace ThreadServidor).
 *
 * author USER
 */
public class ControlJuego {

    /** Controlador de la vista, encargado de actualizar la posición real del Pac-Man. */
    private ControlVista controlVista;

    /** Estado del movimiento después de cada acción (OK, LIMITE, etc.). */
    private String estado;

    /** Puntaje total acumulado. */
    private int puntaje;

    /** Número de frutas encontradas. */
    private int frutasEncontradas;

    /** Cantidad total de frutas para finalizar el juego (4, según requerimiento). */
    private final int TOTAL_FRUTAS = 4;

    /** Tiempo inicial en milisegundos. */
    private long tiempoInicio;

    /** Tiempo final al terminar. */
    private long tiempoFinal;

    /**
     * Constructor:
     * Recibe un ControlVista para manipular el Pac-Man en el panel.
     */
    public ControlJuego() {
        controlVista = new ControlVista(1280,720);
        this.estado = "OK";
        this.puntaje = 0;
        this.frutasEncontradas = 0;
        this.tiempoInicio = System.currentTimeMillis();

        // Inicializa al Pac-Man en el centro del panel
        controlVista.ubicarPacManEnCentro();
    }

    /**
     * Movimiento hacia arriba.
     */
    public void moverArriba() {
        if (controlVista.moverArriba()) {
            estado = "MOVIMIENTO HACIA ARRIBA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /**
     * Movimiento hacia abajo.
     */
    public void moverAbajo() {
        if (controlVista.moverAbajo()) {
            estado = "MOVIMIENTO HACIA ABAJO HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /**
     * Movimiento hacia la izquierda.
     */
    public void moverIzquierda() {
        if (controlVista.moverIzquierda()) {
            estado = "MOVIMIENTO A LA IZQUIERDA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /**
     * Movimiento hacia la derecha.
     */
    public void moverDerecha() {
        if (controlVista.moverDerecha()) {
            estado = "MOVIMIENTO A LA DERECHA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /**
     * Revisa si Pac-Man está encima de una fruta.
     * Si es así:
     *  - Suma puntaje
     *  - Elimina fruta de la pantalla
     *  - Incrementa contador
     */
    private void verificarFruta() {
        int valorFruta = controlVista.detectarFruta();

        if (valorFruta > 0) {
            puntaje += valorFruta;
            frutasEncontradas++;
        }
    }

    /**
     * Retorna el estado del movimiento posterior a la última acción.
     *
     * @return "OK" o "LIMITE_VENTANA"
     */
    public String getEstadoMovimiento() {
        return estado;
    }

    /**
     * Determina si el juego ya terminó (todas las frutas encontradas).
     */
    public boolean juegoTerminado() {
        return frutasEncontradas >= TOTAL_FRUTAS;
    }

    /**
     * Retorna el puntaje final acumulado.
     */
    public int getPuntajeFinal() {
        return puntaje;
    }

    /**
     * Retorna el tiempo total del juego, en milisegundos.
     */
    public long getTiempoTotal() {
        tiempoFinal = System.currentTimeMillis();
        return tiempoFinal - tiempoInicio;
    }
    
}

