/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.servidor.modelo.DAO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * AleatorioDAO
 * 
 * Gestiona un archivo binario de acceso aleatorio donde cada registro
 * tiene tamaño fijo y almacena:
 *  - nombre del jugador (30 caracteres fijos)
 *  - puntaje (int)
 *  - tiempo total (long)
 *
 * Cada registro tiene un tamaño fijo de 72 bytes.
 *
 * author USER
 */
public class AleatorioDAO {

    /** Tamaño fijo del nombre (30 caracteres = 60 bytes UTF-16). */
    private static final int TAM_NOMBRE = 30;

    /** Tamaño total en bytes por registro. */
    private static final int TAM_REGISTRO = 72;

    private final File archivo;
    private RandomAccessFile raf;

    /**
     * Constructor.
     * @param archivoArchivo archivo binario donde se guardan registros
     */
    public AleatorioDAO(File archivoArchivo) {
        this.archivo = archivoArchivo;
    }

    /**
     * Abre el archivo para lectura/escritura en modo aleatorio.
     */
    private void abrir() throws IOException {
        raf = new RandomAccessFile(archivo, "rw");
    }

    /**
     * Cierra el archivo.
     */
    private void cerrar() throws IOException {
        if (raf != null) raf.close();
    }

    /**
     * Escribe un registro al final del archivo.
     * 
     * @param nombre nombre del jugador
     * @param puntaje puntaje final
     * @param tiempo tiempo total
     */
    public void guardarRegistro(String nombre, int puntaje, long tiempo) throws IOException {
        try {
            abrir();
            long numReg = raf.length() / TAM_REGISTRO;
            long pos = numReg * TAM_REGISTRO;
            raf.seek(pos);

            escribirNombreFijo(nombre);
            raf.writeInt(puntaje);
            raf.writeLong(tiempo);

        } finally {
            cerrar();
        }
    }

    /**
     * Escribe un nombre de tamaño fijo (30 caracteres).
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
     * Lee un nombre de tamaño fijo (30 chars).
     */
    private String leerNombreFijo() throws IOException {
        char[] buffer = new char[TAM_NOMBRE];
        for (int i = 0; i < TAM_NOMBRE; i++) {
            buffer[i] = raf.readChar();
        }
        return new String(buffer).trim();
    }

    /**
     * Representa un registro individual.
     */
    public static class Registro {
        public String nombre;
        public int puntaje;
        public long tiempo;

        public Registro(String nombre, int puntaje, long tiempo) {
            this.nombre = nombre;
            this.puntaje = puntaje;
            this.tiempo = tiempo;
        }
    }

    /**
     * Lee todos los registros del archivo.
     */
    public Registro[] leerTodos() throws IOException {
        try {
            abrir();
            long totalReg = raf.length() / TAM_REGISTRO;
            Registro[] lista = new Registro[(int) totalReg];

            for (int i = 0; i < totalReg; i++) {
                raf.seek(i * TAM_REGISTRO);
                String nombre = leerNombreFijo();
                int puntaje = raf.readInt();
                long tiempo = raf.readLong();
                lista[i] = new Registro(nombre, puntaje, tiempo);
            }

            return lista;

        } finally {
            cerrar();
        }
    }

    /**
     * Obtiene el mejor jugador:
     * - Mayor puntaje
     * - Si hay empate, menor tiempo
     */
    public Registro obtenerMejorJugador() throws IOException {
        Registro[] registros = leerTodos();

        if (registros.length == 0) return null;

        Registro mejor = registros[0];

        for (int i = 1; i < registros.length; i++) {
            Registro r = registros[i];

            if (r.puntaje > mejor.puntaje) {
                mejor = r;
            } else if (r.puntaje == mejor.puntaje && r.tiempo < mejor.tiempo) {
                mejor = r;
            }
        }

        return mejor;
    }
}

