/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.conexion;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase responsable únicamente de establecer y cerrar la conexión hacia
 * un archivo de acceso aleatorio (RandomAccessFile). El archivo permite
 * lecturas y escrituras basadas en posiciones y tamaños específicos de bytes.
 *
 * Esta clase NO implementa la lógica de lectura ni escritura de registros.
 * Solo gestiona la creación y administración del objeto RandomAccessFile,
 * para ser usado posteriormente por un DAO de acceso aleatorio.
 *
 * Principio aplicado: SINGLE RESPONSIBILITY (SOLID).
 * 
 * Modo de apertura: "rw" (lectura y escritura).
 *
 * @author USER
 */
public class ConexionAleatorio {

    /** Archivo físico sobre el que se realizará acceso aleatorio. */
    private File archivo;

    /** Objeto RandomAccessFile que permite operar por posiciones. */
    private RandomAccessFile raf;

    /**
     * Constructor vacío. El archivo será recibido posteriormente mediante
     * abrirArchivo().
     */
    public ConexionAleatorio() { }

    /**
     * Abre o crea el archivo aleatorio con permisos de lectura y escritura.
     *
     * @param archivo Archivo físico que se usará para acceso aleatorio.
     * @throws IOException si ocurre un error al intentar abrirlo.
     */
    public void abrirArchivo(File archivo) throws IOException {
        this.archivo = archivo;
        this.raf = new RandomAccessFile(archivo, "rw");
    }

    /**
     * Retorna el objeto RandomAccessFile para que el DAO pueda leer o
     * escribir en posiciones específicas del archivo.
     *
     * @return Objeto RandomAccessFile conectado.
     */
    public RandomAccessFile getRandomAccessFile() {
        return raf;
    }

    /**
     * Cierra la conexión del archivo aleatorio si está abierta.
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
     * @return Archivo utilizado por RandomAccessFile.
     */
    public File getArchivo() {
        return archivo;
    }
}

