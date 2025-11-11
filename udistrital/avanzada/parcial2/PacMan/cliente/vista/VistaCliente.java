package udistrital.avanzada.parcial2.PacMan.cliente.vista;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VistaCliente extends JFrame {

    private JTextField campoIntroducir;
    private JTextArea areaPantalla;

    public VistaCliente() {
        super("Cliente");

        Container contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout(10, 10));

        campoIntroducir = new JTextField();
        campoIntroducir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoIntroducir.setEnabled(true);
        campoIntroducir.addActionListener(e -> {
            String texto = campoIntroducir.getText().trim();
            if (!texto.isEmpty()) {
                areaPantalla.append("CLIENTE>>> " + texto + "\n");
                campoIntroducir.setText("");
            }
        });
        contenedor.add(campoIntroducir, BorderLayout.NORTH);

        areaPantalla = new JTextArea();
        areaPantalla.setEditable(false);
        areaPantalla.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaPantalla.setBackground(new Color(245, 245, 245));
        areaPantalla.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scroll = new JScrollPane(areaPantalla);
        contenedor.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnConectar = new JButton("Conectar");
        JButton btnDesconectar = new JButton("Desconectar");
        JButton btnLimpiar = new JButton("Limpiar chat");

        btnLimpiar.addActionListener(e -> areaPantalla.setText(""));
        btnConectar.addActionListener(e -> areaPantalla.append("Conectando al servidor...\n"));
        btnDesconectar.addActionListener(e -> areaPantalla.append("Conexión cerrada.\n"));

        panelBotones.add(btnConectar);
        panelBotones.add(btnDesconectar);
        panelBotones.add(btnLimpiar);

        contenedor.add(panelBotones, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VistaCliente::new);
    }
}
