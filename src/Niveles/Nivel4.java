package Niveles;

import Audio.Musica;
import Audio.Sonidos;
import Juego.Juego;
import SubMenuOption.SeleccionarNiveles;
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
                board[x][y] = WALL;
            }
        }
        
        board[3][1] = EMPTY;
        board[4][1] = TARGET;
        board[2][2] = EMPTY;
        board[2][3] = EMPTY;
        board[3][2] = EMPTY;
        board[4][2] = EMPTY;
        board[2][2] = EMPTY;
        board[3][3] = EMPTY;
        board[4][3] = EMPTY;
        board[5][3] = EMPTY;
        board[5][4] = EMPTY;
        board[6][4] = BOX;
        board[7][4] = EMPTY;
        board[2][5] = EMPTY;
        board[3][5] = EMPTY;
        board[4][5] = EMPTY;
        board[5][5] = BOX;
        board[6][5] = EMPTY;
        board[7][5] = EMPTY;
        board[2][6] = EMPTY;
        board[3][6] = EMPTY;
        board[4][6] = TARGET;
        board[5][6] = BOX;
        board[6][6] = TARGET;
        board[7][6] = EMPTY;
        
        playerX = 3;
        playerY = 4;
        board[playerX][playerY] = PLAYER;
        
        moveCount = 0;
        movesLabel.setText("Movimientos: " + moveCount);
        
        Musica.getInstance().play(1);
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
        
        Musica.getInstance().audioStop();
        gameCompleted = true;
        Sonidos.getInstance().play(2);
        
        if (usuarioActual != null) {
            UserFile.setNivelCompletado(usuarioActual, 4);
            
            int puntos = 1500 - (moveCount * 5); 
            if (puntos < 150) puntos = 150; 
            UserFile.actualizarPuntos(usuarioActual, puntos);
        }
        
        JOptionPane.showMessageDialog(frame, "¡Nivel completado!\nMovimientos: " + moveCount, 
                                     "Felicitaciones", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(frame, 
        "¡Ya puedes avanzar al siguiente nivel!\n" +"","", JOptionPane.INFORMATION_MESSAGE);
        new SeleccionarNiveles(usuarioActual);
        frame.dispose();
        Musica.getInstance().play(0);
    }
}

