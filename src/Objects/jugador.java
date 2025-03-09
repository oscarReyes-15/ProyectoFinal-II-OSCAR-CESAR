package Objects;


import User.User;
import javax.swing.*;



public class jugador {
    private final User user;
    private int x, y;
    private int moves = 0;
    private final ImageIcon playerTexture;
    private JLabel playerObj;
    
    public jugador (User user) {
        this.user = user;
        
        x = 0; y = 0;
        playerTexture = new ImageIcon("Images\\avatar1.png");
        playerObj = new JLabel(playerTexture);
    }
    
    public int getMoves () {
        return moves;
    }

    public JLabel getPlayerObj() {
        return playerObj;
    }
        
    public ImageIcon getImage () {
        return playerTexture;
    }
    
    public void moveX (float paso) {
        x += paso; 
        moves += 1;
    }
    
    public void moveY (float paso) {
        y += paso;
        moves += 1;
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
