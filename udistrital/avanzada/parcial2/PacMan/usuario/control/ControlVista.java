/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.control;

import udistrital.avanzada.parcial2.PacMan.usuario.vista.VistaUser;

public class ControlVista {

    private final VistaUser vista;
    private Runnable accionArriba;
    private Runnable accionAbajo;
    private Runnable accionIzquierda;
    private Runnable accionDerecha;
    private Runnable accionSalir;

    public ControlVista() {
        this.vista = new VistaUser();
        configurarEventos();
    }

    private void configurarEventos() {

        vista.getBtnArriba().addActionListener(e -> {
            if (accionArriba != null) accionArriba.run();
        });

        vista.getBtnAbajo().addActionListener(e -> {
            if (accionAbajo != null) accionAbajo.run();
        });

        vista.getBtnIzquierda().addActionListener(e -> {
            if (accionIzquierda != null) accionIzquierda.run();
        });

        vista.getBtnDerecha().addActionListener(e -> {
            if (accionDerecha != null) accionDerecha.run();
        });

        vista.getBtnSalir().addActionListener(e -> {
            if (accionSalir != null) accionSalir.run();
        });
    }
    
    public void mostrarMensaje(String respuesta){
        
        vista.mostrarMensaje(respuesta);
    }

    // ======== estos son los performers que UsuarioConexion asignará =========

    public void setOnArriba(Runnable r) { this.accionArriba = r; }
    public void setOnAbajo(Runnable r) { this.accionAbajo = r; }
    public void setOnIzquierda(Runnable r) { this.accionIzquierda = r; }
    public void setOnDerecha(Runnable r) { this.accionDerecha = r; }
    public void setOnSalir(Runnable r) { this.accionSalir = r; }

    public VistaUser getVista() {
        return vista;
    }
}

