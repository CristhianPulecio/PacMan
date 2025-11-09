/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO;

import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion.ConexionProperties;

/**
 * DAO encargado de leer información desde un archivo de propiedades.
 * 
 * Esta clase obtiene:
 *  - Lista de usuarios
 *  - Lista de contraseñas
 *  - Puerto del servidor
 *  - URL de conexión a la base de datos
 *
 * No se manejan objetos de negocio; todo se gestiona mediante Strings.
 *
 * El archivo .properties debe contener claves como:
 *
 * usuarios=juan,pedro,maria
 * contrasenas=1234,abcd,pass
 * puerto=5000
 * db_url=jdbc:mysql://localhost:3306/pacman
 *
 * Principio aplicado: SINGLE RESPONSIBILITY (SOLID).
 *
 * Esta clase solo lee valores y genera estructuras simples.
 * 
 * Requiere que el archivo ya haya sido cargado mediante ConexionProperties.
 *
 * author USER
 */
public class PropertiesDAO {

    /** Referencia al loader de propiedades. */
    private final ConexionProperties conexionProperties;

    /**
     * Constructor del DAO.
     *
     * @param conexionProperties instancia ya cargada con el archivo .properties.
     */
    public PropertiesDAO(ConexionProperties conexionProperties) {
        this.conexionProperties = conexionProperties;
    }

    /**
     * Lee la lista de usuarios del archivo .properties.
     * 
     * @return Lista de Strings con usuarios. Nunca retorna null.
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
     * Lee la lista de contraseñas del archivo .properties.
     *
     * @return Lista de Strings con contraseñas. Nunca retorna null.
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
     * Obtiene el puerto del servidor definido en el archivo .properties.
     *
     * @return valor del puerto como String (puede convertirse a int fuera).
     */
    public String obtenerPuerto() {
        return conexionProperties.getProperty("puerto");
    }

    /**
     * Obtiene la URL de conexión a la base de datos.
     *
     * @return URL como String.
     */
    public String obtenerUrlBaseDatos() {
        return conexionProperties.getProperty("db_url");
    }
}

