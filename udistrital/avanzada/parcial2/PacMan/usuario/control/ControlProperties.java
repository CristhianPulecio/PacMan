/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import java.io.File;
import java.io.IOException;
import udistrital.avanzada.parcial2.PacMan.usuario.modelo.ConexionPropertiesUser;

/**
 * ControlProperties
 *
 * RESPONSABILIDADES:
 * - Recibir desde una clase externa el archivo .properties (JFileChooser).
 * - Crear y administrar ConexionPropertiesUser.
 * - Exponer métodos de acceso a valores comunes del archivo properties.
 *
 * NO crea GUI.
 * NO abre sockets.
 * NO imprime nada.
 *
 * author USER
 */
public class ControlProperties {

    private final ConexionPropertiesUser conexionProperties;

    /**
     * Constructor:
     * Recibe el archivo seleccionado por una clase externa (VistaArchivosUser).
     *
     * @param archivoProperties archivo .properties elegido por el usuario.
     * @throws IOException si ocurre un error leyendo el archivo.
     */
    public ControlProperties(File archivoProperties) throws IOException {
        this.conexionProperties = new ConexionPropertiesUser();
        this.conexionProperties.cargarArchivoProperties(archivoProperties);
    }

    /**
     * Retorna el valor asociado a una clave.
     */
    public String getProperty(String clave) {
        return conexionProperties.getProperty(clave);
    }

    /**
     * Obtiene el usuario definido en el archivo properties.
     */
    public String getUsuario() {
        return conexionProperties.getProperty("usuario");
    }

    /**
     * Obtiene la contraseña definida en el archivo properties.
     */
    public String getContrasena() {
        return conexionProperties.getProperty("contrasena");
    }

    /**
     * Obtiene la IP del servidor.
     */
    public String getIPServidor() {
        return conexionProperties.getProperty("ip_servidor");
    }

    /**
     * Obtiene el puerto del servidor.
     */
    public String getPuertoServidor() {
        return conexionProperties.getProperty("puerto_servidor");
    }

    /**
     * Libera la conexión al archivo properties.
     */
    public void cerrar() {
        conexionProperties.cerrarConexion();
    }
}

