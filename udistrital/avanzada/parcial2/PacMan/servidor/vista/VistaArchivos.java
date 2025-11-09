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
 * VistaArchivos
 *
 * Clase simple encargada únicamente de permitir que el usuario seleccione:
 *  - Un archivo .properties
 *  - Un archivo para el archivo aleatorio (.dat)
 *
 * Esta clase NO crea el servidor.
 * El servidor la crea a ella para obtener los archivos seleccionados.
 *
 * No imprime nada.
 *
 * author USER
 */
public class VistaArchivos extends JFrame {

    private File archivoProperties;
    private File archivoAleatorio;

    public VistaArchivos() {
        // No es necesario mostrar ventana
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        seleccionarArchivoProperties();
        seleccionarArchivoAleatorio();
    }

    /**
     * Abre el JFileChooser para el archivo .properties.
     */
    private void seleccionarArchivoProperties() {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione archivo .properties");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivo Properties (*.properties)", "properties"));

        int opcion = chooser.showOpenDialog(this);

     
        this.archivoProperties = chooser.getSelectedFile();

    }

    /**
     * Abre el JFileChooser para seleccionar archivo aleatorio
     * (.dat o cualquier archivo binario).
     */
    private void seleccionarArchivoAleatorio() {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione archivo aleatorio (.dat)");

        chooser.setFileFilter(new FileNameExtensionFilter("Archivo binario (*.dat)", "dat"));

        int opcion = chooser.showSaveDialog(this);


        this.archivoAleatorio = chooser.getSelectedFile();
   
    }

    /* ============================================
       GETTERS PARA EL SERVIDOR
       ============================================ */

    public File getArchivoProperties() {
        return archivoProperties;
    }

    public File getArchivoAleatorio() {
        return archivoAleatorio;
    }
}

