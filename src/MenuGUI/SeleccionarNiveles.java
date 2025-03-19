package MenuGUI;

import Niveles.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author danilos
 */
public class SeleccionarNiveles extends JFrame implements ActionListener {
    private JButton lvl1btn, lvl2btn, lvl3btn, lvl4btn, lvl5btn, backBtn;
    private String usuarioActual;
    private JLabel fondo;
    
    public SeleccionarNiveles(String usuario) {
        this.usuarioActual = usuario;
        
        this.setTitle("Seleccionar Nivel");
        this.setSize(800, 450); // Same resolution as NewAvatar
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
        
        lvl1btn = createLevelButton("Nivel 1", startX, y, buttonWidth, buttonHeight);
        lvl2btn = createLevelButton("Nivel 2", startX + buttonWidth + spacing, y, buttonWidth, buttonHeight);
        lvl3btn = createLevelButton("Nivel 3", startX + (buttonWidth + spacing) * 2, y, buttonWidth, buttonHeight);
        lvl4btn = createLevelButton("Nivel 4", startX + (buttonWidth + spacing) * 3, y, buttonWidth, buttonHeight);
        lvl5btn = createLevelButton("Nivel 5", startX + (buttonWidth + spacing) * 4, y, buttonWidth, buttonHeight);
        
        // Create back button
        backBtn = new JButton("Regresar");
        backBtn.setBounds(350, 350, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);
        
        this.add(fondo);
        
        this.setVisible(true);
    }
    
    private JButton createLevelButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(245, 222, 179)); // Light  
        button.setForeground(new Color(139, 69, 19)); // Dark brown color
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(this);
        add(button);
        return button;
    }
    
  @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == backBtn) {
        this.dispose();
        new Menu(usuarioActual);
    } else if (e.getSource() == lvl1btn) {
        new Nivel1(usuarioActual); 
        this.dispose();
    } else if (e.getSource() == lvl2btn) {
         new Nivel2(usuarioActual); 
        this.dispose();
    } 
}
}