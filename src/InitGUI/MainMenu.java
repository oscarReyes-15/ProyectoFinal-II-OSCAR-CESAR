package InitGUI;

import Audio.Sonidos;
import InitGUI.Login;
import InitGUI.Registro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private JButton loginbtn;
    private JButton crearcuentabtn;
    private JButton salirbtn;
    private JLabel fondo;
    
    public MainMenu() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        this.setTitle("MENU");
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        loginbtn = new JButton("1. Iniciar Sesion");
        loginbtn.setBounds(325, 150, 150, 40);
        loginbtn.addActionListener(this);
        this.add(loginbtn);
        
        crearcuentabtn = new JButton("2. Crear Cuenta");
        crearcuentabtn.setBounds(325, 210, 150, 40);
        crearcuentabtn.addActionListener(this);
        this.add(crearcuentabtn);
        
        salirbtn = new JButton("3. Salir");
        salirbtn.setBounds(325, 270, 150, 40);
        salirbtn.addActionListener(this);
        this.add(salirbtn);
        
        this.add(fondo); 
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Sonidos.getInstance().play(3);
        if (e.getSource() == loginbtn) {
            new Login();
            this.dispose();
        } else if (e.getSource() == crearcuentabtn) {
            new Registro();
            this.dispose();
        } else if (e.getSource() == salirbtn) {
            JOptionPane.showMessageDialog(null, "Saliendo");
            System.exit(0);
        }
    }
}