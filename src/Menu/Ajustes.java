package Menu;

import Audio.Musica;
import Audio.Sonidos;
import SubMenuOption.*;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Ajustes extends JFrame implements ActionListener, ChangeListener {
    
    private JButton volverBtn, idiomaBtn, controlesBtn, ncontrolesBtn;
    private JSlider volumenSlider;
    private JLabel fondo, volumenLabel;
    private String usuarioActual;
    private ResourceBundle messages;
    private Locale currentLocale;
    
    public Ajustes(String usuario) {
        this.usuarioActual = usuario;
        
        this.currentLocale = LanguageManager.getCurrentLocale();
        this.messages = LanguageManager.getMessages();
        
        initComponents();
    }
    
    private void initComponents() {
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
        this.setTitle(messages.getString("title.settings"));
        this.setLayout(null);
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        idiomaBtn = new JButton(messages.getString("button.language"));
        idiomaBtn.setBounds(325, 120, 150, 30);
        idiomaBtn.addActionListener(this);
        this.add(idiomaBtn);
        
        volumenLabel = new JLabel(messages.getString("label.volume") + " " +(Musica.getInstance().volume * 100));
        volumenLabel.setBounds(325, 170, 150, 30);
        volumenLabel.setForeground(java.awt.Color.WHITE);
        this.add(volumenLabel);
        
        volumenSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (Musica.getInstance().volume * 100));
        System.out.println((int) (Musica.getInstance().volume * 100 ) );
        volumenSlider.setBounds(325, 200, 150, 50);
        volumenSlider.setMajorTickSpacing(25);
        volumenSlider.setMinorTickSpacing(5);
        volumenSlider.setPaintTicks(true);
        volumenSlider.setPaintLabels(true);
        volumenSlider.setOpaque(false);
        volumenSlider.addChangeListener(this);
        this.add(volumenSlider);
        
        controlesBtn = new JButton(messages.getString("button.controls"));
        controlesBtn.setBounds(325, 270, 150, 30);
        controlesBtn.addActionListener(this);
        this.add(controlesBtn);
        
        ncontrolesBtn = new JButton(messages.getString("button.ncontrols"));
        ncontrolesBtn.setBounds(325, 310, 150, 30);
        ncontrolesBtn.addActionListener(this);
        this.add(ncontrolesBtn);
        
        volverBtn = new JButton(messages.getString("button.back"));
        volverBtn.setBounds(325, 350, 150, 30);
        volverBtn.addActionListener(this);
        this.add(volverBtn);
        
        this.add(fondo);
        this.setVisible(true);
    }
    
    private void changeLanguage(String language) {
        LanguageManager.setLanguageForUser(language, usuarioActual);
        
        this.currentLocale = LanguageManager.getCurrentLocale();
        this.messages = LanguageManager.getMessages();
        
        updateUITexts();
    }
    
    private void updateUITexts() {
        this.setTitle(messages.getString("title.settings"));
        idiomaBtn.setText(messages.getString("button.language"));
        volumenLabel.setText(messages.getString("label.volume") + " " + volumenSlider.getValue() + "%");
        controlesBtn.setText(messages.getString("button.controls"));
        volverBtn.setText(messages.getString("button.back"));
        ncontrolesBtn.setText(messages.getString("button.ncontrols"));
    }
    
    private String getKeyName(char key) {
    return String.valueOf(key).toUpperCase();
}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Sonidos.getInstance().play(3);
        if (e.getSource() == idiomaBtn) {
            String[] opciones = {"Español", "English", "Italiano"};
            String seleccion = (String) JOptionPane.showInputDialog(
                    this,
                    messages.getString("dialog.selectLanguage"),
                    messages.getString("dialog.changeLanguage"),
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);
            
            if (seleccion != null) {
                changeLanguage(seleccion);
                
                JOptionPane.showMessageDialog(this, 
                    messages.getString("dialog.languageChanged") + " " + seleccion);
            }
        } else if (e.getSource() == controlesBtn) {
            try {
                char[] controls = Juego.ControlManager.loadControls(usuarioActual);
                
                JOptionPane.showMessageDialog(this,
                        messages.getString("controls.title") + "\n\n" +
                        messages.getString("controls.moveUp") + ": " + getKeyName(controls[0]) + "\n" +              
                        messages.getString("controls.moveDown") + ": " + getKeyName(controls[1]) + "\n" +
                        messages.getString("controls.moveLeft") + ": " + getKeyName(controls[2]) + "\n" +
                        messages.getString("controls.moveRight") + ": " + getKeyName(controls[3]) + "\n" +
                        messages.getString("controls.reset") + ": " + getKeyName(controls[4]),
                        "", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        messages.getString("controls.title") + "\n\n" +
                        messages.getString("controls.moveUp") + "\n" +              
                        messages.getString("controls.moveDown") + "\n" +
                        messages.getString("controls.moveLeft") + "\n" +
                        messages.getString("controls.moveRight") + "\n" +
                        messages.getString("controls.reset"),
                        "", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == volverBtn) {
            new Menu(usuarioActual, currentLocale);
            this.dispose();
        } else if (e.getSource() == ncontrolesBtn) {
            try {
                ControlSettings controlSettings = new ControlSettings(usuarioActual);
                controlSettings.setVisible(true);
                this.setVisible(false);
                this.dispose();
            } catch (Exception ex) {
                System.err.println("Error opening control settings: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error opening control settings: " + ex.getMessage(),
                        messages.getString("dialog.error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == volumenSlider) {
            float valor = volumenSlider.getValue();
            volumenLabel.setText(messages.getString("label.volume") + " " + valor + "%");
            Musica.getInstance().setVolume(valor / 100);
            Musica.volume = valor/100;
            System.out.println("new value of vol: " + valor / 100);
        }
    }
}