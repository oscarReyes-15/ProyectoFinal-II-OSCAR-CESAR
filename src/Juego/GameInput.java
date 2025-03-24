
package Juego;

import Objects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.UserFile;

public abstract class GameInput extends JPanel implements ObjectConstants, MapConstants{
    
    protected int[][] board = new int[COLS][ROWS];
    protected int playerX, playerY;
    protected int moveCount = 0;
    protected boolean gameCompleted = false;
    protected int currentLevel;
    
    protected JFrame frame;
    protected JButton resetBtn;
    protected JButton menuBtn;
    protected JLabel movesLabel;
    protected JLabel levelLabel;
    protected JLabel timerLabel; 
    
    protected Image backgroundImage;
    protected Image wallImage;
    protected Image boxImage;
    protected Image targetImage;
    protected Image playerImage;
    
    protected String usuarioActual;
    
    protected Thread timerThread;
    protected volatile boolean timerRunning;
    protected long startTime; 
    protected long elapsedTime; 
    
    public GameInput(String usuario, int nivel) {
        this.usuarioActual = usuario;
        this.currentLevel = nivel;
        this.setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        this.elapsedTime = 0;
        
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameCompleted) return;
                
                int key = e.getKeyCode();
                
                switch (key) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        movePlayer(0, -1);
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        movePlayer(0, 1);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        movePlayer(-1, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        movePlayer(1, 0);
                        break;
                    case KeyEvent.VK_R:
                        resetLevel();
                        break;
                }
                
                repaint();
                checkWinCondition();
            }
        });
        
        startTimer();
    }
    
    protected abstract void initializeLevel();
    protected abstract void checkWinCondition();
    protected abstract void movePlayer(int dx, int dy); // mover
    protected abstract void resetLevel();
    
    protected void startTimer() {
        if (timerThread != null && timerThread.isAlive()) {
            timerRunning = false;
            try {
                timerThread.join(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        timerRunning = true;
        startTime = System.currentTimeMillis();
        
        timerThread = new Thread(() -> {
            while (timerRunning) {
                long currentTime = System.currentTimeMillis();
                long currentElapsedTime = (currentTime - startTime) / 1000;
                
                SwingUtilities.invokeLater(() -> {
                    if (timerLabel != null) {
                        timerLabel.setText(formatTime(currentElapsedTime + elapsedTime));
                    }
                });
                
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        
        timerThread.setDaemon(true);
        timerThread.start();
    }
    
    protected void stopTimer() {
        timerRunning = false;
        
        if (timerThread != null) {
            try {
                timerThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        if (startTime > 0) {
            long currentTime = System.currentTimeMillis();
            elapsedTime += (currentTime - startTime) / 1000;
            
            if (usuarioActual != null) {
                UserFile.actualizarTiempoJugado(usuarioActual, elapsedTime);
            }
        }
    }
    
    protected String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
