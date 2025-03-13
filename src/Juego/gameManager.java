package Juego;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import User.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public final class gameManager extends JPanel {    
    // user
    User userActual; // todas las clases usan la misma referencia
    
    // manager  
    JPanel actual; // el panel a ser mostrado
    MapaMundi MM;  // clase que maneja diversos mundos
    JFrame f;
    
    public gameManager (User userActual) {
        // inicializacion
        f = new JFrame();
        this.userActual = userActual;

        MM = new MapaMundi(userActual, this);
        actual = MM.getMapa();
        
        // frame conf
        setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(300, 300));
        f.add(this);
        f.setVisible(true);
        
    }

    public void select (JPanel cartridge) {
        actual = null; // reusamos
        actual = cartridge;
        
        // segun la instancia 
        if (cartridge instanceof Mundo){
            ((Mundo)actual).cargar();
            
        } else if (cartridge instanceof Nivel){
            ((Nivel)actual).cargar(); 
        }
        display(cartridge);
    }
    
    public void display () {
        removeAll();
        add(actual);
        revalidate();
        repaint();
    
    }
    
    public void display (JPanel cartridge) {
        display();
        cartridge.requestFocus();
    }
    
    public static void main(String[] args) {
        gameManager G= new gameManager (new User("robRigattoni", "123"));
        G.display();
    }   
        
}
 /*
    - dependencias

    - funciones
        - display (Mundo o nivel)
        - select (Mundo o nivel, dsps de seleccionar despliega (display))
*/