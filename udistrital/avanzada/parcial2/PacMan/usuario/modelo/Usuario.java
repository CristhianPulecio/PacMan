/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.modelo;

/**
 * Clase {@code Usuario}
 *
 * <p>Modelo simple que representa a un usuario del sistema Pac-Man.</p>
 *
 * <p>Contiene únicamente los atributos:</p>
 * <ul>
 *   <li>usuario → nombre o identificador del jugador.</li>
 *   <li>contrasena → clave de acceso del jugador.</li>
 * </ul>
 *
 * <p>Se utiliza como entidad de transferencia de datos entre el control
 * y la capa de comunicación con el servidor.</p>
 *
 * @author 
 * Miguel Hernández
 */
public class Usuario {
    
    /** Nombre del usuario. */
    private String usuario;
    
    /** Contraseña asociada al usuario. */
    private String contrasena;

    /**
     * Constructor principal del modelo Usuario.
     *
     * @param usuario nombre del usuario.
     * @param contrasena contraseña del usuario.
     */
    public Usuario(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /** @return nombre del usuario. */
    public String getUsuario() {
        return usuario;
    }

    /** @param usuario establece un nuevo nombre de usuario. */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /** @return contraseña del usuario. */
    public String getContrasena() {
        return contrasena;
    }

    /** @param contrasena establece una nueva contraseña. */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

