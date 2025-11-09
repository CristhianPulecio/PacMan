/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada exclusivamente de establecer y cerrar la conexión
 * con una base de datos MySQL. Esta clase recibe los parámetros de conexión
 * (URL, usuario y contraseña) desde una clase externa, típicamente obtenidos
 * desde un archivo .properties usando la clase ConexionProperties.
 *
 * Principio aplicado: SINGLE RESPONSIBILITY (SOLID).
 *
 * Esta clase NO implementa operaciones SQL ni lógica del patrón DAO.
 * Únicamente crea y gestiona la conexión.
 * 
 * @author USER
 */
public class ConexionBaseDatos {

    /** URL de la base de datos (jdbc:mysql://...) */
    private final String url;

    /** Usuario de la base de datos */
    private final String usuario;

    /** Contraseña del usuario */
    private final String contrasena;

    /** Objeto Connection activo */
    private Connection conexion;

    /**
     * Constructor que recibe todos los parámetros de conexión.
     *
     * 
     * @param contrasena Contraseña del usuario.
     */
    public ConexionBaseDatos(String url) {
        this.url = url;
        this.usuario = "root";
        this.contrasena = "";
    }

    /**
     * Establece la conexión con la base de datos MySQL.
     *
     * @throws SQLException si ocurre un error durante la conexión.
     */
    public void conectar() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
        }
    }

    /**
     * Retorna la conexión activa para ser utilizada por las clases DAO.
     *
     * @return Objeto Connection conectado a la BD.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión activa si existe.
     *
     * @throws SQLException si ocurre un error durante el cierre.
     */
    public void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
