package Juego;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class NivelMap {
    // Constants for map elements
    public static final int WALL = 1;
    public static final int EMPTY = 0;
    public static final int BOX = 2;
    public static final int TARGET = 3;
    public static final int PLAYER = 4;
    public static final int BOX_ON_TARGET = 5;
    public static final int PLAYER_ON_TARGET = 6;
    
    private int[][] map;
    private int width;
    private int height;
    private int playerX;
    private int playerY;
    
    // Images for each element
    private Image wallImg;
    private Image emptyImg;
    private Image boxImg;
    private Image targetImg;
    private Image playerImg;
    private Image boxOnTargetImg;
    private Image playerOnTargetImg;
    
    public NivelMap(int levelNumber) {
        // Load images
        loadImages();
        
        // Initialize map based on level number
        initializeMap(levelNumber);
    }
    
    private void loadImages() {
        wallImg = new ImageIcon("src/Images/wall.png").getImage();
        emptyImg = new ImageIcon("src/Images/empty.png").getImage();
        boxImg = new ImageIcon("src/Images/box.png").getImage();
        targetImg = new ImageIcon("src/Images/target.png").getImage();
        playerImg = new ImageIcon("src/Images/sprite1.png").getImage();
        boxOnTargetImg = new ImageIcon("src/Images/boxOnTarget.png").getImage();
        playerOnTargetImg = new ImageIcon("src/Images/playerOnTarget.png").getImage();
    }
    
    private void initializeMap(int levelNumber) {
        switch (levelNumber) {
            case 1:
                // Level 1 map layout similar to your screenshot
                map = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 1, 2, 0, 0, 0, 1, 0, 1},
                    {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
                    {1, 0, 0, 3, 3, 1, 2, 0, 0, 1},
                    {1, 0, 0, 0, 0, 1, 0, 0, 4, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                };
                break;
            case 2:
                // Level 2 map - create your own layout
                map = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 3, 0, 0, 3, 0, 1},
                    {1, 0, 0, 2, 2, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 4, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1}
                };
                break;
            // Add more levels as needed
            default:
                // Default empty map
                map = new int[][] {
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 4, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1}
                };
        }
        
        height = map.length;
        width = map[0].length;
        
        // Find player position
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == PLAYER || map[y][x] == PLAYER_ON_TARGET) {
                    playerX = x;
                    playerY = y;
                    break;
                }
            }
        }
    }
    
    public void draw(Graphics g, int tileSize) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int element = map[y][x];
                Image img = null;
                
                switch(element) {
                    case WALL:
                        img = wallImg;
                        break;
                    case EMPTY:
                        img = emptyImg;
                        break;
                    case BOX:
                        img = boxImg;
                        break;
                    case TARGET:
                        img = targetImg;
                        break;
                    case PLAYER:
                        img = playerImg;
                        break;
                    case BOX_ON_TARGET:
                        img = boxOnTargetImg;
                        break;
                    case PLAYER_ON_TARGET:
                        img = playerOnTargetImg;
                        break;
                }
                
                if (img != null) {
                    g.drawImage(img, x * tileSize, y * tileSize, tileSize, tileSize, null);
                }
            }
        }
    }
    
    // Move player in the specified direction
    public boolean movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;
        
        // Check if the move is valid
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return false;
        }
        
        // Check if the destination is a wall
        if (map[newY][newX] == WALL) {
            return false;
        }
        
        // Check if the destination contains a box
        if (map[newY][newX] == BOX || map[newY][newX] == BOX_ON_TARGET) {
            int newBoxX = newX + dx;
            int newBoxY = newY + dy;
            
            // Check if the box can be pushed
            if (newBoxX < 0 || newBoxX >= width || newBoxY < 0 || newBoxY >= height) {
                return false;
            }
            
            if (map[newBoxY][newBoxX] == WALL || map[newBoxY][newBoxX] == BOX || map[newBoxY][newBoxX] == BOX_ON_TARGET) {
                return false;
            }
            
            // Move the box
            if (map[newBoxY][newBoxX] == TARGET) {
                map[newBoxY][newBoxX] = BOX_ON_TARGET;
            } else {
                map[newBoxY][newBoxX] = BOX;
            }
            
            // Update the current box position
            if (map[newY][newX] == BOX_ON_TARGET) {
                map[newY][newX] = TARGET;
            } else {
                map[newY][newX] = EMPTY;
            }
        }
        
        // Move the player
        if (map[newY][newX] == TARGET) {
            map[newY][newX] = PLAYER_ON_TARGET;
        } else {
            map[newY][newX] = PLAYER;
        }
        
        // Update the old player position
        if (map[playerY][playerX] == PLAYER_ON_TARGET) {
            map[playerY][playerX] = TARGET;
        } else {
            map[playerY][playerX] = EMPTY;
        }
        
        // Update player position
        playerX = newX;
        playerY = newY;
        
        return true;
    }
    
    // Check if the level is complete (all boxes are on targets)
    public boolean isLevelComplete() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == BOX) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Getters for player position
    public int getPlayerX() {
        return playerX;
    }
    
    public int getPlayerY() {
        return playerY;
    }
    
    // Map dimensions
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}