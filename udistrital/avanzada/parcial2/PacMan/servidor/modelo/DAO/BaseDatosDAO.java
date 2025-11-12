/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion.ConexionBaseDatos;

/**
 * Clase {@code BaseDatosDAO}
 * 
 * <p>DAO especializado en el manejo de la tabla <b>usuarios</b> en la base de 
 * datos.</p>
 * 
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Insertar nuevos usuarios en la tabla.</li>
 *   <li>Validar credenciales de usuario (usuario + contraseña).</li>
 * </ul>
 * 
 * <p>Cada método se encarga de abrir y cerrar la conexión
 * asegurando independencia entre operaciones.</p>
 * 
 * <p>Requiere la tabla:</p>
 * <pre>
 * CREATE TABLE usuarios (
 *     usuario VARCHAR(50) PRIMARY KEY,
 *     contrasena VARCHAR(100) NOT NULL
 * );
 * </pre>
 * 
 * @author 
 * Cristhian Pulecio
 */
public class BaseDatosDAO {

    /** Conexión general a la base de datos, administrada externamente. */
    private final ConexionBaseDatos conexionBD;

    /**
     * Constructor del DAO.
     * 
     * @param conexionBD objeto de conexión a la base de datos.
     */
    public BaseDatosDAO(ConexionBaseDatos conexionBD) {
        this.conexionBD = conexionBD;
    }

    /**
     * Inserta un nuevo usuario en la tabla de base de datos.
     * 
     * <p>Abre la conexión, ejecuta la sentencia SQL y la cierra al 
     * finalizar.</p>
     * 
     * @param usuario nombre del usuario.
     * @param contrasena contraseña asociada.
     * @throws SQLException si ocurre un error al insertar.
     */
    public void insertarUsuario(String usuario, String contrasena) throws 
            SQLException {
        String sql = "INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)";

        try {
            conexionBD.conectar();
            Connection cn = conexionBD.getConexion();

            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);
                ps.executeUpdate();
                ps.close();
            }

        } finally {
            conexionBD.cerrarConexion();
        }
    }

    /**
     * Verifica si un usuario con la contraseña indicada existe en la base de
     * datos.
     * 
     * @param usuario nombre del usuario.
     * @param contrasena contraseña a validar.
     * @return {@code true} si las credenciales son válidas, {@code false} si 
     * no existen.
     * @throws SQLException si ocurre un error en la consulta SQL.
     */
    public boolean validarUsuario(String usuario, String contrasena) 
            throws SQLException {
        String sql = "SELECT usuario FROM usuarios WHERE usuario = ? AND "
                + "contrasena = ?";

        try {
            conexionBD.conectar();
            Connection cn = conexionBD.getConexion();

            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }

        } finally {
            conexionBD.cerrarConexion();
        }
    }
}


