/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase {@code ConexionPropertiesServer}
 *
 * <p>Responsable de cargar, mantener y cerrar un archivo de configuración
 * <b>.properties</b> que contiene parámetros como usuarios, contraseñas, puerto
 * y URL de base de datos.</p>
 *
 * <p>La ruta del archivo es proporcionada externamente mediante un 
 * {@link javax.swing.JFileChooser}.</p>
 *
 * <p><b>Principio aplicado:</b> Single Responsibility (SOLID),
 * ya que esta clase únicamente gestiona la carga y lectura del archivo
 * de propiedades.</p>
 *
 * <p>No realiza conexión a la base de datos, ni operaciones de red,
 * ni validaciones adicionales.</p>
 *
 * @author 
 * Cristhian Pulecio
 */
public class ConexionPropertiesServer {

    /** Objeto {@link Properties} que contiene los pares clave-valor cargados.*/
    private Properties properties;

    /** Archivo .properties actualmente en uso. */
    private File archivo;

    /**
     * Constructor por defecto.
     * Inicializa la estructura de propiedades vacía.
     */
    public ConexionPropertiesServer() {
        this.properties = new Properties();
    }

    /**
     * Carga el archivo .properties en memoria.
     *
     * @param archivo archivo seleccionado desde la vista (JFileChooser).
     * @throws IOException si ocurre un error durante la lectura del archivo.
     */
    public void cargarArchivoProperties(File archivo) throws IOException {
        this.archivo = archivo;

        try (FileInputStream fis = new FileInputStream(archivo)) {
            properties.load(fis);
        }
    }

    /**
     * Recupera el valor asociado a una clave del archivo properties.
     *
     * @param clave clave a buscar dentro del archivo.
     * @return valor asociado, o {@code null} si la clave no existe.
     */
    public String getProperty(String clave) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(clave);
    }

    /**
     * Libera los recursos asociados a la conexión de propiedades.
     * 
     * <p>Equivale al cierre de conexión para este tipo de recurso:
     * limpia el contenido cargado y anula la referencia al archivo.</p>
     */
    public void cerrarConexion() {
        if (properties != null) {
            properties.clear();
        }
        archivo = null;
    }

    /**
     * Retorna el archivo .properties actualmente cargado.
     *
     * @return instancia de {@link File} que representa el archivo cargado.
     */
    public File getArchivo() {
        return archivo;
    }
}


