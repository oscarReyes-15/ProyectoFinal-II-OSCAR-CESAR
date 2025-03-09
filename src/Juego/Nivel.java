package Juego;

import javax.swing.JPanel;
import Objects.*;
import User.User;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Nivel extends JPanel {
    // dependencias
    User user;
    
    
    // atributos del nivel
    jugador player;
    inputHandler input;
    int moves;
    int stars = 0;
    boolean isDesBloqueado = false;
        
    public Nivel() {
        
    }
    
    public boolean cargarNivel () {
        if (isDesBloqueado){
            player = new jugador(user);
            input = new inputHandler(player);
            moves = player.getMoves();
            return true;
        } else {return false;}
    }
    
    public void input () {
       
    }
    
    public void abrirNivel () {
        isDesBloqueado = true;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.red);
        
        g2.fillRect(player.getX(), player.getY(), 100, 100);
        player.printPos();

    }
}
