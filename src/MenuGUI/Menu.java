package MenuGUI;

import Juego.gameManager;
import User.User;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
    
    private JButton jugar, salirBtn, miPerfilBtn;
    private String usuarioActual;

    public Menu(String usuario) {
        this.usuarioActual = usuario;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Menu Principal");
        this.setLayout(null);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(177, 37, 7));

        jugar = new JButton("1. Jugar");
        jugar.setBounds(125, 100, 150, 30);
        jugar.addActionListener(this);
        this.add(jugar);

        miPerfilBtn = new JButton("2. Mi Perfil");
        miPerfilBtn.setBounds(125, 160, 150, 30);
        miPerfilBtn.addActionListener(this);
        this.add(miPerfilBtn);
        
        salirBtn=new JButton("3. Cerrar Sesion");
        salirBtn.setBounds(125,200,150,30);
        salirBtn.addActionListener(this);
        this.add(salirBtn);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugar) {
            gameManager G= new gameManager (new User("robRigattoni", "123"));
            G.display();
        } if(e.getSource()==miPerfilBtn){
                  new MiPerfil(usuarioActual);
                  this.dispose();
        }
        else if (e.getSource() == salirBtn) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesión?", 
                                                             "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                new MainMenu();
                this.dispose();
            }
        }
    }

}
