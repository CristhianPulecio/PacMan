/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import udistrital.avanzada.parcial2.PacMan.servidor.vista.VistaServidor;

/**
 * ControlVistaServer
 * 
 * <p>Controlador encargado de manejar la lógica visual del juego Pac-Man
 * en el lado del servidor. Se comunica con la clase {@link VistaServidor}
 * para actualizar la interfaz gráfica del juego (posición del Pac-Man, frutas,
 * tiempo y puntaje).</p>
 * 
 * <p>Esta clase gestiona la posición, movimiento y detección de colisiones
 * del personaje, así como la generación aleatoria de frutas con imágenes
 * y valores específicos.</p>
 * 
 * <p>No contiene lógica de red ni de almacenamiento,
 * su único propósito es mantener sincronizada la vista.</p>
 *
 * <p>Principios aplicados:</p>
 * <ul>
 *   <li><b>SRP</b> (Single Responsibility): solo gestiona aspectos 
 *   visuales.</li>
 *   <li><b>MVC</b>: actúa como el "Controlador" entre la lógica y la 
 *   vista.</li>
 * </ul>
 *
 * @author 
 * Cristhian Pulecio
 */
public class ControlVistaServer {

    /** Dimensiones del panel donde se desplaza Pac-Man */
    private final int anchoPanel;
    private final int altoPanel;

    /** Tamaños fijos para el personaje y las frutas */
    private final int TAM_PACMAN = 128;
    private final int TAM_FRUTA = 64;

    /** Desplazamiento en píxeles por movimiento */
    private final int VELOCIDAD = 16;

    /** Coordenadas actuales del Pac-Man */
    private int pacmanX;
    private int pacmanY;

    /** Imagen e ícono asociados al Pac-Man */
    private Image imgPacman;
    private ImageIcon iconPacman;

    /** Arreglo que contiene las frutas activas en pantalla */
    private Map<String, Object>[] frutas;

    /** Imágenes de las frutas disponibles en el juego */
    private Image imgCereza;
    private Image imgFresa;
    private Image imgNaranja;
    private Image imgManzana;
    private Image imgMelon;
    private Image imgGalaxian;
    private Image imgCampana;
    private Image imgLlave;

    /** Generador de números aleatorios para ubicar frutas */
    private final Random random = new Random();

    /** Listener asociado al botón "Salir" en la vista */
    private final ActionListener salirListener;

    /** Acción ejecutada cuando el jugador solicita salir */
    private Runnable callbackSalida;

    /** Referencia a la vista principal del servidor */
    private VistaServidor vista;

    /**
     * Constructor principal.
     * 
     * @param anchoPanel ancho de la zona jugable.
     * @param altoPanel alto de la zona jugable.
     */
    public ControlVistaServer(int anchoPanel, int altoPanel) {

        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;
        this.vista = new VistaServidor();

        // Crea y muestra la ventana del juego
        vista.crearVentana(this.vista);

        // Carga imágenes y genera frutas aleatorias
        cargarImagenes();
        generarFrutasAleatorias();

        // Define la acción del botón "Salir"
        this.salirListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (callbackSalida != null) callbackSalida.run();
            }
        };
    }

    /**
     * Devuelve el ActionListener asociado al botón "Salir".
     * 
     * @return listener de salida.
     */
    public ActionListener getSalirListener() {
        return this.salirListener;
    }

    /**
     * Muestra el tiempo transcurrido en segundos en la vista.
     * 
     * @param millis tiempo en milisegundos.
     */
    public void setTiempo(long millis) {
        long segundos = millis / 1000;
        vista.setTiempoTexto(segundos + " s");
    }

    /**
     * Actualiza el puntaje mostrado en la interfaz.
     * 
     * @param p puntaje actual.
     */
    public void setPuntaje(int p) {
        vista.setPuntajeTexto(p);
    }


    /**
     * Carga todas las imágenes necesarias para el Pac-Man
     * y las frutas disponibles en el juego.
     */
    private void cargarImagenes() {
        iconPacman = new ImageIcon("src/Specs/images/Pacman.gif");
        imgPacman = iconPacman.getImage();

        imgCereza = new ImageIcon("src/Specs/images/Cereza.png").getImage();
        imgFresa = new ImageIcon("src/Specs/images/Fresa.png").getImage();
        imgNaranja = new ImageIcon("src/Specs/images/Naranja.png").getImage();
        imgManzana = new ImageIcon("src/Specs/images/Manzana.png").getImage();
        imgMelon = new ImageIcon("src/Specs/images/Melon.png").getImage();
        imgGalaxian = new ImageIcon("src/Specs/images/Galaxian.png").getImage();
        imgCampana = new ImageIcon("src/Specs/images/Campana.png").getImage();
        imgLlave = new ImageIcon("src/Specs/images/Cereza.png").getImage();
    }


    /**
     * Centra al Pac-Man en el panel de juego.
     */
    public void ubicarPacManEnCentro() {
        pacmanX = (anchoPanel - TAM_PACMAN) / 2;
        pacmanY = (altoPanel - TAM_PACMAN) / 2;
        actualizarVista();
    }

    /**
     * Genera cuatro frutas aleatorias con tipo, posición y valor asignados.
     */
    private void generarFrutasAleatorias() {
        frutas = new Map[4];

        String[] tipos = {
            "CEREZA", "FRESA", "NARANJA", "MANZANA",
            "MELON", "GALAXIAN", "CAMPANA", "LLAVE"
        };

        for (int i = 0; i < frutas.length; i++) {
            String tipo = tipos[random.nextInt(tipos.length)];
            int fx = random.nextInt(anchoPanel - TAM_FRUTA);
            int fy = random.nextInt(altoPanel - TAM_FRUTA);

            Map<String, Object> fruta = new HashMap<>();
            fruta.put("tipo", tipo);
            fruta.put("x", fx);
            fruta.put("y", fy);
            fruta.put("imagen", obtenerImagenFruta(tipo));
            fruta.put("valor", obtenerPuntajeFruta(tipo));

            frutas[i] = fruta;
        }

        actualizarVista();
    }


    /** Mueve el personaje hacia arriba. */
    public boolean moverArriba() {
        if (pacmanY - VELOCIDAD < 0) return false;
        pacmanY -= VELOCIDAD;
        actualizarVista();
        return true;
    }

    /** Mueve el personaje hacia abajo. */
    public boolean moverAbajo() {
        if (pacmanY + VELOCIDAD > altoPanel - TAM_PACMAN) return false;
        pacmanY += VELOCIDAD;
        actualizarVista();
        return true;
    }

    /** Mueve el personaje hacia la izquierda. */
    public boolean moverIzquierda() {
        if (pacmanX - VELOCIDAD < 0) return false;
        pacmanX -= VELOCIDAD;
        actualizarVista();
        return true;
    }

    /** Mueve el personaje hacia la derecha. */
    public boolean moverDerecha() {
        if (pacmanX + VELOCIDAD > anchoPanel - TAM_PACMAN) return false;
        pacmanX += VELOCIDAD;
        actualizarVista();
        return true;
    }

    /**
     * Detecta si Pac-Man ha tocado una fruta y devuelve su valor en puntos.
     * 
     * @return puntaje correspondiente a la fruta recolectada, o 0 si no hay 
     * colisión.
     */
    public int detectarFruta() {
        Rectangle rPacman = new Rectangle(pacmanX, pacmanY, TAM_PACMAN, 
                TAM_PACMAN);

        for (int i = 0; i < frutas.length; i++) {
            Map<String, Object> f = frutas[i];
            if (f != null) {
                int x = (int) f.get("x");
                int y = (int) f.get("y");
                int valor = (int) f.get("valor");

                Rectangle rFruta = new Rectangle(x, y, TAM_FRUTA, TAM_FRUTA);

                if (rPacman.intersects(rFruta)) {
                    frutas[i] = null; // fruta recolectada
                    actualizarVista();
                    return valor;
                }
            }
        }
        return 0;
    }


    /**
     * Actualiza gráficamente la vista de Pac-Man y las frutas activas.
     */
    private void actualizarVista() {
        // Pac-Man
        vista.setPacman(iconPacman, pacmanX, pacmanY);

        // Frutas 1 a 4
        for (int i = 0; i < 4; i++) {
            Map<String, Object> f = frutas[i];
            if (f != null) {
                vista.getClass().getMethods(); // placeholder no lógico
            }
        }

        // Actualiza frutas individualmente
        if (frutas[0] != null)
            vista.setFruta1((Image) frutas[0].get("imagen"), (int) 
                    frutas[0].get("x"), (int) frutas[0].get("y"));
        else vista.setFruta1(null, 0, 0);

        if (frutas[1] != null)
            vista.setFruta2((Image) frutas[1].get("imagen"), 
                    (int) frutas[1].get("x"), (int) frutas[1].get("y"));
        else vista.setFruta2(null, 0, 0);

        if (frutas[2] != null)
            vista.setFruta3((Image) frutas[2].get("imagen"), 
                    (int) frutas[2].get("x"), (int) frutas[2].get("y"));
        else vista.setFruta3(null, 0, 0);

        if (frutas[3] != null)
            vista.setFruta4((Image) frutas[3].get("imagen"), 
                    (int) frutas[3].get("x"), (int) frutas[3].get("y"));
        else vista.setFruta4(null, 0, 0);

        vista.repaint();
    }

    /**
     * Retorna el puntaje correspondiente al tipo de fruta.
     */
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

    /**
     * Retorna la imagen correspondiente al tipo de fruta.
     */
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

    /**
     * Crea un mapa con los datos del jugador, su puntaje y tiempo final.
     * 
     * @param jugador nombre del jugador.
     * @param puntaje puntaje final obtenido.
     * @param tiempo duración total del juego.
     * @return mapa con los datos del resultado.
     */
    public Map<String, Object> procesarSalidaServidor(String jugador, 
            int puntaje, long tiempo) {
        Map<String, Object> result = new HashMap<>();
        result.put("jugador", jugador);
        result.put("puntaje", puntaje);
        result.put("tiempo", tiempo);
        return result;
    }

    /**
     * Asigna una acción a ejecutar cuando se activa el evento de salida.
     * 
     * @param r acción (Runnable) a ejecutar.
     */
    public void setCallbackSalida(Runnable r) {
        this.callbackSalida = r;
    }

    public int getPacmanX() { return pacmanX; }
    public int getPacmanY() { return pacmanY; }
    public Image getImgPacman() { return imgPacman; }
    public Map<String, Object>[] getFrutas() { return frutas; }
    public int getTAM_PACMAN() { return TAM_PACMAN; }

}





