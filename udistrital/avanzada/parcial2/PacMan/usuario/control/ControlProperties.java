/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import java.io.File;
import java.io.IOException;
import udistrital.avanzada.parcial2.PacMan.usuario.modelo.ConexionPropertiesUser;

/**
 * Clase {@code ControlProperties}
 * 
 * <p>Responsable de gestionar el acceso al archivo de configuración
 * <b>.properties</b> del cliente Pac-Man.</p>
 *
 * <p>Sus principales responsabilidades son:</p>
 * <ul>
 *   <li>Recibir desde una vista el archivo .properties seleccionado.</li>
 *   <li>Crear y administrar la conexión mediante 
 * {@link ConexionPropertiesUser}.</li>
 *   <li>Proporcionar acceso a los valores de configuración, como usuario,
 *       contraseña, IP y puerto del servidor.</li>
 * </ul>
 *
 * <p>Esta clase no imprime, no maneja sockets y no crea interfaz gráfica.</p>
 *
 * <p>Principio aplicado: <b>Single Responsibility (SRP)</b>.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class ControlProperties {

    /** Objeto de conexión encargado de manejar el archivo .properties. */
    private final ConexionPropertiesUser conexionProperties;

    /**
     * Constructor principal que carga el archivo de propiedades seleccionado.
     *
     * @param archivoProperties archivo .properties elegido por el usuario.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    public ControlProperties(File archivoProperties) throws IOException {
        this.conexionProperties = new ConexionPropertiesUser();
        this.conexionProperties.cargarArchivoProperties(archivoProperties);
    }

    /**
     * Retorna el valor asociado a una clave específica dentro del archivo.
     *
     * @param clave clave buscada.
     * @return valor de la propiedad o {@code null} si no existe.
     */
    public String getProperty(String clave) {
        return conexionProperties.getProperty(clave);
    }

    /** @return nombre del usuario definido en el archivo .properties. */
    public String getUsuario() {
        return conexionProperties.getProperty("usuario");
    }

    /** @return contraseña del usuario definida en el archivo .properties. */
    public String getContrasena() {
        return conexionProperties.getProperty("contrasena");
    }

    /** @return dirección IP del servidor. */
    public String getIPServidor() {
        return conexionProperties.getProperty("ip_servidor");
    }

    /** @return puerto configurado para la conexión con el servidor. */
    public String getPuertoServidor() {
        return conexionProperties.getProperty("puerto_servidor");
    }

    /**
     * Cierra y libera los recursos asociados a la conexión del archivo 
     * .properties.
     */
    public void cerrar() {
        conexionProperties.cerrarConexion();
    }
}


