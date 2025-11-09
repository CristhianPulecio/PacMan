/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * ControlVista:
 * Encargado de:
 *  - Gestionar visualmente Pac-Man
 *  - Crear frutas aleatorias con imagen y puntaje
 *  - Detectar colisiones
 *  - Proveer el listener de salida
 *
 * No crea GUI ni botones.
 * No imprime.
 *
 * La Vista usa los getters para pintar.
 *
 * author USER
 */
public class ControlVista {

    private final int anchoPanel;
    private final int altoPanel;
    private final int TAM = 32;

    private int pacmanX;
    private int pacmanY;

    private Image imgPacman;

    private Fruta[] frutas;

    private Image imgCereza;
    private Image imgFresa;
    private Image imgNaranja;
    private Image imgManzana;
    private Image imgMelon;
    private Image imgGalaxian;
    private Image imgCampana;
    private Image imgLlave;

    private final Random random = new Random();

    private final ActionListener salirListener;

    public ControlVista(int anchoPanel, int altoPanel) {
        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;

        cargarImagenes();
        generarFrutasAleatorias();

        this.salirListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarSalidaServidor();
            }
        };
    }

    public ActionListener getSalirListener() {
        return this.salirListener;
    }

    /* =========================================================
       CARGA DE IMÁGENES
       ========================================================= */

    private void cargarImagenes() {
        imgPacman = new ImageIcon("imagenes/pacman.png").getImage();

        imgCereza = new ImageIcon("imagenes/cereza.png").getImage();
        imgFresa = new ImageIcon("imagenes/fresa.png").getImage();
        imgNaranja = new ImageIcon("imagenes/naranja.png").getImage();
        imgManzana = new ImageIcon("imagenes/manzana.png").getImage();
        imgMelon = new ImageIcon("imagenes/melon.png").getImage();
        imgGalaxian = new ImageIcon("imagenes/galaxian.png").getImage();
        imgCampana = new ImageIcon("imagenes/campana.png").getImage();
        imgLlave = new ImageIcon("imagenes/llave.png").getImage();
    }

    /* =========================================================
       UBICACIÓN Y MOVIMIENTOS
       ========================================================= */

    public void ubicarPacManEnCentro() {
        pacmanX = (anchoPanel - TAM) / 2;
        pacmanY = (altoPanel - TAM) / 2;
    }

    private void generarFrutasAleatorias() {
        frutas = new Fruta[4];

        String[] tipos = {
            "CEREZA", "FRESA", "NARANJA", "MANZANA",
            "MELON", "GALAXIAN", "CAMPANA", "LLAVE"
        };

        for (int i = 0; i < frutas.length; i++) {
            String tipo = tipos[random.nextInt(tipos.length)];
            int fx = random.nextInt(anchoPanel - TAM);
            int fy = random.nextInt(altoPanel - TAM);

            frutas[i] = new Fruta(tipo, fx, fy, obtenerImagenFruta(tipo), obtenerPuntajeFruta(tipo));
        }
    }

    public boolean moverArriba() {
        if (pacmanY - 4 < 0) return false;
        pacmanY -= 4;
        return true;
    }

    public boolean moverAbajo() {
        if (pacmanY + 4 > altoPanel - TAM) return false;
        pacmanY += 4;
        return true;
    }

    public boolean moverIzquierda() {
        if (pacmanX - 4 < 0) return false;
        pacmanX -= 4;
        return true;
    }

    public boolean moverDerecha() {
        if (pacmanX + 4 > anchoPanel - TAM) return false;
        pacmanX += 4;
        return true;
    }

    /* =========================================================
       DETECCIÓN DE FRUTA → RETORNA PUNTAJE
       ========================================================= */

    /**
     * Detecta si Pac-Man toca una fruta:
     * - Elimina la fruta
     * - Retorna su puntaje
     * 
     * Si no toca ninguna fruta, retorna 0.
     */
    public int detectarFruta() {
        Rectangle rPacman = new Rectangle(pacmanX, pacmanY, TAM, TAM);

        for (int i = 0; i < frutas.length; i++) {
            Fruta f = frutas[i];
            if (f != null) {
                Rectangle rFruta = new Rectangle(f.x, f.y, TAM, TAM);
                if (rPacman.intersects(rFruta)) {
                    int valor = f.valor;
                    frutas[i] = null; // eliminar fruta
                    return valor;
                }
            }
        }
        return 0;
    }

    /* =========================================================
       PUNTAJES
       ========================================================= */
    private int obtenerPuntajeFruta(String tipo) {
        switch (tipo) {
            case "CEREZA": return 100;
            case "FRESA": return 300;
            case "NARANJA": return 500;
            case "MANZANA": return 700;
            case "MELON": return 1000;
            case "GALAXIAN": return 2000;
            case "CAMPANA": return 3000;
            case "LLAVE": return 5000;
        }
        return 0;
    }

    private Image obtenerImagenFruta(String tipo) {
        switch (tipo) {
            case "CEREZA": return imgCereza;
            case "FRESA": return imgFresa;
            case "NARANJA": return imgNaranja;
            case "MANZANA": return imgManzana;
            case "MELON": return imgMelon;
            case "GALAXIAN": return imgGalaxian;
            case "CAMPANA": return imgCampana;
            case "LLAVE": return imgLlave;
        }
        return null;
    }

    /* =========================================================
       SALIDA DEL SERVIDOR
       ========================================================= */

    public ResultadoJuego procesarSalidaServidor() {
        return new ResultadoJuego("", 0, 0); 
    }

    /* =========================================================
       GETTERS PARA VISTA
       ========================================================= */

    public int getPacmanX() { return pacmanX; }
    public int getPacmanY() { return pacmanY; }
    public Image getImgPacman() { return imgPacman; }
    public Fruta[] getFrutas() { return frutas; }
    public int getTAM() { return TAM; }

    /* =========================================================
       CLASES INTERNAS
       ========================================================= */

    public static class Fruta {
        public String tipo;
        public int x, y;
        public Image imagen;
        public int valor;

        public Fruta(String tipo, int x, int y, Image imagen, int valor) {
            this.tipo = tipo;
            this.x = x;
            this.y = y;
            this.imagen = imagen;
            this.valor = valor;
        }
    }

    public static class ResultadoJuego {
        public final String jugador;
        public final int puntaje;
        public final long tiempo;

        public ResultadoJuego(String jugador, int puntaje, long tiempo) {
            this.jugador = jugador;
            this.puntaje = puntaje;
            this.tiempo = tiempo;
        }
    }
}


