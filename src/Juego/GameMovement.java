package Juego;

import Audio.Sonidos;
import Objects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import User.*;

public abstract class GameMovement extends JPanel implements KeyListener, ObjectConstants, MapConstants{
       
    protected int[][] board = new int[COLS][ROWS];
    
    protected Image backgroundImage;
    protected Image wallImage;
    protected Image boxImage;
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
    
    protected Timer gameTimer;
    protected long startTime;
    protected long elapsedTime;
    
    protected Jugador player;
    
    public GameMovement(String usuario, int nivel) {
        this.usuarioActual = usuario;
        this.currentLevel = nivel;
        this.moveCount = 0;
        this.elapsedTime = 0;
        
        player = new Jugador();
        
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
    
    protected final void startTimer() {
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
        int newX = player.X + dx;
        int newY = player.Y + dy;
        
        player.updateDirection(dx, dy);
        
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
                Sonidos.getInstance().play(1);
            } else {
                return; 
            }
        } else {
            Sonidos.getInstance().play(0);
            moved = true;
        }
        
        if (board[player.X][player.Y] == PLAYER) {
            board[player.X][player.Y] = EMPTY;
        } else { 
            board[player.X][player.Y] = TARGET;
        }
        
        player.move(newX,newY);
        
        if (board[player.X][player.Y] == EMPTY) {
            board[player.X][player.Y] = PLAYER;
        } else if (board[player.X][player.Y] == TARGET) {
            board[player.X][player.Y] = PLAYER_ON_TARGET;
        }
        
        if (moved) {
            player.updatePlayerSprite(player.lastDirection);
            moveCount++;
            movesLabel.setText("Movimientos: " + moveCount);
        }
        
        
        
        repaint();
        
        checkWinCondition();
    }
}