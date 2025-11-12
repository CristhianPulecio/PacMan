/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase {@code ConexionAleatorio}
 *
 * <p>Encargada únicamente de gestionar la conexión con un archivo de acceso 
 * aleatorio
 * mediante {@link RandomAccessFile}. Permite abrir, cerrar y recuperar el 
 * archivo,
 * pero no implementa lógica de lectura o escritura.</p>
 *
 * <p>Modo de apertura: <b>"rw"</b> (lectura y escritura).</p>
 *
 * <p><b>Principio aplicado:</b> Single Responsibility (SOLID), ya que su 
 * función
 * es exclusivamente mantener la conexión al archivo.</p>
 *
 * @author 
 * Cristhian Pulecio
 */
public class ConexionAleatorio {

    /** Archivo físico sobre el cual se realizará acceso aleatorio. */
    private File archivo;

    /** Objeto RandomAccessFile que permite manipulación por posiciones. */
    private RandomAccessFile raf;

    /**
     * Constructor vacío. 
     * El archivo será definido posteriormente mediante 
     * {@link #abrirArchivo(File)}.
     */
    public ConexionAleatorio() { }

    /**
     * Abre o crea el archivo aleatorio con permisos de lectura y escritura.
     *
     * @param archivo archivo físico que se usará para acceso aleatorio.
     * @throws IOException si ocurre un error al intentar abrir el archivo.
     */
    public void abrirArchivo(File archivo) throws IOException {
        this.archivo = archivo;
        this.raf = new RandomAccessFile(archivo, "rw");
    }

    /**
     * Retorna el objeto {@link RandomAccessFile} activo para que el DAO
     * correspondiente realice operaciones sobre el archivo.
     *
     * @return instancia de RandomAccessFile abierta.
     */
    public RandomAccessFile getRandomAccessFile() {
        return raf;
    }

    /**
     * Cierra la conexión al archivo aleatorio si está abierta.
     *
     * @throws IOException si ocurre un error durante el cierre.
     */
    public void cerrarArchivo() throws IOException {
        if (raf != null) {
            raf.close();
        }
        raf = null;
        archivo = null;
    }

    /**
     * Retorna el archivo físico asociado a la conexión.
     *
     * @return objeto File actualmente vinculado.
     */
    public File getArchivo() {
        return archivo;
    }
}


