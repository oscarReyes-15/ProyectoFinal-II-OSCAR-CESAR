package Menu;

import Audio.Sonidos;
import InitGUI.*;
import SubMenuOption.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MiPerfil extends JFrame implements ActionListener {
    private JButton regresarBtn, cambiarContraBtn, eliminarCuentaBtn, cambiarAvatarBtn, ultimasPartidasBtn;
    private String usuarioActual;
    private JLabel usuarioLabel, nombreLabel, puntosLabel, fechaLabel, avatarLabel, tiempoJugadoLabel, 
                  partidasJugadasLabel, nivelMaximoLabel, ultimoLoginLabel, fondo, espacioLabel;
    private User userData;
    private JPanel avatarPanel, infoPanel;
    private ResourceBundle messages;
    private DateTimeFormatter formatter;

    public MiPerfil(String usuario) {
        this.usuarioActual = usuario;
        this.messages = LanguageManager.getMessages();
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
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
        infoPanel.setBounds(30, 30, 470, 285); // Aumentado la altura para incluir nueva información
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

        ultimasPartidasBtn = new JButton(messages.getString("button.lastGames"));
        ultimasPartidasBtn.setBounds(550, 250, 150, 30);
        ultimasPartidasBtn.addActionListener(this);
        this.add(ultimasPartidasBtn);

        cambiarContraBtn = new JButton(messages.getString("button.changePassword"));
        cambiarContraBtn.setBounds(50, 325, 200, 30);
        cambiarContraBtn.addActionListener(this);
        this.add(cambiarContraBtn);

        eliminarCuentaBtn = new JButton(messages.getString("button.deleteAccount"));
        eliminarCuentaBtn.setBounds(50, 365, 200, 30);
        eliminarCuentaBtn.addActionListener(this);
        this.add(eliminarCuentaBtn);

        regresarBtn = new JButton(messages.getString("button.back"));
        regresarBtn.setBounds(270, 325, 200, 30); 
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);

        usuarioLabel = new JLabel();
        usuarioLabel.setBounds(20, 20, 250, 30);
        usuarioLabel.setForeground(Color.white);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(usuarioLabel);
        
        nombreLabel = new JLabel();
        nombreLabel.setBounds(20, 50, 250, 30);
        nombreLabel.setForeground(Color.white);
        nombreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(nombreLabel);

        puntosLabel = new JLabel();
        puntosLabel.setBounds(20, 80, 250, 30);
        puntosLabel.setForeground(Color.white);
        puntosLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(puntosLabel);

        fechaLabel = new JLabel();
        fechaLabel.setBounds(20, 110, 440, 30);
        fechaLabel.setForeground(Color.white);
        fechaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(fechaLabel);
        
        ultimoLoginLabel = new JLabel();
        ultimoLoginLabel.setBounds(20, 140, 440, 30);
        ultimoLoginLabel.setForeground(Color.white);
        ultimoLoginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(ultimoLoginLabel);

        nivelMaximoLabel = new JLabel();
        nivelMaximoLabel.setBounds(20, 170, 250, 30);
        nivelMaximoLabel.setForeground(Color.white);
        nivelMaximoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(nivelMaximoLabel);

        partidasJugadasLabel = new JLabel();
        partidasJugadasLabel.setBounds(20, 200, 250, 30);
        partidasJugadasLabel.setForeground(Color.white);
        partidasJugadasLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(partidasJugadasLabel);

        tiempoJugadoLabel = new JLabel();
        tiempoJugadoLabel.setBounds(20, 230, 250, 30); 
        tiempoJugadoLabel.setForeground(Color.white);
        tiempoJugadoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(tiempoJugadoLabel);

        JLabel titleLabel = new JLabel(messages.getString("title.profile"));
        titleLabel.setBounds(135, 5, 200, 30);
        titleLabel.setForeground(new Color(255, 255, 200));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(titleLabel);

        espacioLabel = new JLabel();
        espacioLabel.setBounds(510, 30, 20, 285); 
        this.add(espacioLabel);

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
                
                String fechaCreacionStr = userData.getFechaCreacion().format(formatter);
                fechaLabel.setText(messages.getString("profile.creationDate") + ": " + fechaCreacionStr);
                
                String ultimoLoginStr = userData.getUltimoLogin().format(formatter);
                ultimoLoginLabel.setText(messages.getString("profile.lastLogin") + ": " + ultimoLoginStr);
                
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
                ultimoLoginLabel.setText(messages.getString("profile.lastLogin") + ": " + messages.getString("profile.notAvailable"));
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
        Object[][] partidas = GameHistory.obtenerHistorial(usuarioActual);
        
        if (partidas.length == 0) {
            JOptionPane.showMessageDialog(this, 
                (messages.getString("No hay partidas registradas")), 
                 (messages.getString("dialog.lastgames")), 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder mensaje = new StringBuilder("dialog.lastgames\n");
        mensaje.append(messages.getString(("level\ttime\tresults\n")));
        
        for (Object[] partida : partidas) {
            int nivel = (Integer) partida[0];
            long tiempo = (Long) partida[1];
            boolean completado = (Boolean) partida[2];
            
            String tiempoFormateado = GameHistory.formatearTiempo(tiempo);
            String resultado = completado ? (messages.getString("dialog.completed")): (messages.getString("dialog.notCompleted"));
            
            mensaje.append(nivel)
                   .append("\t")
                   .append(tiempoFormateado)
                   .append("\t")
                   .append(resultado)
                   .append("\n");
        }
        
        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false); 
        JScrollPane scrollPane = new JScrollPane(textArea); 
        
        JOptionPane.showMessageDialog(null, scrollPane, (messages.getString("dialog.lastgames")), JOptionPane.INFORMATION_MESSAGE);
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
