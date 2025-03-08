package MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;

/**
 *
 * @author LENOVO
 */
public class MiPerfil extends JFrame implements ActionListener {
   private JButton regresarBtn, cambiarContraBtn, eliminarCuentaBtn, cambiarAvatarBtn;
   private String usuarioActual;
   private JLabel usuarioLabel, puntosLabel, fechaLabel, avatarLabel;
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
        
        ImageIcon Avatar = new ImageIcon("src/Images/avatar1.png");
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
            } else {
                usuarioLabel.setText("Usuario: " + usuarioActual);
                puntosLabel.setText("Puntos: No disponible");
                fechaLabel.setText("Fecha de creación: No disponible");
            }
        }
    }
    
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cambiarAvatarBtn) {
            new NewPfP();
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