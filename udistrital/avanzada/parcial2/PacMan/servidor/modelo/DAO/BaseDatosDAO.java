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
 * DAO encargado exclusivamente de interactuar con la tabla de usuarios.
 *
 * Ahora cada método abre y cierra la conexión al terminar su operación,
 * cumpliendo estrictamente lo solicitado.
 *
 * Funciones:
 * 1. Insertar usuario (usuario, contrasena)
 * 2. Validar usuario (usuario, contrasena)
 *
 * No se usa ningún objeto de dominio; cada método recibe los parámetros.
 *
 * Principio aplicado: SINGLE RESPONSIBILITY (SOLID).
 * 
 * Requiere tabla:
 * CREATE TABLE usuarios (
 *     usuario VARCHAR(50) PRIMARY KEY,
 *     contrasena VARCHAR(100) NOT NULL
 * );
 * 
 * @author USER
 */
public class BaseDatosDAO {

    /** Conexión proporcionada externamente. */
    private final ConexionBaseDatos conexionBD;

    /**
     * Constructor del DAO.
     * @param conexionBD instancia activa de ConexionBaseDatos.
     */
    public BaseDatosDAO(ConexionBaseDatos conexionBD) {
        this.conexionBD = conexionBD;
    }

    /**
     * Inserta un usuario en la base de datos.
     * Este método abre la conexión y la cierra al finalizar.
     *
     * @param usuario    nombre del usuario.
     * @param contrasena contraseña del usuario.
     * @throws SQLException si ocurre un error durante la inserción.
     */
    public void insertarUsuario(String usuario, String contrasena) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)";

        try {
            conexionBD.conectar(); // Abrir conexión
            Connection cn = conexionBD.getConexion();

            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);
                ps.executeUpdate();
                ps.close();
            }

        } finally {
            conexionBD.cerrarConexion(); // Cerrar conexión obligatoriamente
        }
    }

    /**
     * Valida si un usuario existe en la base de datos con la contraseña dada.
     * Este método abre la conexión y la cierra al finalizar.
     *
     * @param usuario    nombre del usuario a buscar.
     * @param contrasena contraseña a validar.
     * @return true si las credenciales son válidas, false si no existen.
     * @throws SQLException si ocurre un error en la consulta.
     */
    public boolean validarUsuario(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT usuario FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try {
            conexionBD.conectar(); // Abrir conexión
            Connection cn = conexionBD.getConexion();

            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();

                }                
            }

        } finally {
            
            conexionBD.cerrarConexion(); // Cerrar conexión obligatoriamente
        }
    }
}

