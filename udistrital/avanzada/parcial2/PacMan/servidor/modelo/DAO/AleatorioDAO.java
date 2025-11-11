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
 * AleatorioDAO
 *
 * Guarda registros en archivo binario aleatorio.
 * Cada registro tiene:
 *  - nombre (30 chars => 60 bytes)
 *  - puntaje (int 4 bytes)
 *  - tiempo  (long 8 bytes)
 *
 * Total = 72 bytes por registro.
 */
public class AleatorioDAO {

    private static final int TAM_NOMBRE = 30;
    private static final int TAM_REGISTRO = 72;

    private final File archivo;
    private RandomAccessFile raf;

    public AleatorioDAO(File archivoArchivo) {
        this.archivo = archivoArchivo;
    }

    private void abrir() throws IOException {
        raf = new RandomAccessFile(archivo, "rw");
    }

    private void cerrar() throws IOException {
        if (raf != null) raf.close();
    }

    /**
     * Guarda un registro recibiendo valores simples.
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
     * Guarda un registro recibiendo un Map con:
     *  - "usuario" : String
     *  - "puntaje" : Integer
     *  - "tiempo"  : Long
     */
    public void guardarRegistro(Map<String, Object> datos) throws IOException {
        String nombre = (String) datos.get("usuario");
        int puntaje = (int) datos.get("puntaje");
        long tiempo = (long) datos.get("tiempo");

        guardarRegistro(nombre, puntaje, tiempo);
    }

    private void escribirNombreFijo(String nombre) throws IOException {
        StringBuilder sb = new StringBuilder(nombre);

        if (sb.length() > TAM_NOMBRE) {
            sb.setLength(TAM_NOMBRE);
        } else {
            while (sb.length() < TAM_NOMBRE) sb.append(" ");
        }

        raf.writeChars(sb.toString());
    }

    private String leerNombreFijo() throws IOException {
        char[] buffer = new char[TAM_NOMBRE];
        for (int i = 0; i < TAM_NOMBRE; i++) {
            buffer[i] = raf.readChar();
        }
        return new String(buffer).trim();
    }

    /**
     * Lee todos los registros como una lista de Map<String,Object>.
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
     * Obtiene el mejor jugador como un Map:
     *  - mayor puntaje
     *  - en caso de empate, menor tiempo
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


