package Juego;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import User.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public final class gameManager extends JFrame implements Runnable {
    // game functioning
    Thread gameThread;
    int fps = 10;
    
    // user
    User userActual;
    
    // manager de mundos 
    Mundo mundo1, mundo2; JPanel mundoActual;
    Nivel nivelTest;
    
    public gameManager (User userActual) {
        // inicializacion
        JFrame f = new JFrame();
        this.userActual = userActual;
        mundo1 = new Mundo(userActual, this);
        mundo2 = new Mundo(userActual, this);
        mundoActual = mundo1;
        nivelTest = new Nivel();
        
        // frame conf
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(300, 300));
        f.add(this);
        f.setVisible(true);
        
    }
    
    public void startGame () {
        gameThread = new Thread(this);
        nivelTest.abrirNivel();
        if(nivelTest.cargarNivel())
            gameThread.start(); addKeyListener(nivelTest.input);
            
    }
    
    @Override
    public void run() {
        while (gameThread != null){
            
            // 1. actualizar valores
            update();
           
            // 2. pintar en el canvas      
            revalidate();
            repaint();
            
            // 3. WAIT
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException ex) {
                Logger.getLogger(gameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    void update () {
        System.out.println("updated");
    }
    
    
    
    public static void main(String[] args) {
        gameManager G= new gameManager (new User("robRigattoni", "123"));
        G.startGame();
    }
}
