/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.vista;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase {@code VistaArchivosUser}
 *
 * <p>Su única responsabilidad es permitir que el usuario seleccione un archivo
 * de configuración con extensión <b>.properties</b> mediante un cuadro de 
 * diálogo gráfico.</p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Abrir un {@link JFileChooser} para elegir el archivo de 
 * propiedades.</li>
 *   <li>Filtrar los archivos visibles a solo los de tipo <code>.
 * properties</code>.</li>
 *   <li>Devolver el archivo seleccionado al controlador.</li>
 * </ul>
 *
 * <p><b>Restricciones:</b></p>
 * <ul>
 *   <li>No valida el contenido del archivo.</li>
 *   <li>No realiza lectura ni escritura.</li>
 *   <li>No imprime mensajes ni genera errores visibles al usuario.</li>
 * </ul>
 *
 * @author 
 * Nicolás Arias
 */
public class VistaArchivosUser {

    /**
     * Abre un diálogo de selección de archivos para permitir al usuario elegir
     * un archivo de configuración (.properties) en su sistema.
     *
     * @return el {@link File} seleccionado por el usuario, o {@code null}
     *         si el usuario cancela la selección.
     */
    public File seleccionarArchivoProperties() {
        JFileChooser chooser = new JFileChooser();

        // Filtro para mostrar únicamente archivos .properties
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Archivos de configuración "
                        + "(*.properties)", "properties");
        chooser.setFileFilter(filter);

        // Abre el diálogo y espera la acción del usuario
        int opcion = chooser.showOpenDialog(null);

        // Retorna el archivo seleccionado (puede ser null si el usuario cancela)
        return chooser.getSelectedFile();
    }
}


