package MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;
import java.io.*;


/**
 *
 * @author LENOVO
 */

public class MiPerfil extends JFrame implements ActionListener {
   private JButton regresarBtn, cambiarContraBtn, eliminarCuentaBtn, cambiarAvatarBtn;
   private String usuarioActual;
   private JLabel usuarioLabel, puntosLabel, fechaLabel, avatarLabel, tiempoJugadoLabel, partidasJugadasLabel;
   private User userData;
   private JPanel avatarPanel;

    
    public MiPerfil(String usuario) {
        this.usuarioActual = usuario;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Mi Perfil");
        this.setLayout(null);
        this.setSize(500, 450);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(177, 37, 7));
        
        userData = UserFile.cargarUsuario(usuarioActual);
        
        avatarPanel = new JPanel();
        avatarPanel.setBounds(300, 20, 150, 150);
        avatarPanel.setBackground(new Color(177, 37, 7));
        this.add(avatarPanel);
        
        ImageIcon Avatar = new ImageIcon("src/Images/avatardef.png");
        Image img = Avatar.getImage();
        Image imgEscalada = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon avatarEscalado = new ImageIcon(imgEscalada);

        avatarLabel = new JLabel(avatarEscalado);
        avatarLabel.setPreferredSize(new Dimension(120, 120));
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        avatarPanel.add(avatarLabel);
        
        cambiarAvatarBtn = new JButton("Cambiar Avatar");
        cambiarAvatarBtn.setBounds(300, 180, 150, 30);
        cambiarAvatarBtn.addActionListener(this);
        this.add(cambiarAvatarBtn);
        
        cambiarContraBtn = new JButton("Cambiar Contraseña");
        cambiarContraBtn.setBounds(50, 250, 200, 30);
        cambiarContraBtn.addActionListener(this);
        this.add(cambiarContraBtn);
        
        eliminarCuentaBtn = new JButton("Eliminar Cuenta");
        eliminarCuentaBtn.setBounds(50, 290, 200, 30);
        eliminarCuentaBtn.addActionListener(this);
        this.add(eliminarCuentaBtn);
        
        regresarBtn = new JButton("Regresar");
        regresarBtn.setBounds(50, 330, 200, 30);
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);
        
        usuarioLabel = new JLabel();
        usuarioLabel.setBounds(50, 20, 200, 30);
        usuarioLabel.setForeground(Color.white);
        this.add(usuarioLabel);
        
        puntosLabel = new JLabel();
        puntosLabel.setBounds(50, 60, 200, 30);
        puntosLabel.setForeground(Color.white);
        this.add(puntosLabel);
        
        fechaLabel = new JLabel();
        fechaLabel.setBounds(50, 100, 200, 30);
        fechaLabel.setForeground(Color.white);
        this.add(fechaLabel);
        
        tiempoJugadoLabel = new JLabel();
        tiempoJugadoLabel.setBounds(50, 140, 200, 30);
        tiempoJugadoLabel.setForeground(Color.white);
        this.add( tiempoJugadoLabel);
        
        partidasJugadasLabel = new JLabel();
        partidasJugadasLabel.setBounds(50, 180, 200, 30);
        partidasJugadasLabel.setForeground(Color.white);
        this.add(partidasJugadasLabel);
        
        actualizarDatos();
        this.setVisible(true);
    }

    
 private void actualizarDatos() {
    if (usuarioActual != null) {
        userData = UserFile.cargarUsuario(usuarioActual);
        
        if (userData != null) {
            usuarioLabel.setText("Usuario: " + userData.getUsuario());
            puntosLabel.setText("Puntos: " + userData.getPuntos());
            fechaLabel.setText("Fecha de creación: " + userData.getFechaCreacion().toString());
            tiempoJugadoLabel.setText("Tiempo Jugado: ");
            partidasJugadasLabel.setText("Partidas Jugadas: ");
            
            String avatarSeleccionado = cargarAvatarSeleccionado(usuarioActual);
            String avatarPath = "src/Images/" + avatarSeleccionado + ".png";
            
            try {
                ImageIcon Avatar = new ImageIcon(avatarPath);
                Image img = Avatar.getImage();
                Image imgEscalada = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                ImageIcon avatarEscalado = new ImageIcon(imgEscalada);
                avatarLabel.setIcon(avatarEscalado);
            } catch (Exception ex) {
                System.err.println("Error cargando imagen: " + avatarPath);
                try {
                    ImageIcon Avatar = new ImageIcon("src/Images/avatardef.png");
                    Image img = Avatar.getImage();
                    Image imgEscalada = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                    ImageIcon avatarEscalado = new ImageIcon(imgEscalada);
                    avatarLabel.setIcon(avatarEscalado);
                } catch (Exception e) {
                    System.err.println("Error cargando imagen predeterminada");
                }
            }
        } else {
            usuarioLabel.setText("Usuario: " + usuarioActual);
            puntosLabel.setText("Puntos: No disponible");
            fechaLabel.setText("Fecha de creación: No disponible");
            tiempoJugadoLabel.setText("Tiempo Jugado: No disponible");
            partidasJugadasLabel.setText("Partidas Jugadas: No disponible ");
        }
    }
}
 private String cargarAvatarSeleccionado(String usuario) {
    File archivo = new File(usuario + "/avatar.txt");
    if (!archivo.exists()) {
        return "avatardef"; // Default avatar
    }
    
    try (FileInputStream fis = new FileInputStream(archivo)) {
        byte[] data = new byte[(int) archivo.length()];
        fis.read(data);
        return new String(data).trim();
    } catch (IOException ex) {
        System.err.println("Error al cargar avatar: " + ex.getMessage());
        return "avatardef";
    }
}
    
    @Override
    public void actionPerformed(ActionEvent e) {
           if (e.getSource() == cambiarAvatarBtn) {
        new NewAvatar(usuarioActual);
        this.dispose();          
        } else if (e.getSource() == cambiarContraBtn) {
          
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