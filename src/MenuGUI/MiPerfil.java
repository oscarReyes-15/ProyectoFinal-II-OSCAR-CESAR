package MenuGUI;

import Sounds.Sonidos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;
import java.io.*;
import java.util.ResourceBundle;

public class MiPerfil extends JFrame implements ActionListener {
    private JButton regresarBtn, cambiarContraBtn, eliminarCuentaBtn, cambiarAvatarBtn;
    private String usuarioActual;
    private JLabel usuarioLabel, puntosLabel, fechaLabel, avatarLabel, tiempoJugadoLabel, partidasJugadasLabel, nivelMaximoLabel, fondo;
    private User userData;
    private JPanel avatarPanel, infoPanel;
    private ResourceBundle messages;
    private Sonidos s;
    
    public MiPerfil(String usuario, Sonidos s) {
        this.usuarioActual = usuario;
        this.messages = LanguageManager.getMessages();
        this. s = s;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(messages.getString("title.profile"));
        this.setLayout(null);
        this.setSize(800, 450); 
        this.setLocationRelativeTo(null);

        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui1.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);

        userData = UserFile.cargarUsuario(usuarioActual);

        infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBounds(30, 30, 470, 250); 
        infoPanel.setBackground(new Color(0, 20, 60, 200)); 
        infoPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2)); 
        this.add(infoPanel);

        avatarPanel = new JPanel();
        avatarPanel.setBounds(550, 50, 150, 150);
        avatarPanel.setBackground(new Color(0, 0, 0, 0));
        this.add(avatarPanel);

        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(120, 120));
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        avatarPanel.add(avatarLabel);

        cambiarAvatarBtn = new JButton(messages.getString("button.changeAvatar"));
        cambiarAvatarBtn.setBounds(550, 210, 150, 30);
        cambiarAvatarBtn.addActionListener(this);
        this.add(cambiarAvatarBtn);

        // Botones ahora ubicados bajo el panel de información
        cambiarContraBtn = new JButton(messages.getString("button.changePassword"));
        cambiarContraBtn.setBounds(50, 290, 200, 30);
        cambiarContraBtn.addActionListener(this);
        this.add(cambiarContraBtn);

        eliminarCuentaBtn = new JButton(messages.getString("button.deleteAccount"));
        eliminarCuentaBtn.setBounds(50, 330, 200, 30);
        eliminarCuentaBtn.addActionListener(this);
        this.add(eliminarCuentaBtn);

        regresarBtn = new JButton(messages.getString("button.back"));
        regresarBtn.setBounds(50, 370, 200, 30);
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);

        // Etiquetas dentro del panel de información
        usuarioLabel = new JLabel();
        usuarioLabel.setBounds(20, 20, 250, 30);
        usuarioLabel.setForeground(Color.white);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(usuarioLabel);

        puntosLabel = new JLabel();
        puntosLabel.setBounds(20, 60, 250, 30);
        puntosLabel.setForeground(Color.white);
        puntosLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(puntosLabel);

        fechaLabel = new JLabel();
        fechaLabel.setBounds(20, 100, 250, 30);
        fechaLabel.setForeground(Color.white);
        fechaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(fechaLabel);

        nivelMaximoLabel = new JLabel();
        nivelMaximoLabel.setBounds(20, 140, 250, 30);
        nivelMaximoLabel.setForeground(Color.white);
        nivelMaximoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(nivelMaximoLabel);

        partidasJugadasLabel = new JLabel();
        partidasJugadasLabel.setBounds(20, 180, 250, 30);
        partidasJugadasLabel.setForeground(Color.white);
        partidasJugadasLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(partidasJugadasLabel);

        tiempoJugadoLabel = new JLabel();
        tiempoJugadoLabel.setBounds(20, 220, 250, 30);
        tiempoJugadoLabel.setForeground(Color.white);
        tiempoJugadoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(tiempoJugadoLabel);

        JLabel titleLabel = new JLabel(messages.getString("title.profile"));
        titleLabel.setBounds(135, 5, 200, 30);
        titleLabel.setForeground(new Color(255, 255, 200));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(titleLabel);

        actualizarDatos();

        this.add(fondo);

        this.setVisible(true);
    }

    private void actualizarDatos() {
        if (usuarioActual != null) {
            userData = UserFile.cargarUsuario(usuarioActual);

            if (userData != null) {
                usuarioLabel.setText(messages.getString("profile.user") + ": " + userData.getUsuario());
                puntosLabel.setText(messages.getString("profile.points") + ": " + userData.getPuntos());
                fechaLabel.setText(messages.getString("profile.creationDate") + ": " + userData.getFechaCreacion().toString());
                
                int nivelMaximo = UserFile.getNivelMaximo(usuarioActual);
                int partidasJugadas = UserFile.getPartidasJugadas(usuarioActual);
                
                nivelMaximoLabel.setText(messages.getString("profile.maxLevel") + ": " + nivelMaximo);
                partidasJugadasLabel.setText(messages.getString("profile.gamesPlayed") + ": " + partidasJugadas);
                tiempoJugadoLabel.setText(messages.getString("profile.timePlayed") + ": " + messages.getString("profile.notAvailable"));

                actualizarAvatar();
            } else {
                usuarioLabel.setText(messages.getString("profile.user") + ": " + usuarioActual);
                puntosLabel.setText(messages.getString("profile.points") + ": " + messages.getString("profile.notAvailable"));
                fechaLabel.setText(messages.getString("profile.creationDate") + ": " + messages.getString("profile.notAvailable"));
                nivelMaximoLabel.setText(messages.getString("profile.maxLevel") + ": " + messages.getString("profile.notAvailable"));
                partidasJugadasLabel.setText(messages.getString("profile.gamesPlayed") + ": " + messages.getString("profile.notAvailable"));
                tiempoJugadoLabel.setText(messages.getString("profile.timePlayed") + ": " + messages.getString("profile.notAvailable"));
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
            s.Play(3);
            new NewAvatar(usuarioActual, s);
            this.dispose();
        } else if (e.getSource() == cambiarContraBtn) {
            s.Play(3);
            UserLogic userLogic = new UserLogic(usuarioActual);
            userLogic.cambiarContrasena();
        } else if (e.getSource() == eliminarCuentaBtn) {
            s.Play(3);
            UserLogic userLogic = new UserLogic(usuarioActual);
            boolean deleted = userLogic.eliminarCuenta();
            if (deleted) {
                new MainMenu(s);
                this.dispose();
            }
        } else if (e.getSource() == regresarBtn) {
            s.Play(3);
            new Menu(usuarioActual, s);
            this.dispose();
        }
    }
}