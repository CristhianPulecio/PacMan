/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase {@code AleatorioDAO}
 * 
 * <p>DAO encargado de manejar el almacenamiento y lectura de registros
 * binarios en un archivo aleatorio. Cada registro contiene la información
 * de un jugador: nombre, puntaje y tiempo.</p>
 * 
 * <p>Estructura de cada registro:</p>
 * <ul>
 *   <li><b>nombre:</b> 30 caracteres (60 bytes)</li>
 *   <li><b>puntaje:</b> entero (4 bytes)</li>
 *   <li><b>tiempo:</b> long (8 bytes)</li>
 * </ul>
 * <p>Total = 72 bytes por registro.</p>
 * 
 * <p>El archivo se manipula con {@link RandomAccessFile} permitiendo lectura y 
 * escritura
 * directa sin cargar todo el contenido en memoria.</p>
 * 
 * @author 
 * Cristhian Pulecio
 */
public class AleatorioDAO {

    /** Tamaño máximo permitido para el nombre (en caracteres) */
    private static final int TAM_NOMBRE = 30;

    /** Tamaño total de cada registro en bytes */
    private static final int TAM_REGISTRO = 72;

    /** Archivo físico donde se almacenan los registros */
    private final File archivo;

    /** Acceso aleatorio al archivo */
    private RandomAccessFile raf;

    /**
     * Constructor del DAO.
     * 
     * @param archivoArchivo archivo binario donde se almacenarán los 
     * registros.
     */
    public AleatorioDAO(File archivoArchivo) {
        this.archivo = archivoArchivo;
    }

    /** Abre el archivo en modo lectura/escritura ("rw"). */
    private void abrir() throws IOException {
        raf = new RandomAccessFile(archivo, "rw");
    }

    /** Cierra el archivo si está abierto. */
    private void cerrar() throws IOException {
        if (raf != null) raf.close();
    }

    /**
     * Guarda un nuevo registro en el archivo aleatorio con los valores 
     * indicados.
     * 
     * @param nombre nombre del jugador (máx. 30 caracteres).
     * @param puntaje puntaje obtenido.
     * @param tiempo tiempo total en milisegundos.
     * @throws IOException si ocurre un error al escribir en el archivo.
     */
    public void guardarRegistro(String nombre, int puntaje, long tiempo) 
            throws IOException {
        try {
            abrir();

            // Calcular posición al final del archivo
            long numReg = raf.length() / TAM_REGISTRO;
            long pos = numReg * TAM_REGISTRO;
            raf.seek(pos);

            // Escribir los datos
            escribirNombreFijo(nombre);
            raf.writeInt(puntaje);
            raf.writeLong(tiempo);

        } finally {
            cerrar();
        }
    }

    /**
     * Guarda un registro tomando los valores desde un {@link Map}.
     * 
     * <p>Claves esperadas:</p>
     * <ul>
     *   <li>"usuario" → String</li>
     *   <li>"puntaje" → Integer</li>
     *   <li>"tiempo"  → Long</li>
     * </ul>
     * 
     * @param datos mapa con la información del jugador.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public void guardarRegistro(Map<String, Object> datos) throws IOException {
        String nombre = (String) datos.get("usuario");
        int puntaje = (int) datos.get("puntaje");
        long tiempo = (long) datos.get("tiempo");

        guardarRegistro(nombre, puntaje, tiempo);
    }

    /**
     * Escribe el nombre ajustándolo al tamaño fijo (30 caracteres).
     * Si es más corto, se completa con espacios.
     */
    private void escribirNombreFijo(String nombre) throws IOException {
        StringBuilder sb = new StringBuilder(nombre);

        if (sb.length() > TAM_NOMBRE) {
            sb.setLength(TAM_NOMBRE);
        } else {
            while (sb.length() < TAM_NOMBRE) sb.append(" ");
        }

        raf.writeChars(sb.toString());
    }

    /**
     * Lee un nombre de longitud fija (30 caracteres) desde el archivo.
     * 
     * @return nombre leído sin espacios sobrantes.
     */
    private String leerNombreFijo() throws IOException {
        char[] buffer = new char[TAM_NOMBRE];
        for (int i = 0; i < TAM_NOMBRE; i++) {
            buffer[i] = raf.readChar();
        }
        return new String(buffer).trim();
    }

    /**
     * Lee todos los registros del archivo y los devuelve como una lista de 
     * mapas.
     * 
     * @return lista de registros con claves "usuario", "puntaje" y "tiempo".
     * @throws IOException si ocurre un error al leer.
     */
    public List<Map<String,Object>> leerTodos() throws IOException {
        List<Map<String,Object>> lista = new ArrayList<>();

        try {
            abrir();
            long totalReg = raf.length() / TAM_REGISTRO;

            for (int i = 0; i < totalReg; i++) {
                raf.seek(i * TAM_REGISTRO);

                String nombre = leerNombreFijo();
                int puntaje = raf.readInt();
                long tiempo = raf.readLong();

                Map<String,Object> registro = Map.of(
                    "usuario", nombre,
                    "puntaje", puntaje,
                    "tiempo", tiempo
                );

                lista.add(registro);
            }

            return lista;

        } finally {
            cerrar();
        }
    }

    /**
     * Busca el mejor jugador en el archivo.
     * 
     * <p>Se selecciona el de mayor puntaje, y en caso de empate, el de menor 
     * tiempo.</p>
     * 
     * @return mapa con los datos del mejor jugador, o {@code null} si no hay 
     * registros.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    public Map<String,Object> obtenerMejorJugador() throws IOException {
        List<Map<String,Object>> registros = leerTodos();
        if (registros.isEmpty()) return null;

        Map<String,Object> mejor = registros.get(0);

        for (int i = 1; i < registros.size(); i++) {
            Map<String,Object> r = registros.get(i);

            int p1 = (int) mejor.get("puntaje");
            int p2 = (int) r.get("puntaje");

            long t1 = (long) mejor.get("tiempo");
            long t2 = (long) r.get("tiempo");

            if (p2 > p1 || (p2 == p1 && t2 < t1)) {
                mejor = r;
            }
        }

        return mejor;
    }
}



