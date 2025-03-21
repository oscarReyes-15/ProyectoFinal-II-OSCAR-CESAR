package MenuGUI;

import Sounds.Sonidos;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Menu extends JFrame implements ActionListener {
    
    private JButton jugar, salirBtn, miPerfilBtn, multiplayerBtn, ajustesBtn;
    private String usuarioActual;
    private JLabel fondo;
    private ResourceBundle messages;
    Sonidos s;
    
    public Menu(String usuario, Sonidos s) {
        this.usuarioActual = usuario;
        
        
        // Get messages from LanguageManager
        this.messages = LanguageManager.getMessages();
        this.s = s;
        initComponents();
    }
    
    // Constructor that accepts a Locale
    public Menu(String usuario, Locale locale, Sonidos s) {
        this.usuarioActual = usuario;
        
        // Update LanguageManager with the provided locale
        if (locale.getLanguage().equals("en")) {
            LanguageManager.setLanguage("English");
        } else {
            LanguageManager.setLanguage("Español");
        }
        
        // Get updated messages
        this.messages = LanguageManager.getMessages();
        
        this.s = s;
        initComponents();
    }
    
    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(messages.getString("title.main"));
        this.setLayout(null);
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        
        
        // Load background image
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        // Create buttons with localized text
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
        
        this.add(fondo); // Add the background last so it's in the lowest layer
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugar) {
            s.Play(3);
            new SeleccionarNiveles(usuarioActual, s);
            this.dispose();
        } else if (e.getSource() == miPerfilBtn) {
            s.Play(3);
            new MiPerfil(usuarioActual, s);
            this.dispose();
        } else if (e.getSource() == multiplayerBtn) {
            s.Play(3);
            JOptionPane.showMessageDialog(this, messages.getString("dialog.multiplayer"));
        } else if (e.getSource() == ajustesBtn) {
            s.Play(3);
            new Ajustes(usuarioActual, s);
            this.dispose();
        } else if (e.getSource() == salirBtn) {
            s.Play(3);
            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                messages.getString("dialog.confirmLogout"), 
                messages.getString("dialog.confirmation"), 
                JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                new MainMenu(s);
                this.dispose();
            }
        }
    }
}