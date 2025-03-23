package Menu;

import Audio.Sonidos;
import InitGUI.*;
import SubMenuOption.*;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.*;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Menu extends JFrame implements ActionListener {
    
    private JButton jugar, salirBtn, miPerfilBtn, multiplayerBtn, ajustesBtn;
    private String usuarioActual;
    private JLabel fondo;
    private ResourceBundle messages;
    
    public Menu(String usuario) {
        this.usuarioActual = usuario;
        this.messages = LanguageManager.getMessages();
        
        initComponents();
    }
    
    public Menu(String usuario, Locale locale) {
        this.usuarioActual = usuario;
        
        if (locale.getLanguage().equals("en")) {
            LanguageManager.setLanguage("English");
        } else if(locale.getLanguage().equals("es")) {
            LanguageManager.setLanguage("Español");
        } else{
            LanguageManager.setLanguage("Italiano");
                }
        
        this.messages = LanguageManager.getMessages();
        initComponents();
    }
    
    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(messages.getString("title.main"));
        this.setLayout(null);
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
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
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        jugar = new JButton(messages.getString("button.play"));
        jugar.setBounds(325, 120, 150, 30);
        jugar.addActionListener(this);
        this.add(jugar);
        
        miPerfilBtn = new JButton(messages.getString("button.profile"));
        miPerfilBtn.setBounds(325, 170, 150, 30);
        miPerfilBtn.addActionListener(this);
        this.add(miPerfilBtn);
        
        multiplayerBtn = new JButton(messages.getString("button.ranking"));
        multiplayerBtn.setBounds(325, 220, 150, 30);
        multiplayerBtn.addActionListener(this);
        this.add(multiplayerBtn);
        
        ajustesBtn = new JButton(messages.getString("button.settings"));
        ajustesBtn.setBounds(325, 270, 150, 30);
        ajustesBtn.addActionListener(this);
        this.add(ajustesBtn);
        
        salirBtn = new JButton(messages.getString("button.logout"));
        salirBtn.setBounds(325, 320, 150, 30);
        salirBtn.addActionListener(this);
        this.add(salirBtn);
        
        this.add(fondo);
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Sonidos.getInstance().play(3);
        if (e.getSource() == jugar) {
            new SeleccionarNiveles(usuarioActual);
            this.dispose();
        } else if (e.getSource() == miPerfilBtn) {
            new MiPerfil(usuarioActual);
            this.dispose();
        } else if (e.getSource() == multiplayerBtn) {
            new Ranking(usuarioActual);
            this.dispose();
        } else if (e.getSource() == ajustesBtn) {
            new Ajustes(usuarioActual);
            this.dispose();
        } else if (e.getSource() == salirBtn) {
            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                messages.getString("dialog.confirmLogout"), 
                messages.getString("dialog.confirmation"), 
                JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                new MainMenu();
                this.dispose();
            }
        }
    }
}