/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.control;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Clase principal de entrada del módulo servidor.
 * 
 * <p>Su única responsabilidad es inicializar la aplicación del lado servidor,
 * creando una instancia de la clase {@link Servidor}, la cual se encargará de
 * gestionar conexiones, base de datos y clientes.</p>
 *
 * <p>Esta clase no contiene lógica adicional ni configuraciones explícitas.
 * Simplemente delega el control al constructor de {@code Servidor()}.</p>
 *
 * @author 
 * Cristhian Pulecio
 */
public class LauncherServidor {

    /**
     * Método principal (punto de inicio del programa).
     * 
     * <p>Lanza el flujo completo del sistema del servidor Pac-Man,
     * inicializando todos los controladores y vistas asociados.</p>
     *
     * @param args argumentos de línea de comando (no utilizados).
     * @throws IOException si ocurre un error al leer archivos de configuración.
     * @throws SQLException si ocurre un error al conectarse con la base de 
     * datos.
     */
    public static void main(String[] args) throws IOException, SQLException {
        // Inicia el flujo completo del sistema del servidor
        new Servidor();
    }
}


