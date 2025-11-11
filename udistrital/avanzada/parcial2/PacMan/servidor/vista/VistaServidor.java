package udistrital.avanzada.parcial2.PacMan.servidor.vista;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class VistaServidor extends JPanel implements ActionListener, KeyListener {
    
    private int x = 450, y = 450; 
    private int velocidad = 4;
    private Timer timer;
    private ArrayList<Rectangle> paredes;
    private ArrayList<Rectangle> puntos; 
    private Image pacman;

    public VistaServidor() {
        setPreferredSize(new Dimension(512, 512));
        setBackground(Color.BLACK);

        pacman = new ImageIcon("src/imagenes/pacman.gif").getImage(); 
        timer = new Timer(16, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);

        crearParedes();
    }

    private void crearParedes() {
        paredes = new ArrayList<>();
        puntos = new ArrayList<>(); 
        
     
        int[][] mapa = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1},
            {1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 1, 2, 1},
            {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 1}, 
            {1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
            {1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 1, 2, 1},
            {1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1}, 
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int TAMAÑO_CELDA = 32;

        for (int fila = 0; fila < mapa.length; fila++) {
            for (int col = 0; col < mapa[0].length; col++) {
                int x = col * TAMAÑO_CELDA;
                int y = fila * TAMAÑO_CELDA;
                
                if (mapa[fila][col] == 1) {
                 
                    paredes.add(new Rectangle(x, y, TAMAÑO_CELDA, TAMAÑO_CELDA));
                } else if (mapa[fila][col] == 2) {
              
                    int punto_size = 6;
                    int punto_x = x + TAMAÑO_CELDA/2 - punto_size/2;
                    int punto_y = y + TAMAÑO_CELDA/2 - punto_size/2;
                    puntos.add(new Rectangle(punto_x, punto_y, punto_size, punto_size));
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        g.setColor(Color.BLUE);
        for (Rectangle r : paredes)
            g.fillRect(r.x, r.y, r.width, r.height);

        
        g.setColor(Color.WHITE);
        for (Rectangle p : puntos)
            g.fillOval(p.x, p.y, p.width, p.height); 

        g.drawImage(pacman, x, y, 28, 28, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private void mover(int dx, int dy) {
        int nuevoX = x + dx;
        int nuevoY = y + dy;
        Rectangle pacmanRect = new Rectangle(nuevoX, nuevoY, 28, 28);

     
        for (Rectangle pared : paredes)
            if (pacmanRect.intersects(pared)) return; 

      
        for (int i = 0; i < puntos.size(); i++) {
            Rectangle punto = puntos.get(i);
            if (pacmanRect.intersects(punto)) {
                puntos.remove(i); 
                break; 
            }
        }

       
        x = nuevoX;
        y = nuevoY;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) mover(0, -velocidad);
        if (code == KeyEvent.VK_DOWN) mover(0, velocidad);
        if (code == KeyEvent.VK_LEFT) mover(-velocidad, 0);
        if (code == KeyEvent.VK_RIGHT) mover(velocidad, 0);
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Pac-Man");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(new VistaServidor());
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}