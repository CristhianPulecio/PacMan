/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO;

import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion.ConexionPropertiesServer;

/**
 * Clase {@code PropertiesDAO}
 * 
 * <p>DAO encargado de obtener información desde un archivo de propiedades 
 * (.properties)
 * previamente cargado con {@link ConexionPropertiesServer}.</p>
 * 
 * <p>El archivo debe contener claves como:</p>
 * <pre>
 * usuarios=juan,pedro,maria
 * contrasenas=1234,abcd,pass
 * puerto=5000
 * db_url=jdbc:mysql://localhost:3306/pacman
 * usuarioBD=root
 * contrasenaBD=1234
 * </pre>
 * 
 * <p>Permite obtener listas de usuarios, contraseñas, el puerto del servidor
 * y los datos de conexión a la base de datos.</p>
 * 
 * @author 
 * Cristhian Pulecio
 */
public class PropertiesDAO {

    /** Referencia a la conexión con el archivo .properties. */
    private final ConexionPropertiesServer conexionProperties;

    /**
     * Constructor del DAO.
     * 
     * @param conexionProperties instancia ya inicializada con el archivo de 
     * propiedades.
     */
    public PropertiesDAO(ConexionPropertiesServer conexionProperties) {
        this.conexionProperties = conexionProperties;
    }

    /**
     * Carga la lista de usuarios definidos en el archivo .properties.
     * 
     * @return lista de usuarios como {@code List<String>}, nunca null.
     */
    public List<String> cargarUsuarios() {
        List<String> lista = new ArrayList<>();

        String data = conexionProperties.getProperty("usuarios");
        if (data != null && !data.isEmpty()) {
            String[] arr = data.split(",");
            for (String u : arr) {
                lista.add(u.trim());
            }
        }
        return lista;
    }

    /**
     * Carga la lista de contraseñas definidas en el archivo .properties.
     * 
     * @return lista de contraseñas como {@code List<String>}, nunca null.
     */
    public List<String> cargarContrasenas() {
        List<String> lista = new ArrayList<>();

        String data = conexionProperties.getProperty("contrasenas");
        if (data != null && !data.isEmpty()) {
            String[] arr = data.split(",");
            for (String c : arr) {
                lista.add(c.trim());
            }
        }
        return lista;
    }

    /**
     * Obtiene el puerto configurado en el archivo .properties.
     * 
     * @return puerto como cadena de texto (debe convertirse a entero 
     * externamente).
     */
    public String obtenerPuerto() {
        return conexionProperties.getProperty("puerto");
    }

    /**
     * Obtiene la URL de conexión a la base de datos.
     * 
     * @return cadena con la URL JDBC.
     */
    public String obtenerUrlBaseDatos() {
        return conexionProperties.getProperty("db_url");
    }

    /**
     * Obtiene el usuario de la base de datos definido en el archivo.
     * 
     * @return nombre de usuario para conexión a la BD.
     */
    public String obtenerUsuarioBaseDatos() {
        return conexionProperties.getProperty("usuarioBD");
    }

    /**
     * Obtiene la contraseña de la base de datos definida en el archivo.
     * 
     * @return contraseña de conexión a la BD.
     */
    public String obtenerContrasenaBaseDatos() {
        return conexionProperties.getProperty("contrasenaBD");
    }
}


