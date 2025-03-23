package Niveles;
import Audio.Musica;
import Audio.Sonidos;
import Juego.Juego;
import SubMenuOption.SeleccionarNiveles;
import javax.swing.*;
import java.awt.*;
import User.*;

public class Nivel1 extends Juego {
    
    public Nivel1() {
        this(null);
    }
    
    public Nivel1(String usuario) {
        super(usuario, 1);
        
        loadImages();
        setupFrame("Sokoban - Nivel 1");
        
        if (usuarioActual != null) {
            UserFile.incrementarPartidasJugadas(usuarioActual);
        }
        
        initializeLevel();
        
        this.requestFocus();
    }
    
    @Override
    protected void initializeLevel() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                board[x][y] = EMPTY;
            }
        }
        
        for (int x = 0; x < COLS; x++) {
            board[x][0] = WALL;
            board[x][ROWS - 1] = WALL;
        }
        
        for (int y = 0; y < ROWS; y++) {
            board[0][y] = WALL;
            board[COLS - 1][y] = WALL;
        }
        
        board[2][2] = WALL;
        board[2][3] = WALL;
        board[2][4] = WALL;
        board[3][2] = WALL;
        board[4][2] = WALL;
        board[7][2] = WALL;
        board[7][3] = WALL;
        board[7][4] = WALL;
        board[7][5] = WALL;
        board[7][6] = WALL;
        board[7][7] = WALL;
        board[6][7] = WALL;
        board[5][7] = WALL;
        board[4][7] = WALL;
        
        board[2][6] = TARGET;
        board[3][6] = TARGET;
        board[4][6] = TARGET;
        
        board[3][4] = BOX;
        board[4][4] = BOX;
        board[5][4] = BOX;
        
        playerX = 5;
        playerY = 5;
        board[playerX][playerY] = PLAYER;
        
        moveCount = 0;
        movesLabel.setText("Movimientos: " + moveCount);
        
        gameCompleted = false;
        Musica.getInstance().audioStop();
        Musica.getInstance().play(1);
    }
    
    @Override
    protected void checkWinCondition() {
        boolean allBoxesOnTargets = true;
        int boxOnTargetCount = 0;
        
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (board[x][y] == BOX) {
                    allBoxesOnTargets = false;
                } else if (board[x][y] == BOX_ON_TARGET) {
                    boxOnTargetCount++;
                }
            }
        }
        
        if (!allBoxesOnTargets && boxOnTargetCount < 3) {
            return;
        }
        
        Musica.getInstance().audioStop();
        gameCompleted = true;
        Sonidos.getInstance().play(2);
        
        stopTimer();
        
        if (usuarioActual != null) {
            UserFile.setNivelCompletado(usuarioActual, 1);
            
            int puntosPorMovimientos = 1000 - (moveCount * 5);
            if (puntosPorMovimientos < 100) puntosPorMovimientos = 100; // Minimum points
            
            UserFile.actualizarPuntos(usuarioActual, puntosPorMovimientos);
            
        }
        
        String tiempoFormateado = formatTime(elapsedTime);
        
        JOptionPane.showMessageDialog(frame, 
            "¡Nivel completado!\n" +
            "Movimientos: " + moveCount + "\n" +
            "Tiempo: " + tiempoFormateado,
            "Felicitaciones", JOptionPane.INFORMATION_MESSAGE);
         JOptionPane.showMessageDialog(frame, 
        "¡Ya puedes avanzar al siguiente nivel!\n" +"","", JOptionPane.INFORMATION_MESSAGE);
        new SeleccionarNiveles(usuarioActual);
        frame.dispose();
        Musica.getInstance().play(0);
    }
}