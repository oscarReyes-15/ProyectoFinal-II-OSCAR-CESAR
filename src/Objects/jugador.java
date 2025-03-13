package Objects;


import Juego.Nivel;
import Juego.inputHandler;
import User.User;
import java.awt.Graphics2D;
import javax.swing.*;



public class jugador {
    private final User user;
    private inputHandler input;
    private Nivel nivel;
    
    private int x, y;
    private int moves = 0;
    private final ImageIcon playerTexture;
    
    private int speed = 25;
    
    public jugador (User user, inputHandler input, Nivel nivel) {
        this.user = user;
        this.input = input;
        this.nivel = nivel;
        
        x = nivel.getSpawnX(); y = nivel.getSpawnY();
        playerTexture = new ImageIcon("C:\\Users\\LENOVO\\OneDrive - Universidad Tecnologica Centroamericana\\Trimestre #3\\Programacion II\\GarbageTests\\SodokanV1\\src\\Images\\avatar2.png");
    }
    
    public int getMoves () {
        return moves;
    }

    public ImageIcon getImage () {
        return playerTexture;
    }
    
    public void move () {
        int y2 = y;
        int x2 = x;
        if (input.downPressed && (y2 += speed) < nivel.getHeight()){
            y += speed;
        } else if (input.upPressed && (y2 += speed) > 0){
            y += -speed;
        }
        if (input.rightPressed && (x2 += speed) < nivel.getWidth()){
            x += speed;
        } else if (input.leftPressed && (x2 += speed) > 0){
            x += -speed;
        }
        moves += 1;
    }
    
    
    public void draw (Graphics2D g, JComponent comp) {
        g.drawImage(getImage().getImage(), x, y, 64, 64,  comp);
    }
    
    public void printPos () {
        System.out.println( x + " " + y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    
}
