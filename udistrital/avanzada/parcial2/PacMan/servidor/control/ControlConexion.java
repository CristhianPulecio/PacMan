/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO.AleatorioDAO;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO.BaseDatosDAO;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO.PropertiesDAO;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion.ConexionBaseDatos;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion.ConexionProperties;

/**
 * ControlConexion
 *
 * Flujo real:
 *  - VistaArchivos elige:
 *       * archivoProperties
 *       * archivoAleatorio
 *  - Servidor crea ControlConexion con esos archivos
 *  - ControlConexion crea TODOS los DAOs necesarios
 *
 * Responsabilidades:
 * 1. Cargar el archivo .properties.
 * 2. Crear PropertiesDAO.
 * 3. Crear BaseDatosDAO (usando URL del properties).
 * 4. Crear AleatorioDAO.
 * 5. Registrar los usuarios en BD desde properties.
 * 6. Permitir guardar puntaje final en archivo aleatorio.
 *
 * NO maneja sockets.
 * NO crea GUI.
 * NO imprime nada.
 *
 * author USER
 */
public class ControlConexion {

    private final ConexionProperties conexionProperties;
    private final PropertiesDAO propertiesDAO;

    private final ConexionBaseDatos conexionBaseDatos;
    private final BaseDatosDAO baseDatosDAO;

    private final AleatorioDAO aleatorioDAO;

    /**
     * Constructor principal.
     *
     * @param archivoProperties archivo .properties elegido por el usuario.
     * @param archivoAleatorio archivo binario aleatorio elegido por el usuario.
     * @throws IOException si falla lectura del properties.
     */
    public ControlConexion(File archivoProperties, File archivoAleatorio) throws IOException {

        // 1. Cargar archivo properties
        this.conexionProperties = new ConexionProperties();
        this.conexionProperties.cargarArchivoProperties(archivoProperties);

        // 2. Crear DAO de properties
        this.propertiesDAO = new PropertiesDAO(conexionProperties);

        // 3. Crear conexión a la base de datos usando URL del properties
        String urlBD = propertiesDAO.obtenerUrlBaseDatos();
        String usuario = propertiesDAO.obtenerUsuarioBaseDatos();
        String contrasena = propertiesDAO.obtenerContrasenaBaseDatos();
        this.conexionBaseDatos = new ConexionBaseDatos(urlBD, usuario, contrasena);

        // 4. Crear DAO de base de datos
        this.baseDatosDAO = new BaseDatosDAO(conexionBaseDatos);

        // 5. Crear DAO para archivo aleatorio
        this.aleatorioDAO = new AleatorioDAO(archivoAleatorio);
    }

    /**
     * Inserta en la base de datos todos los usuarios presentes en el .properties.
     */
    public void registrarUsuariosDesdeProperties() throws SQLException {

        List<String> usuarios = propertiesDAO.cargarUsuarios();
        List<String> contrasenas = propertiesDAO.cargarContrasenas();

        int total = Math.min(usuarios.size(), contrasenas.size());

        for (int i = 0; i < total; i++) {
            baseDatosDAO.insertarUsuario(usuarios.get(i), contrasenas.get(i));
        }
    }

    /**
     * Guarda el registro final de un jugador al terminar su partida.
     */
    public void guardarResultadoEnAleatorio(String usuario, int puntaje, long tiempo)
            throws IOException {

        aleatorioDAO.guardarRegistro(usuario, puntaje, tiempo);
    }
    
    public boolean validarUsuario(String usuario, String contrasena) throws SQLException {
        return baseDatosDAO.validarUsuario(usuario, contrasena);
    }

    /* ======================================================
       GETTERS PARA EL SERVIDOR Y LOS THREADSERVIDOR
       ====================================================== */

    public PropertiesDAO getPropertiesDAO() {
        return propertiesDAO;
    }

    public BaseDatosDAO getBaseDatosDAO() {
        return baseDatosDAO;
    }

    public AleatorioDAO getAleatorioDAO() {
        return aleatorioDAO;
    }
}


