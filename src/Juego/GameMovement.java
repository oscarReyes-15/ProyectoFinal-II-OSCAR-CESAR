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
    protected Image[] playerSprites = new Image[12]; 
    protected int currentSpriteIndex = 0;
    protected int lastDirection = 0; 
    
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
    
    protected Timer gameTimer;
    protected long startTime;
    protected long elapsedTime;
    
    public GameMovement(String usuario, int nivel) {
        this.usuarioActual = usuario;
        this.currentLevel = nivel;
        this.moveCount = 0;
        this.elapsedTime = 0;
        
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
                    UserFile.actualizarTiempoJugado(usuarioActual, 1); 
                }
            }
        }, 0, 1000); 
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
    
    protected void updatePlayerSprite(int direction) {
        currentSpriteIndex = (currentSpriteIndex + 1) % 3;
        switch (direction) {
            case 0: 
                playerImage = playerSprites[0 + currentSpriteIndex];
                break;
            case 1: 
                playerImage = playerSprites[3 + currentSpriteIndex];
                break;
            case 2: 
                playerImage = playerSprites[6 + currentSpriteIndex];
                break;
            case 3: 
                playerImage = playerSprites[9 + currentSpriteIndex];
                break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameCompleted) {
            return;
        }
        
        char keyChar = Character.toUpperCase(e.getKeyChar());
        
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
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    protected void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;
        
        if (dx > 0) {
            lastDirection = 1;
        } else if (dx < 0) {
            lastDirection = 0;
        } else if (dy > 0) {
            lastDirection = 3; 
        } else if (dy < 0) {
            lastDirection = 2; 
        }
        
        if (newX < 0 || newX >= COLS || newY < 0 || newY >= ROWS) {
            return;
        }
        
        if (board[newX][newY] == WALL) {
            return; 
        }
        
        boolean moved = false;
        
        if (board[newX][newY] == BOX || board[newX][newY] == BOX_ON_TARGET) {
            int newBoxX = newX + dx;
            int newBoxY = newY + dy;
            
            if (newBoxX < 0 || newBoxX >= COLS || newBoxY < 0 || newBoxY >= ROWS) {
                return;
            }
            
            if (board[newBoxX][newBoxY] == EMPTY || board[newBoxX][newBoxY] == TARGET) {
                if (board[newBoxX][newBoxY] == EMPTY) {
                    board[newBoxX][newBoxY] = BOX;
                } else { 
                    board[newBoxX][newBoxY] = BOX_ON_TARGET;
                }
                
                if (board[newX][newY] == BOX) {
                    board[newX][newY] = EMPTY;
                } else { 
                    board[newX][newY] = TARGET;
                }
                moved = true;
            } else {
                return; 
            }
        } else {
            moved = true;
        }
        
        if (board[playerX][playerY] == PLAYER) {
            board[playerX][playerY] = EMPTY;
        } else { 
            board[playerX][playerY] = TARGET;
        }
        
        playerX = newX;
        playerY = newY;
        
        if (board[playerX][playerY] == EMPTY) {
            board[playerX][playerY] = PLAYER;
        } else if (board[playerX][playerY] == TARGET) {
            board[playerX][playerY] = PLAYER_ON_TARGET;
        }
        
        if (moved) {
            updatePlayerSprite(lastDirection);
            moveCount++;
            movesLabel.setText("Movimientos: " + moveCount);
        }
        
        repaint();
        
        checkWinCondition();
    }
}