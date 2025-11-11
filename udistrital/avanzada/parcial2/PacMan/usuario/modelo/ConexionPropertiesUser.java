/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase responsable únicamente de cargar y gestionar la lectura
 * de un archivo .properties para el lado del USUARIO.
 *
 * La ruta del archivo será suministrada mediante un JFileChooser externo.
 *
 * RESPONSABILIDADES:
 *  - Cargar el archivo .properties
 *  - Permitir obtener los valores por clave
 *  - Limpiar recursos ("cerrar conexión")
 *
 * Esta clase NO conecta a BD, NO abre sockets y NO imprime.
 *
 * author USER
 */
public class ConexionPropertiesUser {

    /** Objeto que almacena las propiedades cargadas desde el archivo. */
    private Properties properties;

    /** Archivo .properties cargado. */
    private File archivo;

    /**
     * Constructor vacío.
     * El archivo se cargará usando cargarArchivoProperties(File).
     */
    public ConexionPropertiesUser() {
        this.properties = new Properties();
    }

    /**
     * Carga en memoria el archivo .properties recibido.
     *
     * @param archivo archivo seleccionado mediante JFileChooser.
     * @throws IOException si ocurre error de lectura.
     */
    public void cargarArchivoProperties(File archivo) throws IOException {
        this.archivo = archivo;

        try (FileInputStream fis = new FileInputStream(archivo)) {
            properties.load(fis);
        }
    }

    /**
     * Obtiene el valor asociado a una clave del archivo .properties.
     *
     * @param clave clave a consultar.
     * @return valor encontrado o null si no existe.
     */
    public String getProperty(String clave) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(clave);
    }

    /**
     * Libera recursos.
     * Esto equivale a "cerrar la conexión" de este tipo de recurso.
     */
    public void cerrarConexion() {
        if (properties != null) {
            properties.clear();
        }
        archivo = null;
    }

    /**
     * Retorna el archivo actualmente cargado.
     */
    public File getArchivo() {
        return archivo;
    }
}

