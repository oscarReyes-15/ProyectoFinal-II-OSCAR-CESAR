package Niveles;

import Juego.Juego;
import javax.swing.*;
import User.*;

/**
 *
 * @author danilos
 */
public class Nivel4 extends Juego{
    
    public Nivel4() {
        this(null);
    }
    
    public Nivel4(String usuario) {
        super(usuario, 4);
        
        loadImages();
        setupFrame("Sokoban - Nivel 4");
        
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
        board[2][5] = WALL;
        board[2][6] = WALL;
        board[3][2] = WALL;
        board[4][4] = WALL;
        board[5][4] = WALL;
        board[6][2] = WALL;
        board[6][3] = WALL;
        board[6][4] = WALL;
        board[6][6] = WALL;
        board[6][7] = WALL;
        board[7][7] = WALL;
        
        board[4][7] = TARGET;
        board[5][7] = TARGET;
        board[7][3] = TARGET;
        board[7][4] = TARGET;
        
        board[3][4] = BOX;
        board[3][5] = BOX;
        board[4][5] = BOX;
        board[5][5] = BOX;
        
        playerX = 4;
        playerY = 3;
        board[playerX][playerY] = PLAYER;
        
        moveCount = 0;
        movesLabel.setText("Movimientos: " + moveCount);
        
        gameCompleted = false;
    }
    
    @Override
    protected void checkWinCondition() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (board[x][y] == BOX) {
                    return;
                }
            }
        }
        
        gameCompleted = true;
        
        if (usuarioActual != null) {
            UserFile.setNivelCompletado(usuarioActual, 2);
            
            int puntos = 1500 - (moveCount * 5); 
            if (puntos < 150) puntos = 150; 
            UserFile.actualizarPuntos(usuarioActual, puntos);
        }
        
        JOptionPane.showMessageDialog(frame, "¡Nivel completado!\nMovimientos: " + moveCount, 
                                     "Felicitaciones", JOptionPane.INFORMATION_MESSAGE);
    }
}

