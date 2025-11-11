/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.IOException;

/**
 * Controlador del juego para UN jugador.
 *
 * Se actualiza para trabajar correctamente con la nueva versión de ControlVista
 * que usa Map<String,Object> en lugar de clases internas para frutas y resultados.
 *
 * No se requiere modificar lógica porque ControlJuego no accede a la fruta, 
 * solo recibe el puntaje desde controlVista.detectarFruta().
 *
 * author USER
 */
public class ControlJuego {

    private ControlVista controlVista;
    private final ControlConexion controlConexion;

    private String estado;
    private String usuario;

    private int puntaje;
    private int frutasEncontradas;

    private final int TOTAL_FRUTAS = 4;

    private long tiempoInicio;
    private long tiempoFinal;

    private Runnable onJuegoTerminado;

    public ControlJuego(ControlConexion controlConexion, String usuario) {
        this.controlConexion = controlConexion;
        this.usuario = usuario;

        this.controlVista = new ControlVista(1280, 720);
        this.tiempoInicio = System.currentTimeMillis();

        controlVista.ubicarPacManEnCentro();

        // Listener cuando se presiona el botón salir
        controlVista.setCallbackSalida(() -> salir());
    }

    /* ===============================================
       MOVIMIENTOS
       =============================================== */

    public void moverArriba() {
        if (controlVista.moverArriba()) {
            estado = "MOVIMIENTO HACIA ARRIBA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    public void moverAbajo() {
        if (controlVista.moverAbajo()) {
            estado = "MOVIMIENTO HACIA ABAJO HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    public void moverIzquierda() {
        if (controlVista.moverIzquierda()) {
            estado = "MOVIMIENTO A LA IZQUIERDA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    public void moverDerecha() {
        if (controlVista.moverDerecha()) {
            estado = "MOVIMIENTO A LA DERECHA HECHO";
            verificarFruta();
        } else {
            estado = "LIMITE DE LA VENTANA";
        }
    }

    /* ===============================================
       DETECCIÓN DE FRUTA
       =============================================== */
    private void verificarFruta() {
        int valorFruta = controlVista.detectarFruta();
        if (valorFruta > 0) {
            puntaje += valorFruta;
            frutasEncontradas++;
        }
    }

    public boolean juegoTerminado() {
        return frutasEncontradas >= TOTAL_FRUTAS;
    }

    public String getEstadoMovimiento() {
        return estado;
    }

    public int getPuntajeFinal() {
        return puntaje;
    }

    public long getTiempoTotal() {
        tiempoFinal = System.currentTimeMillis();
        return tiempoFinal - tiempoInicio;
    }

    /* ===============================================
       SALIDA Y REGISTRO EN ARCHIVO ALEATORIO
       =============================================== */

    private void salir() {
        try {
            int puntajeFinal = getPuntajeFinal();
            long tiempoTotal = getTiempoTotal();

            // Registrar resultado en archivo aleatorio
            controlConexion.guardarResultadoEnAleatorio(usuario, puntajeFinal, tiempoTotal);

            // CallBack para ThreadServidor
            if (onJuegoTerminado != null) {
                onJuegoTerminado.run();
            }

        } catch (IOException e) {
            // no imprimir
        }
    }

    public void setOnJuegoTerminado(Runnable r) {
        this.onJuegoTerminado = r;
    }

}


