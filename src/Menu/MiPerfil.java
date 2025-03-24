package Menu;

import Audio.Sonidos;
import InitGUI.*;
import SubMenuOption.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;
import java.io.*;
import java.util.ResourceBundle;

public class MiPerfil extends JFrame implements ActionListener {
    private JButton regresarBtn, cambiarContraBtn, eliminarCuentaBtn, cambiarAvatarBtn, ultimasPartidasBtn;
    private String usuarioActual;
    private JLabel usuarioLabel, nombreLabel, puntosLabel, fechaLabel, avatarLabel, tiempoJugadoLabel, partidasJugadasLabel, nivelMaximoLabel, fondo;
    private User userData;
    private JPanel avatarPanel, infoPanel;
    private ResourceBundle messages;

    public MiPerfil(String usuario) {
        this.usuarioActual = usuario;
        this.messages = LanguageManager.getMessages();
        
        try {
            ImageIcon taza = new ImageIcon(getClass().getResource("/imagescan/logo.jpg"));
            if (taza.getImageLoadStatus() == MediaTracker.COMPLETE) {
                this.setIconImage(taza.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            } else {
                System.err.println("Unable to load the image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(messages.getString("title.profile"));
        this.setLayout(null);
        this.setSize(800, 450); 
        this.setResizable(false);  
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

        // Nuevo botón para ver últimas partidas
        ultimasPartidasBtn = new JButton(messages.getString("button.lastGames"));
        if (!messages.containsKey("button.lastGames")) {
            ultimasPartidasBtn.setText("Ver últimas partidas");
        }
        ultimasPartidasBtn.setBounds(550, 250, 150, 30);
        ultimasPartidasBtn.addActionListener(this);
        this.add(ultimasPartidasBtn);

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
        
        // Nueva etiqueta para mostrar el nombre del usuario
        nombreLabel = new JLabel();
        nombreLabel.setBounds(20, 60, 250, 30);
        nombreLabel.setForeground(Color.white);
        nombreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(nombreLabel);

        puntosLabel = new JLabel();
        puntosLabel.setBounds(20, 100, 250, 30);
        puntosLabel.setForeground(Color.white);
        puntosLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(puntosLabel);

        fechaLabel = new JLabel();
        fechaLabel.setBounds(20, 140, 250, 30);
        fechaLabel.setForeground(Color.white);
        fechaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(fechaLabel);

        nivelMaximoLabel = new JLabel();
        nivelMaximoLabel.setBounds(20, 180, 250, 30);
        nivelMaximoLabel.setForeground(Color.white);
        nivelMaximoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(nivelMaximoLabel);

        partidasJugadasLabel = new JLabel();
        partidasJugadasLabel.setBounds(20, 220, 250, 30);
        partidasJugadasLabel.setForeground(Color.white);
        partidasJugadasLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(partidasJugadasLabel);

        tiempoJugadoLabel = new JLabel();
        tiempoJugadoLabel.setBounds(270, 220, 250, 30); // Positioned to the right of partidasJugadasLabel
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
                
                String nombre = userData.getNombre();
                if (nombre != null && !nombre.isEmpty()) {
                    nombreLabel.setText(messages.getString("profile.name") + ": " + nombre);
                } else {
                    nombreLabel.setText(messages.getString("profile.name") + ": " + messages.getString("profile.notAvailable"));
                }
                
                puntosLabel.setText(messages.getString("profile.points") + ": " + userData.getPuntos());
                fechaLabel.setText(messages.getString("profile.creationDate") + ": " + userData.getFechaCreacion().toString());
                
                int nivelMaximo = UserFile.getNivelMaximo(usuarioActual);
                int partidasJugadas = UserFile.getPartidasJugadas(usuarioActual);
                
                String tiempoFormateado = UserFile.getTiempoFormateado(usuarioActual);
                
                nivelMaximoLabel.setText(messages.getString("profile.maxLevel") + ": " + nivelMaximo);
                partidasJugadasLabel.setText(messages.getString("profile.gamesPlayed") + ": " + partidasJugadas);
                tiempoJugadoLabel.setText(messages.getString("profile.timePlayed") + ": " + tiempoFormateado);

                actualizarAvatar();
            } else {
                usuarioLabel.setText(messages.getString("profile.user") + ": " + usuarioActual);
                nombreLabel.setText(messages.getString("profile.name") + ": " + messages.getString("profile.notAvailable"));
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
                cargarAvatarPredeterminado();
            }
        } else {
            cargarAvatarPredeterminado();
        }
    }

    private void cargarAvatarPredeterminado() {
        try {
            ImageIcon Avatar = new ImageIcon("src/AvatarImages/avatardef.png");
            Image img = Avatar.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            avatarLabel.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
        }
    }
    
    private void mostrarUltimasPartidas() {
        // Use the GameHistory class to retrieve game history instead of local method
        Object[][] partidas = GameHistory.obtenerHistorial(usuarioActual);
        
        if (partidas.length == 0) {
            JOptionPane.showMessageDialog(this, 
                "No hay partidas registradas", 
                "Últimas partidas", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder mensaje = new StringBuilder("Últimas partidas\n");
        mensaje.append("Nivel\tDuración\tResultado\n");
        
        for (Object[] partida : partidas) {
            int nivel = (Integer) partida[0];
            long tiempo = (Long) partida[1];
            boolean completado = (Boolean) partida[2];
            
            String tiempoFormateado = GameHistory.formatearTiempo(tiempo);
            String resultado = completado ? "Completado" : "No completado";
            
            mensaje.append(nivel)
                   .append("\t")
                   .append(tiempoFormateado)
                   .append("\t")
                   .append(resultado)
                   .append("\n");
        }
        
        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false); // Hacer que el texto no sea editable
        JScrollPane scrollPane = new JScrollPane(textArea); 
        
        JOptionPane.showMessageDialog(null, scrollPane, "Últimas partidas", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sonidos.getInstance().play(3);
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
        } else if (e.getSource() == ultimasPartidasBtn) {
            mostrarUltimasPartidas();
        }
    }
}