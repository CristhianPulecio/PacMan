/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.vista;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * VistaArchivosUser
 *
 * ÚNICA RESPONSABILIDAD:
 * - Permitir al usuario seleccionar un archivo .properties mediante JFileChooser.
 *
 * NO lee el archivo.
 * NO imprime nada.
 * NO valida lógica del contenido.
 *
 * author USER
 */
public class VistaArchivosUser {

    /**
     * Abre un JFileChooser para seleccionar un archivo .properties.
     *
     * @return Archivo seleccionado, o null si el usuario cancela.
     */
    public File seleccionarArchivoProperties() {
        JFileChooser chooser = new JFileChooser();

        // Filtra solo archivos .properties
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Archivos de configuración (*.properties)", "properties");
        chooser.setFileFilter(filter);

        int opcion = chooser.showOpenDialog(null);


        return chooser.getSelectedFile();

    }
}

