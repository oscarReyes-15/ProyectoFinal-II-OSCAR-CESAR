package Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import User.*;

public abstract class GameMovement extends JPanel implements KeyListener {
    
    protected static final int ROWS = 10;
    protected static final int COLS = 10;
    protected static final int CELL_SIZE = 60;
    
    // Cell types
    protected static final int EMPTY = 0;
    protected static final int WALL = 1;
    protected static final int BOX = 2;
    protected static final int TARGET = 3;
    protected static final int PLAYER = 4;
    protected static final int BOX_ON_TARGET = 5;
    protected static final int PLAYER_ON_TARGET = 6;
    
    protected int[][] board = new int[COLS][ROWS];
    protected int playerX, playerY;
    
    protected Image backgroundImage;
    protected Image wallImage;
    protected Image boxImage;
    protected Image playerImage;
    protected Image targetImage;
    
    protected JFrame frame;
    protected JLabel levelLabel;
    protected JLabel movesLabel;
    protected JLabel timerLabel;
    protected JButton resetBtn;
    protected JButton menuBtn;
    
    protected int currentLevel;
    protected int moveCount;
    protected boolean gameCompleted;
    protected String usuarioActual;
    
    // Timer variables
    protected Timer gameTimer;
    protected long startTime;
    protected long elapsedTime;
    
    public GameMovement(String usuario, int nivel) {
        this.usuarioActual = usuario;
        this.currentLevel = nivel;
        this.moveCount = 0;
        this.elapsedTime = 0;
        
        // Apply the user's custom controls if available
        if (usuario != null) {
            ControlManager.applyUserControls(usuario);
        }
        
        setFocusable(true);
        addKeyListener(this);
        
        startTimer();
    }
    
    protected abstract void initializeLevel();
    protected abstract void checkWinCondition();
    protected abstract void resetLevel();
    
    protected void startTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        
        startTime = System.currentTimeMillis() - elapsedTime;
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - startTime;
                updateTimerLabel();
                
                if (usuarioActual != null) {
                    UserFile.actualizarTiempoJugado(usuarioActual, 1); // Add 1 second
                }
            }
        }, 0, 1000); // Update every second
    }
    
    protected void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
        }
    }
    
    protected void updateTimerLabel() {
        SwingUtilities.invokeLater(() -> {
            timerLabel.setText("Tiempo: " + formatTime(elapsedTime));
        });
    }
    
    protected String formatTime(long timeMillis) {
        long seconds = timeMillis / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameCompleted) {
            return;
        }
        
        char keyChar = Character.toUpperCase(e.getKeyChar());
        
        // Check if it matches custom controls
        if (keyChar == ControlSettings.UP_KEY) {
            movePlayer(0, -1);
        } else if (keyChar == ControlSettings.DOWN_KEY) {
            movePlayer(0, 1);
        } else if (keyChar == ControlSettings.LEFT_KEY) {
            movePlayer(-1, 0);
        } else if (keyChar == ControlSettings.RIGHT_KEY) {
            movePlayer(1, 0);
        } else if (keyChar == ControlSettings.RESET_KEY) {
            resetLevel();
        }
        
        // Also support arrow keys for alternative control
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            movePlayer(0, -1);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            movePlayer(0, 1);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            movePlayer(-1, 0);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movePlayer(1, 0);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
    
    protected void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;
        
        // Check bounds
        if (newX < 0 || newX >= COLS || newY < 0 || newY >= ROWS) {
            return;
        }
        
        // Check destination cell
        if (board[newX][newY] == WALL) {
            return; // Can't move through walls
        }
        
        if (board[newX][newY] == BOX || board[newX][newY] == BOX_ON_TARGET) {
            // Try to push the box
            int newBoxX = newX + dx;
            int newBoxY = newY + dy;
            
            // Check bounds for the box
            if (newBoxX < 0 || newBoxX >= COLS || newBoxY < 0 || newBoxY >= ROWS) {
                return;
            }
            
            // Check if box can be moved
            if (board[newBoxX][newBoxY] == EMPTY || board[newBoxX][newBoxY] == TARGET) {
                // Move the box
                if (board[newBoxX][newBoxY] == EMPTY) {
                    board[newBoxX][newBoxY] = BOX;
                } else { // TARGET
                    board[newBoxX][newBoxY] = BOX_ON_TARGET;
                }
                
                // Update the player's cell
                if (board[newX][newY] == BOX) {
                    board[newX][newY] = EMPTY;
                } else { // BOX_ON_TARGET
                    board[newX][newY] = TARGET;
                }
            } else {
                return; // Box can't be moved
            }
        }
        
        // Move player
        if (board[playerX][playerY] == PLAYER) {
            board[playerX][playerY] = EMPTY;
        } else { // PLAYER_ON_TARGET
            board[playerX][playerY] = TARGET;
        }
        
        playerX = newX;
        playerY = newY;
        
        if (board[playerX][playerY] == EMPTY) {
            board[playerX][playerY] = PLAYER;
        } else if (board[playerX][playerY] == TARGET) {
            board[playerX][playerY] = PLAYER_ON_TARGET;
        }
        
        moveCount++;
        movesLabel.setText("Movimientos: " + moveCount);
        
        repaint();
        
        checkWinCondition();
    }
}