package MenuGUI;

import Juego.gameManager;
import User.User;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
    
    private JButton jugar, salirBtn, miPerfilBtn;
    private String usuarioActual;
    private JLabel fondo;
    
    public Menu(String usuario) {
        this.usuarioActual = usuario;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Menu Principal");
        this.setLayout(null);
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        
        // Cargar imagen de fondo
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        jugar = new JButton("1. Jugar");
        jugar.setBounds(325, 150, 150, 30);
        jugar.addActionListener(this);
        this.add(jugar);
        
        miPerfilBtn = new JButton("2. Mi Perfil");
        miPerfilBtn.setBounds(325, 210, 150, 30);
        miPerfilBtn.addActionListener(this);
        this.add(miPerfilBtn);
        
        salirBtn = new JButton("3. Cerrar Sesion");
        salirBtn.setBounds(325, 270, 150, 30);
        salirBtn.addActionListener(this);
        this.add(salirBtn);
        
        this.add(fondo); // Se agrega el fondo al final para que esté en la capa más baja
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugar) {
            new SeleccionarNiveles(usuarioActual);
            this.dispose();
        } else if (e.getSource() == miPerfilBtn) {
            new MiPerfil(usuarioActual);
            this.dispose();
        } else if (e.getSource() == salirBtn) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesión?", 
                                                             "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                new MainMenu();
                this.dispose();
            }
        }
    }
}