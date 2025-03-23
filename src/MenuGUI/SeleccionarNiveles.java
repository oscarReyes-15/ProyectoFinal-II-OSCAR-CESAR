package MenuGUI;

import Niveles.*;
import Sounds.Musica;
import Sounds.Sonidos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 *
 * @author danilos
 */
public class SeleccionarNiveles extends JFrame implements ActionListener {
    private JButton lvl1btn, lvl2btn, lvl3btn, lvl4btn, lvl5btn, backBtn;
    private String usuarioActual;
    private JLabel fondo, titleLabel;
    private ResourceBundle messages;
    private Sonidos s;
    
    public SeleccionarNiveles(String usuario, Sonidos s) {
        this.usuarioActual = usuario;
        this.messages = LanguageManager.getMessages();
        this.s = s;
        
        this.setTitle(messages.getString("title.selectLevel"));
        this.setSize(800, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
   
        int buttonWidth = 80;
        int buttonHeight = 80;
        int spacing = 20;
        int startX = 150;
        int y = 150;
        
        lvl1btn = createLevelButton(messages.getString("1"), startX, y, buttonWidth, buttonHeight);
        lvl2btn = createLevelButton(messages.getString("2"), startX + buttonWidth + spacing, y, buttonWidth, buttonHeight);
        lvl3btn = createLevelButton(messages.getString("3"), startX + (buttonWidth + spacing) * 2, y, buttonWidth, buttonHeight);
        lvl4btn = createLevelButton(messages.getString("4"), startX + (buttonWidth + spacing) * 3, y, buttonWidth, buttonHeight);
        lvl5btn = createLevelButton(messages.getString("5"), startX + (buttonWidth + spacing) * 4, y, buttonWidth, buttonHeight);
        
        backBtn = new JButton(messages.getString("button.back"));
        backBtn.setBounds(350, 350, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);
        
        Musica.getInstance().stop();
        Musica.getInstance().play(1);
        
        this.add(fondo);
        
        this.setVisible(true);
    }
    
    private JButton createLevelButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(245, 222, 179));
        button.setForeground(new Color(139, 69, 19)); 
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(this);
        add(button);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            s.Play(3);
            s.setSound(2);
            s.Stop();
            this.dispose();
            new Menu(usuarioActual,s);
        } else if (e.getSource() == lvl1btn) {
            s.Play(3);
            s.setSound(2);
            s.Stop();
            new Nivel1(usuarioActual, s); 
            this.dispose();
        } else if (e.getSource() == lvl2btn) {
            s.Play(3);
            s.setSound(2);
            s.Stop();
            new Nivel2(usuarioActual, s); 
            this.dispose();
        } else if (e.getSource() == lvl3btn) {
            s.Play(3);
            s.setSound(2);
            s.Stop();
            new Nivel3(usuarioActual, s); 
            this.dispose();    
        } else if (e.getSource() == lvl4btn) {
            s.Play(3); 
            s.setSound(2);
            s.Stop();
            new Nivel4(usuarioActual, s); 
            this.dispose();
        } else if (e.getSource() == lvl5btn) {
            s.Play(3);
            s.setSound(2);
            s.Stop();
            new Nivel5(usuarioActual, s); 
            this.dispose();
        }
    }
}