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
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion.ConexionPropertiesServer;

/**
 * Clase que centraliza la conexión entre los diferentes componentes del servidor.
 * 
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Cargar y leer el archivo de configuración (.properties).</li>
 *   <li>Inicializar las conexiones a base de datos y al archivo aleatorio.</li>
 *   <li>Instanciar los DAOs (Data Access Objects) necesarios para operar.</li>
 *   <li>Registrar usuarios desde el archivo de propiedades en la base de datos.</li>
 *   <li>Guardar los resultados finales de cada jugador en el archivo aleatorio.</li>
 * </ul>
 *
 * <p>No se encarga de manejar sockets, ni de mostrar interfaz gráfica.
 * Su objetivo es proveer servicios de acceso a datos al resto de controladores.</p>
 *
 * @author Cristhian Pulecio
 */
public class ControlConexion {

    /** Conexión hacia el archivo de propiedades (.properties) */
    private final ConexionPropertiesServer conexionProperties;
    
    /** DAO para manejar los valores del archivo de propiedades */
    private final PropertiesDAO propertiesDAO;

    /** Conexión hacia la base de datos */
    private final ConexionBaseDatos conexionBaseDatos;
    
    /** DAO que maneja operaciones sobre la base de datos */
    private final BaseDatosDAO baseDatosDAO;

    /** DAO para la gestión del archivo aleatorio (.dat) */
    private final AleatorioDAO aleatorioDAO;

    /**
     * Constructor principal que inicializa todas las conexiones y DAOs 
     * necesarios para el funcionamiento del servidor.
     *
     * @param archivoProperties archivo de configuración .properties elegido por 
     * el usuario.
     * @param archivoAleatorio archivo binario aleatorio (.dat) donde se guardan
     * los resultados.
     * @throws IOException si ocurre un error al leer el archivo de propiedades.
     */
    public ControlConexion(File archivoProperties, File archivoAleatorio) throws 
            IOException {

        //1. Cargar archivo .properties y leer su contenido
        this.conexionProperties = new ConexionPropertiesServer();
        this.conexionProperties.cargarArchivoProperties(archivoProperties);

        //2. Crear el DAO asociado al archivo de propiedades
        this.propertiesDAO = new PropertiesDAO(conexionProperties);

        //3. Crear conexión a la base de datos usando los valores del properties
        String urlBD = propertiesDAO.obtenerUrlBaseDatos();
        String usuario = propertiesDAO.obtenerUsuarioBaseDatos();
        String contrasena = propertiesDAO.obtenerContrasenaBaseDatos();
        this.conexionBaseDatos = new ConexionBaseDatos(urlBD, usuario, 
                contrasena);

        // 4. Crear DAO que maneja las operaciones sobre la base de datos
        this.baseDatosDAO = new BaseDatosDAO(conexionBaseDatos);

        // 5. Crear DAO que maneja operaciones sobre el archivo aleatorio
        this.aleatorioDAO = new AleatorioDAO(archivoAleatorio);
    }

    /**
     * Inserta en la base de datos todos los usuarios definidos en el archivo
     * .properties, junto con sus respectivas contraseñas.
     *
     * @throws SQLException si ocurre un error durante la inserción en la base 
     * de datos.
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
     * Guarda en el archivo aleatorio la información de una partida terminada.
     *
     * @param usuario nombre del jugador.
     * @param puntaje puntaje final obtenido.
     * @param tiempo tiempo total de la partida.
     * @throws IOException si ocurre un error al escribir en el archivo .dat.
     */
    public void guardarResultadoEnAleatorio(String usuario, int puntaje, long 
            tiempo)
            throws IOException {

        aleatorioDAO.guardarRegistro(usuario, puntaje, tiempo);
    }

    /**
     * Valida las credenciales de un usuario consultando la base de datos.
     *
     * @param usuario nombre de usuario.
     * @param contrasena contraseña asociada.
     * @return true si las credenciales son correctas, false en caso contrario.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public boolean validarUsuario(String usuario, String contrasena) throws 
            SQLException {
        return baseDatosDAO.validarUsuario(usuario, contrasena);
    }


    /** @return DAO asociado al archivo .properties */
    public PropertiesDAO getPropertiesDAO() {
        return propertiesDAO;
    }

    /** @return DAO asociado a la base de datos */
    public BaseDatosDAO getBaseDatosDAO() {
        return baseDatosDAO;
    }

    /** @return DAO asociado al archivo aleatorio */
    public AleatorioDAO getAleatorioDAO() {
        return aleatorioDAO;
    }
}



