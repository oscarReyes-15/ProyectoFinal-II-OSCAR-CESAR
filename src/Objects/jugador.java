package Objects;

import Juego.Nivel;
import Juego.inputHandler;
import User.User;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class jugador {
    // Attributes
    private User user;
    private inputHandler input;
    private Nivel nivel;
    private int moves;
    private ImageIcon image;
    
    // Constructor
    public jugador(User user, inputHandler input, Nivel nivel) {
        this.user = user;
        this.input = input;
        this.nivel = nivel;
        this.moves = 0;
        this.image = new ImageIcon("src/Images/sprite1.png");
    }
    
    // Methods
    public void move() {
        // Movement is now handled in the Nivel class directly
    }
    
    public void incrementMoves() {
        moves++;
    }
    
    public int getMoves() {
        return moves;
    }
    
    public ImageIcon getImage() {
        return image;
    }
    
    public void draw(Graphics2D g2, JPanel panel) {
        // No need to draw here since the map handles drawing the player
    }
}