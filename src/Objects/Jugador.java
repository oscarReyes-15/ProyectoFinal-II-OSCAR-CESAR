/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import java.awt.Image;

/**
 *
 * @author LENOVO
 */
public class Jugador {
    public Image playerImage;//
    public Image[] playerSprites = new Image[12]; //
    public int currentSpriteIndex = 0;//
    public int lastDirection = 0; //
    public int X, Y;
    
    
    public void updatePlayerSprite(int direction) {
        currentSpriteIndex = (currentSpriteIndex + 1) % 3;
        switch (direction) {
            case 0: 
                loadImage(0 + currentSpriteIndex);
                break;
            case 1: 
                loadImage(3 + currentSpriteIndex);
                break;
            case 2: 
                loadImage(6 + currentSpriteIndex);
                break;
            case 3: 
                loadImage(9 + currentSpriteIndex);
                break;
        }
    }
    
    public void move (int x, int y) {
        X = x; Y = y;
    }
    
    public void updateDirection (int dx, int dy) {
        if (dx > 0) {
            lastDirection = 1;
        } else if (dx < 0) {
            lastDirection = 0;
        } else if (dy > 0) {
            lastDirection = 3; 
        } else if (dy < 0) {
            lastDirection = 2; 
        }
    }
    
    public void loadImage (int n) {
        playerImage = playerSprites[n];
    }
        
    public int updateSpriteImage () {
        return currentSpriteIndex = (currentSpriteIndex + 1) % 3;
    }
    
}
