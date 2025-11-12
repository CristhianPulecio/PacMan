/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.vista;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase {@code VistaArchivosServer}
 * 
 * <p>Interfaz sencilla que permite al usuario seleccionar los archivos
 * necesarios para la configuración del servidor Pac-Man:</p>
 * <ul>
 *   <li>Un archivo <b>.properties</b> con la configuración del sistema.</li>
 *   <li>Un archivo <b>.dat</b> que funcionará como archivo aleatorio 
 * binario.</li>
 * </ul>
 *
 * <p>Esta clase no ejecuta la lógica del servidor. Su responsabilidad es
 * únicamente proporcionar las rutas seleccionadas a la clase 
 * {@code Servidor}.</p>
 *
 * <p>Principio aplicado: <b>Single Responsibility (SRP)</b>.</p>
 *
 * @author 
 * Nicolás Arias
 */
public class VistaArchivosServer extends JFrame {

    /** Archivo de configuración .properties seleccionado por el usuario. */
    private File archivoProperties;

    /** Archivo aleatorio (.dat) seleccionado o creado por el usuario. */
    private File archivoAleatorio;

    /**
     * Constructor que inicializa los selectores de archivo sin mostrar una
     * ventana principal visible.
     */
    public VistaArchivosServer() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        seleccionarArchivoProperties();
        seleccionarArchivoAleatorio();
    }

    /**
     * Permite seleccionar el archivo .properties que contiene la configuración
     * del servidor (puerto, usuarios, contraseñas, etc.).
     */
    private void seleccionarArchivoProperties() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione archivo .properties");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivo Properties "
                + "(*.properties)", "properties"));
        chooser.showOpenDialog(this);
        this.archivoProperties = chooser.getSelectedFile();
    }

    /**
     * Permite seleccionar o crear el archivo aleatorio (.dat) donde se
     * almacenarán los puntajes y tiempos de los jugadores.
     */
    private void seleccionarArchivoAleatorio() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione archivo aleatorio (.dat)");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivo binario "
                + "(*.dat)", "dat"));
        chooser.showSaveDialog(this);
        this.archivoAleatorio = chooser.getSelectedFile();
    }

    // ============================================================
    // GETTERS
    // ============================================================

    /** @return archivo .properties seleccionado. */
    public File getArchivoProperties() {
        return archivoProperties;
    }

    /** @return archivo aleatorio (.dat) seleccionado. */
    public File getArchivoAleatorio() {
        return archivoAleatorio;
    }
}


