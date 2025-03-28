package SubMenuOption;

import Audio.Sonidos;
import Menu.MiPerfil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.ResourceBundle;

public class NewAvatar extends JFrame implements ActionListener {
    private JButton regresarBtn;
    private JButton[] avatarButtons;
    private String[] avatarPaths = {
        "src/AvatarImages/avatar1.png",
        "src/AvatarImages/avatar2.png",
        "src/AvatarImages/avatar3.png",
        "src/AvatarImages/avatar4.png"
    };
    private String usuarioActual;
    private JLabel fondo;
    private ResourceBundle messages;
    
    public NewAvatar(String usuario) {
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
        this.setTitle(messages.getString("title.selectAvatar"));
        this.setSize(800, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui1.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        JPanel avatarPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        avatarPanel.setBounds(200, 50, 400, 300);
        avatarPanel.setOpaque(false);
        
        avatarButtons = new JButton[avatarPaths.length];
        
        for (int i = 0; i < avatarPaths.length; i++) {
            try {
                ImageIcon avatarIcon = new ImageIcon(avatarPaths[i]);
                Image img = avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon avatarEscalado = new ImageIcon(img);
                
                avatarButtons[i] = new JButton(avatarEscalado);
                avatarButtons[i].setPreferredSize(new Dimension(150, 150));
                avatarButtons[i].setActionCommand(avatarPaths[i]);
                avatarButtons[i].addActionListener(this);
                avatarPanel.add(avatarButtons[i]);
            } catch (Exception ex) {
                avatarButtons[i] = new JButton("Avatar " + (i + 1));
                avatarButtons[i].setActionCommand(avatarPaths[i]);
                avatarButtons[i].addActionListener(this);
                avatarPanel.add(avatarButtons[i]);
            }
        }
        
        this.add(avatarPanel);
        
        regresarBtn = new JButton(messages.getString("button.returnWithoutChanging"));
        regresarBtn.setBounds(300, 370, 200, 30);
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);
        
        this.add(fondo);
        
        this.setVisible(true);
    }
    
    private boolean copiarAvatarSeleccionado(String usuario, String avatarSeleccionado) {
        try {
            File directorio = new File(usuario);
            if (!directorio.exists() && !directorio.mkdirs()) {
                return false;
            }

            File avatarOrigen = new File(avatarSeleccionado);
            if (!avatarOrigen.exists()) {
                return false;
            }

            File avatarDestino = new File(directorio, "avatar.png");
            Files.copy(avatarOrigen.toPath(), avatarDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Sonidos.getInstance().play(3);
        if (e.getSource() == regresarBtn) {
            try {
                new MiPerfil(usuarioActual);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    messages.getString("dialog.profileError"), 
                    messages.getString("dialog.error"), 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            for (JButton button : avatarButtons) {
                if (e.getSource() == button) {
                    String selectedAvatar = button.getActionCommand();
                    
                    if (copiarAvatarSeleccionado(usuarioActual, selectedAvatar)) {
                        JOptionPane.showMessageDialog(this, 
                            messages.getString("dialog.avatarSelected"), 
                            messages.getString("dialog.success"), 
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        try {
                            new MiPerfil(usuarioActual);
                            this.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, 
                                messages.getString("dialog.profileError"), 
                                messages.getString("dialog.error"), 
                                JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            messages.getString("dialog.avatarError"), 
                            messages.getString("dialog.error"), 
                            JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
        }
    }
}