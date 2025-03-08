package MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
    private String selectedAvatar;
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
                Image img = avatarIcon.getImage();
                Image imgEscalada = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon avatarEscalado = new ImageIcon(imgEscalada);
                
                avatarButtons[i] = new JButton(avatarEscalado);
                avatarButtons[i].setPreferredSize(new Dimension(150, 150));
                avatarButtons[i].setActionCommand("avatar" + (i + 1)); 
                avatarButtons[i].addActionListener(this);
                avatarPanel.add(avatarButtons[i]);
            } catch (Exception ex) {
                System.err.println("Error cargando imagen: " + avatarPaths[i]);
                avatarButtons[i] = new JButton("Avatar " + (i + 1));
                avatarButtons[i].setActionCommand("avatar" + (i + 1));
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
    
    private String getCurrentUser() {
        File[] files = new File(".").listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && !file.getName().startsWith(".")) {
                    return file.getName();
                }
            }
        }
        return null;
    }
    
    private boolean guardarAvatarSeleccionado(String usuario, String avatarSeleccionado) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        File archivo = new File(usuario + "/avatar.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println(avatarSeleccionado);
            return true;
        } catch (IOException ex) {
            System.err.println("Error al guardar avatar: " + ex.getMessage());
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
                JOptionPane.showMessageDialog(this, 
                    "Error al regresar al perfil", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            for (JButton button : avatarButtons) {
                if (e.getSource() == button) {
                    selectedAvatar = button.getActionCommand();
                    
                    if (guardarAvatarSeleccionado(usuarioActual, selectedAvatar)) {
                        JOptionPane.showMessageDialog(this, 
                            "Avatar seleccionado correctamente", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            
                        try {
                            new MiPerfil(usuarioActual);
                            this.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, 
                                "Error al regresar al perfil", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Error al guardar el avatar seleccionado", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
        }
    }
}