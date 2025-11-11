/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.parcial2.PacMan.usuario.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class VistaUser extends JFrame {

    private final JButton btnArriba;
    private final JButton btnAbajo;
    private final JButton btnIzquierda;
    private final JButton btnDerecha;
    private final JButton btnSalir;

    private final JTextArea areaMensajes;

    public VistaUser() {
        super("Cliente Pac-Man");
        setSize(400, 450);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelBotones = new JPanel(new GridLayout(3, 3));

        btnArriba = new JButton("Arriba");
        btnAbajo = new JButton("Abajo");
        btnIzquierda = new JButton("Izquierda");
        btnDerecha = new JButton("Derecha");
        btnSalir = new JButton("Salir");

        panelBotones.add(new JPanel());
        panelBotones.add(btnArriba);
        panelBotones.add(new JPanel());

        panelBotones.add(btnIzquierda);
        panelBotones.add(btnSalir);
        panelBotones.add(btnDerecha);

        panelBotones.add(new JPanel());
        panelBotones.add(btnAbajo);
        panelBotones.add(new JPanel());

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(areaMensajes), BorderLayout.CENTER);

        setVisible(true);
    }

    public JButton getBtnArriba() { return btnArriba; }
    public JButton getBtnAbajo() { return btnAbajo; }
    public JButton getBtnIzquierda() { return btnIzquierda; }
    public JButton getBtnDerecha() { return btnDerecha; }
    public JButton getBtnSalir() { return btnSalir; }

    public void mostrarMensaje(String msg) {
        areaMensajes.append(msg + "\n");
    }
}

