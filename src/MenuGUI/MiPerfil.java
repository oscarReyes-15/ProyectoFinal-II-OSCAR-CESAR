package MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;
import java.io.*;

public class MiPerfil extends JFrame implements ActionListener {
    private JButton regresarBtn, cambiarContraBtn, eliminarCuentaBtn, cambiarAvatarBtn;
    private String usuarioActual;
    private JLabel usuarioLabel, puntosLabel, fechaLabel, avatarLabel, tiempoJugadoLabel, partidasJugadasLabel, nivelMaximoLabel, fondo;
    private User userData;
    private JPanel avatarPanel;

    public MiPerfil(String usuario) {
        this.usuarioActual = usuario;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Mi Perfil");
        this.setLayout(null);
        this.setSize(800, 450); // Se ajusta la resolución
        this.setLocationRelativeTo(null);

        // Cargar imagen de fondo
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);

        userData = UserFile.cargarUsuario(usuarioActual);

        // Panel para el avatar
        avatarPanel = new JPanel();
        avatarPanel.setBounds(550, 50, 150, 150);
        avatarPanel.setBackground(new Color(0, 0, 0, 0)); // Transparente
        this.add(avatarPanel);

        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(120, 120));
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        avatarPanel.add(avatarLabel);

        cambiarAvatarBtn = new JButton("Cambiar Avatar");
        cambiarAvatarBtn.setBounds(550, 210, 150, 30);
        cambiarAvatarBtn.addActionListener(this);
        this.add(cambiarAvatarBtn);

        cambiarContraBtn = new JButton("Cambiar Contraseña");
        cambiarContraBtn.setBounds(50, 290, 200, 30);
        cambiarContraBtn.addActionListener(this);
        this.add(cambiarContraBtn);

        eliminarCuentaBtn = new JButton("Eliminar Cuenta");
        eliminarCuentaBtn.setBounds(50, 330, 200, 30);
        eliminarCuentaBtn.addActionListener(this);
        this.add(eliminarCuentaBtn);

        regresarBtn = new JButton("Regresar");
        regresarBtn.setBounds(50, 370, 200, 30);
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);

        usuarioLabel = new JLabel();
        usuarioLabel.setBounds(50, 50, 250, 30);
        usuarioLabel.setForeground(Color.white);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(usuarioLabel);

        puntosLabel = new JLabel();
        puntosLabel.setBounds(50, 90, 250, 30);
        puntosLabel.setForeground(Color.white);
        this.add(puntosLabel);

        fechaLabel = new JLabel();
        fechaLabel.setBounds(50, 130, 250, 30);
        fechaLabel.setForeground(Color.white);
        this.add(fechaLabel);

        // Add new labels for game statistics
        nivelMaximoLabel = new JLabel();
        nivelMaximoLabel.setBounds(50, 170, 250, 30);
        nivelMaximoLabel.setForeground(Color.white);
        this.add(nivelMaximoLabel);

        partidasJugadasLabel = new JLabel();
        partidasJugadasLabel.setBounds(50, 210, 250, 30);
        partidasJugadasLabel.setForeground(Color.white);
        this.add(partidasJugadasLabel);

        tiempoJugadoLabel = new JLabel();
        tiempoJugadoLabel.setBounds(50, 250, 250, 30);
        tiempoJugadoLabel.setForeground(Color.white);
        this.add(tiempoJugadoLabel);

        actualizarDatos();

        this.add(fondo); // Se agrega el fondo al final para que esté en la capa más baja

        this.setVisible(true);
    }

    private void actualizarDatos() {
        if (usuarioActual != null) {
            userData = UserFile.cargarUsuario(usuarioActual);

            if (userData != null) {
                usuarioLabel.setText("Usuario: " + userData.getUsuario());
                puntosLabel.setText("Puntos: " + userData.getPuntos());
                fechaLabel.setText("Fecha de creación: " + userData.getFechaCreacion().toString());
                
                // Get game statistics
                int nivelMaximo = UserFile.getNivelMaximo(usuarioActual);
                int partidasJugadas = UserFile.getPartidasJugadas(usuarioActual);
                
                nivelMaximoLabel.setText("Nivel máximo: " + nivelMaximo);
                partidasJugadasLabel.setText("Partidas jugadas: " + partidasJugadas);
                tiempoJugadoLabel.setText("Tiempo Jugado: No disponible");

                actualizarAvatar();
            } else {
                usuarioLabel.setText("Usuario: " + usuarioActual);
                puntosLabel.setText("Puntos: No disponible");
                fechaLabel.setText("Fecha de creación: No disponible");
                nivelMaximoLabel.setText("Nivel máximo: No disponible");
                partidasJugadasLabel.setText("Partidas Jugadas: No disponible");
                tiempoJugadoLabel.setText("Tiempo Jugado: No disponible");
            }
        }
    }


    private void actualizarAvatar() {
        File avatarFile = new File(usuarioActual + "/avatar.png");

        if (avatarFile.exists()) {
            try {
                ImageIcon Avatar = new ImageIcon(avatarFile.getAbsolutePath());
                Image img = Avatar.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                avatarLabel.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.err.println("Error cargando avatar personalizado: " + ex.getMessage());
                cargarAvatarPredeterminado();
            }
        } else {
            cargarAvatarPredeterminado();
        }
    }

    private void cargarAvatarPredeterminado() {
        try {
            ImageIcon Avatar = new ImageIcon("src/Images/avatardef.png");
            Image img = Avatar.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            avatarLabel.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.err.println("Error cargando imagen predeterminada: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cambiarAvatarBtn) {
            new NewAvatar(usuarioActual);
            this.dispose();
        } else if (e.getSource() == cambiarContraBtn) {
            UserLogic userLogic = new UserLogic(usuarioActual);
            userLogic.cambiarContrasena();
        } else if (e.getSource() == eliminarCuentaBtn) {
            UserLogic userLogic = new UserLogic(usuarioActual);
            boolean deleted = userLogic.eliminarCuenta();
            if (deleted) {
                new MainMenu();
                this.dispose();
            }
        } else if (e.getSource() == regresarBtn) {
            new Menu(usuarioActual);
            this.dispose();
        }
    }
}
