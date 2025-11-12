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
 * Clase {@code ConexionPropertiesUser}
 *
 * <p>Encargada de la lectura y gestión del archivo <b>.properties</b>
 * que contiene la configuración del cliente Pac-Man (usuario, contraseña,
 * IP del servidor y puerto).</p>
 *
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Cargar en memoria el archivo .properties seleccionado.</li>
 *   <li>Permitir consultar valores por clave.</li>
 *   <li>Limpiar y liberar los recursos al finalizar.</li>
 * </ul>
 *
 * <p>No conecta a bases de datos, no abre sockets y no imprime en consola.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class ConexionPropertiesUser {

    /** Propiedades cargadas desde el archivo. */
    private Properties properties;

    /** Archivo .properties actual. */
    private File archivo;

    /** Constructor vacío. El archivo se carga posteriormente. */
    public ConexionPropertiesUser() {
        this.properties = new Properties();
    }

    /**
     * Carga en memoria el archivo .properties recibido.
     *
     * @param archivo archivo de configuración seleccionado.
     * @throws IOException si ocurre un error de lectura.
     */
    public void cargarArchivoProperties(File archivo) throws IOException {
        this.archivo = archivo;
        try (FileInputStream fis = new FileInputStream(archivo)) {
            properties.load(fis);
        }
    }

    /**
     * Obtiene el valor de una propiedad por su clave.
     *
     * @param clave nombre de la propiedad.
     * @return valor asociado o {@code null} si no existe.
     */
    public String getProperty(String clave) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(clave);
    }

    /**
     * Limpia los recursos cargados en memoria.
     * Equivale a "cerrar la conexión" del archivo de propiedades.
     */
    public void cerrarConexion() {
        if (properties != null) {
            properties.clear();
        }
        archivo = null;
    }

    /** @return archivo actualmente cargado en el sistema. */
    public File getArchivo() {
        return archivo;
    }
}


