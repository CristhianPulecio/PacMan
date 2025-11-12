/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase {@code ConexionBaseDatos}
 *
 * <p>Encargada exclusivamente de establecer y cerrar la conexión con una
 * base de datos MySQL.</p>
 *
 * <p>Los parámetros de conexión (URL, usuario y contraseña) son suministrados
 * desde un archivo .properties cargado por una clase externa.</p>
 *
 * <p><b>Principio aplicado:</b> Single Responsibility (SOLID), ya que esta 
 * clase
 * solo maneja la conexión sin ejecutar operaciones SQL.</p>
 *
 * @author 
 * Cristhian Pulecio
 */
public class ConexionBaseDatos {

    /** URL de conexión a la base de datos (jdbc:mysql://...) */
    private final String url;

    /** Usuario autorizado para la conexión. */
    private final String usuario;

    /** Contraseña del usuario de la base de datos. */
    private final String contrasena;

    /** Objeto Connection que representa la conexión activa. */
    private Connection conexion;

    /**
     * Constructor que inicializa los parámetros de conexión.
     *
     * @param url dirección JDBC de la base de datos.
     * @param usuario nombre del usuario de la BD.
     * @param contrasena contraseña asociada al usuario.
     */
    public ConexionBaseDatos(String url, String usuario, String contrasena) {
        this.url = url;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /**
     * Establece una conexión con la base de datos MySQL.
     *
     * @throws SQLException si ocurre un error durante el intento de conexión.
     */
    public void conectar() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
        }
    }

    /**
     * Retorna la conexión activa para ser utilizada por los DAOs.
     *
     * @return objeto {@link Connection} conectado a la BD.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión activa, si existe.
     *
     * @throws SQLException si ocurre un error al cerrar la conexión.
     */
    public void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}

