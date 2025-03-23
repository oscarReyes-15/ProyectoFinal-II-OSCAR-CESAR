package MenuGUI;

import Sounds.Musica;
import Sounds.Sonidos;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Ajustes extends JFrame implements ActionListener, ChangeListener {
    
    private JButton volverBtn, idiomaBtn, controlesBtn;
    private JSlider volumenSlider;
    private JLabel fondo, volumenLabel;
    private String usuarioActual;
    private ResourceBundle messages;
    private Locale currentLocale;
    private Sonidos s;
    
    public Ajustes(String usuario, Sonidos s) {
        this.usuarioActual = usuario;
        this.s = s;
        
        // Get the current locale and messages from LanguageManager
        this.currentLocale = LanguageManager.getCurrentLocale();
        this.messages = LanguageManager.getMessages();
        
        initComponents();
    }
    
    private void initComponents() {
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
        
        volumenLabel = new JLabel(messages.getString("label.volume"));
        volumenLabel.setBounds(325, 170, 150, 30);
        volumenLabel.setForeground(java.awt.Color.WHITE);
        this.add(volumenLabel);
        
        volumenSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (Musica.volume * 100));
        System.out.println(Musica.volume * 100);
        volumenSlider.setBounds(325, 200, 150, 50);
        volumenSlider.setMajorTickSpacing(25);
        volumenSlider.setMinorTickSpacing(5);
        volumenSlider.setPaintTicks(true);
        volumenSlider.setPaintLabels(true);
        volumenSlider.setOpaque(false);
        volumenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float value = volumenSlider.getValue();
                System.out.println(value + " : " + value/100);
                Musica.getInstance().setVolume(value/100);
            }
        });
        this.add(volumenSlider);
        
        controlesBtn = new JButton(messages.getString("button.controls"));
        controlesBtn.setBounds(325, 270, 150, 30);
        controlesBtn.addActionListener(this);
        this.add(controlesBtn);
        
        volverBtn = new JButton(messages.getString("button.back"));
        volverBtn.setBounds(325, 350, 150, 30);
        volverBtn.addActionListener(this);
        this.add(volverBtn);
        
        this.add(fondo);
        this.setVisible(true);
    }
    
    private void changeLanguage(String language) {
        // Update the language in LanguageManager
        LanguageManager.setLanguage(language);
        
        // Get the updated locale and messages
        this.currentLocale = LanguageManager.getCurrentLocale();
        this.messages = LanguageManager.getMessages();
        
        // Update all UI components
        updateUITexts();
    }
    
    private void updateUITexts() {
        this.setTitle(messages.getString("title.settings"));
        idiomaBtn.setText(messages.getString("button.language"));
        volumenLabel.setText(messages.getString("label.volume") + " " + volumenSlider.getValue() + "%");
        controlesBtn.setText(messages.getString("button.controls"));
        volverBtn.setText(messages.getString("button.back"));
        
        // You might need to update other components if they contain text
    }
    
    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == idiomaBtn) {
        String[] opciones = {"Español", "English"};
        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                messages.getString("dialog.selectLanguage"),
                messages.getString("dialog.changeLanguage"),
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        
        if (seleccion != null) {
            // Change the language
            changeLanguage(seleccion);
            
            // Show confirmation
            JOptionPane.showMessageDialog(this, 
                messages.getString("dialog.languageChanged") + " " + seleccion);
        }
    } else if (e.getSource() == controlesBtn) {
        JOptionPane.showMessageDialog(this,
                messages.getString("controls.title") + "\n\n" +
                messages.getString("controls.moveUp") + "\n" +              
                messages.getString("controls.moveDown") + "\n" +
                messages.getString("controls.moveLeft") + "\n" +
                messages.getString("controls.moveRight") + "\n" +
                messages.getString("controls.reset"),
                "", JOptionPane.INFORMATION_MESSAGE);
    } else if (e.getSource() == volverBtn) {
        new Menu(usuarioActual, currentLocale, s);
        this.dispose();
    }
}    
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == volumenSlider) {
            int valor = volumenSlider.getValue();
            volumenLabel.setText(messages.getString("label.volume") + " " + valor + "%");
        }
    }
}