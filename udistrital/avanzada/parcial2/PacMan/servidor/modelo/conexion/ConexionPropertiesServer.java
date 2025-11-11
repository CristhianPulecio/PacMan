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
 * Clase responsable únicamente de cargar y gestionar la lectura de un archivo
 * .properties. La ruta del archivo será suministrada por medio de un
 * JFileChooser desde una clase externa.
 *
 * Esta clase NO implementa conexión a BD ni sockets; solo gestiona la carga de
 * propiedades solicitadas por el cliente o el servidor.
 *
 * Principio aplicado: SINGLE RESPONSIBILITY (SOLID)
 * 
 * @author Cristhian Pulecio
 */
public class ConexionPropertiesServer {

    /** Objeto que almacena las propiedades cargadas desde el archivo. */
    private Properties properties;

    /** Ruta del archivo properties cargado. */
    private File archivo;

    /**
     * Constructor vacío. El archivo será recibido posteriormente mediante el método
     * cargarArchivoProperties().
     */
    public ConexionPropertiesServer() {
        this.properties = new Properties();
    }

    /**
     * Carga en memoria el archivo .properties recibido.
     *
     * @param archivo Archivo seleccionado mediante JFileChooser.
     * @throws IOException si no es posible leer el archivo o su contenido.
     */
    public void cargarArchivoProperties(File archivo) throws IOException {
        this.archivo = archivo;

        try (FileInputStream fis = new FileInputStream(archivo)) {
            properties.load(fis);
        }
    }

    /**
     * Recupera el valor de una clave dentro del archivo properties.
     *
     * @param clave Nombre de la propiedad.
     * @return Valor asociado a la clave, o null si no existe.
     */
    public String getProperty(String clave) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(clave);
    }

    /**
     * Libera la referencia al archivo y limpia las propiedades cargadas.
     * Esta operación se considera el "cierre de conexión" para este tipo de recurso.
     */
    public void cerrarConexion() {
        if (properties != null) {
            properties.clear();
        }
        archivo = null;
    }

    /**
     * Retorna el archivo actualmente cargado.
     *
     * @return Archivo .properties utilizado.
     */
    public File getArchivo() {
        return archivo;
    }
}

