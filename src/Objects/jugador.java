package Objects;


import Juego.Nivel;
import Juego.inputHandler;
import User.User;
import java.awt.Graphics2D;
import javax.swing.*;



public class jugador extends Object {
    private final User user;
    private inputHandler input;
    private Nivel nivel;
    
    private int moves = 0;    
    private int speed = 16;
    
    public jugador (User user, inputHandler input, Nivel nivel) {
        super(nivel.getSpawnX(), nivel.getSpawnY(), this.nivel = nivel);
        this.user = user;
        this.input = input;
        
        image = new ImageIcon("C:\\Users\\LENOVO\\OneDrive - Universidad Tecnologica Centroamericana\\Trimestre #3\\Programacion II\\GarbageTests\\SodokanV1\\src\\Images\\avatar2.png");
    }
    
    public int getMoves () {
        return moves;
    }
    
    public void move () {
        if (input.downPressed && isInBound(speed, "y")){
            y += speed;
        } else if (input.upPressed && isInBound(-speed, "y")){
            y += -speed;
        }
        if (input.rightPressed && isInBound(speed, "x")){
            x += speed;
        } else if (input.leftPressed && isInBound(-speed, "x")){
            x += -speed;
        }
        moves += 1;
    }
    
    
    public void draw (Graphics2D g, JComponent comp) {
        g.drawImage(getImage().getImage(), x, y, tileSize, tileSize,  comp);
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
