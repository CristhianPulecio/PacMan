/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Clase {@code VistaServidor}
 *
 * <p>Representa la vista gráfica del servidor Pac-Man. 
 * Muestra el tablero con el fondo fijo, el personaje principal (Pac-Man)
 * y hasta cuatro frutas generadas aleatoriamente.</p>
 *
 * <p>No contiene lógica de control ni estructuras de flujo; solo 
 * se encarga del dibujo visual y actualización de los elementos.</p>
 *
 * <p>El fondo no se repinta dinámicamente porque es estático.</p>
 *
 * @author 
 * Nicolás Arias
 */
public class VistaServidor extends JPanel {

    /** Imagen de fondo fija para el tablero. */
    private final Image imgFondo;

    /** Icono y posición del Pac-Man. */
    private ImageIcon iconPacman;
    private int pacX;
    private int pacY;

    /** Imágenes y posiciones de las cuatro frutas en pantalla. */
    private Image fruta1; private int fruta1x; private int fruta1y;
    private Image fruta2; private int fruta2x; private int fruta2y;
    private Image fruta3; private int fruta3x; private int fruta3y;
    private Image fruta4; private int fruta4x; private int fruta4y;

    /** Textos de información (tiempo y puntaje). */
    private String tiempoTexto = "Tiempo: 0 s";
    private String puntajeTexto = "Puntaje: 0";

    /** Constructor: carga el fondo y configura el panel. */
    public VistaServidor() {
        imgFondo = new ImageIcon("src/Specs/images/Background.png").getImage();
        setFocusable(false);
    }


    /** Define la posición y el icono del Pac-Man. */
    public void setPacman(ImageIcon icon, int x, int y) {
        this.iconPacman = icon;
        this.pacX = x;
        this.pacY = y;
    }

    // Asignación directa de frutas y sus posiciones
    public void setFruta1(Image img, int x, int y) { fruta1 = img; 
    fruta1x = x; fruta1y = y; }
    public void setFruta2(Image img, int x, int y) { fruta2 = img; 
    fruta2x = x; fruta2y = y; }
    public void setFruta3(Image img, int x, int y) { fruta3 = img; 
    fruta3x = x; fruta3y = y; }
    public void setFruta4(Image img, int x, int y) { fruta4 = img; 
    fruta4x = x; fruta4y = y; }

    /** Actualiza el texto del tiempo mostrado. */
    public void setTiempoTexto(String t) {
        this.tiempoTexto = "Tiempo: " + t;
    }

    /** Actualiza el texto del puntaje mostrado. */
    public void setPuntajeTexto(int p) {
        this.puntajeTexto = "Puntaje: " + p;
    }

    /**
     * Método de dibujo principal. 
     * Se ejecuta automáticamente cada vez que se repinta el panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo del tablero
        g.drawImage(imgFondo, 0, 0, 1280, 720, this);

        // Dibujo del Pac-Man
        iconPacman.paintIcon(this, g, pacX, pacY);

        // Dibujo de las frutas activas
        g.drawImage(fruta1, fruta1x, fruta1y, 64, 64, this);
        g.drawImage(fruta2, fruta2x, fruta2y, 64, 64, this);
        g.drawImage(fruta3, fruta3x, fruta3y, 64, 64, this);
        g.drawImage(fruta4, fruta4x, fruta4y, 64, 64, this);

        // Información textual
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        g.drawString(puntajeTexto, 20, 40);       // Puntaje arriba izquierda
        g.drawString(tiempoTexto, 1050, 40);      // Tiempo arriba derecha
    }

    /**
     * Crea y muestra la ventana principal del servidor.
     * @param panel instancia del propio {@code VistaServidor}.
     * @return la ventana creada y visible.
     */
    public JFrame crearVentana(VistaServidor panel) {
        JFrame ventana = new JFrame("PAC-MAN SERVIDOR");
        ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventana.add(panel);
        ventana.setSize(1280,720);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        return ventana;
    }
}




