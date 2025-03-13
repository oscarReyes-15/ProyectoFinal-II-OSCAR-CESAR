package MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import User.*;

public class NewAvatar extends JFrame implements ActionListener {
    private JButton regresarBtn;
    private JButton[] avatarButtons;
    private String[] avatarPaths = {
        "src/Images/avatar1.png",
        "src/Images/avatar2.png",
        "src/Images/avatar3.png",
        "src/Images/avatar4.png"
    };
    private String usuarioActual;
    
    public NewAvatar(String usuario) {
        this.usuarioActual = usuario;
        
        this.setTitle("Seleccionar Avatar");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(177, 37, 7));
        
        JPanel avatarPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        avatarPanel.setBackground(new Color(177, 37, 7));
        avatarPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        avatarButtons = new JButton[avatarPaths.length];
        
        for (int i = 0; i < avatarPaths.length; i++) {
            try {
                ImageIcon avatarIcon = new ImageIcon(avatarPaths[i]);
                Image img = avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon avatarEscalado = new ImageIcon(img);
                
                avatarButtons[i] = new JButton(avatarEscalado);
                avatarButtons[i].setPreferredSize(new Dimension(150, 150));
                avatarButtons[i].setActionCommand(avatarPaths[i]); // Store full path
                avatarButtons[i].addActionListener(this);
                avatarPanel.add(avatarButtons[i]);
            } catch (Exception ex) {
                System.err.println("Error cargando imagen: " + avatarPaths[i]);
                avatarButtons[i] = new JButton("Avatar " + (i + 1));
                avatarButtons[i].setActionCommand(avatarPaths[i]);
                avatarButtons[i].addActionListener(this);
                avatarPanel.add(avatarButtons[i]);
            }
        }
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(177, 37, 7));
        
        regresarBtn = new JButton("Regresar sin cambiar");
        regresarBtn.setPreferredSize(new Dimension(200, 40));
        regresarBtn.addActionListener(this);
        bottomPanel.add(regresarBtn);
        
        this.add(avatarPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        
        this.setVisible(true);
    }
    
    private boolean copiarAvatarSeleccionado(String usuario, String avatarSeleccionado) {
        try {
            File directorio = new File(usuario);
            if (!directorio.exists() && !directorio.mkdirs()) {
                System.err.println("No se pudo crear la carpeta del usuario.");
                return false;
            }

            File avatarOrigen = new File(avatarSeleccionado);
            if (!avatarOrigen.exists()) {
                System.err.println("El archivo de avatar no existe: " + avatarOrigen.getAbsolutePath());
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
        if (e.getSource() == regresarBtn) {
            try {
                new MiPerfil(usuarioActual);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al regresar al perfil", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            for (JButton button : avatarButtons) {
                if (e.getSource() == button) {
                    String selectedAvatar = button.getActionCommand();
                    
                    if (copiarAvatarSeleccionado(usuarioActual, selectedAvatar)) {
                        JOptionPane.showMessageDialog(this, "Avatar seleccionado correctamente", "Ã‰xito", JOptionPane.INFORMATION_MESSAGE);
                        
                        try {
                            new MiPerfil(usuarioActual);
                            this.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error al regresar al perfil", "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al copiar el avatar seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
        }
    }
}