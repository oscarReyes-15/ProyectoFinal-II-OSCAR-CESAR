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
public class Nivel3 extends Juego{
    
    public Nivel3() {
        this(null);
    }
    
    public Nivel3(String usuario) {
        super(usuario, 3);
        
        loadImages();
        setupFrame("Sokoban - " + messages.getString("level.3"));
        
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
        
        board[4][1]  = EMPTY;
        board[5][1]  = TARGET;
        board[6][1]  = TARGET;
        board[7][1]  = EMPTY;
        
        board[2][2]  = EMPTY;
        board[3][2]  = EMPTY;
        board[4][2]  = BOX;
        board[5][2]  = TARGET;
        board[6][2]  = EMPTY;
        board[7][2]  = EMPTY;
        
        board[2][3]  = EMPTY;
        board[3][3]  = BOX;
        board[4][3]  = EMPTY;
        board[6][3]  = EMPTY;
        
        board[3][4]  = EMPTY;
        board[4][4]  = BOX;
        board[5][4]  = EMPTY;
        board[6][4]  = EMPTY;
        board[7][4]  = EMPTY;
        
        board[3][5]  = EMPTY;
        board[4][5]  = EMPTY;
        board[6][5]  = EMPTY;
        board[7][5]  = EMPTY;
        
        
        player.X = 3;
        player.Y = 2;
        board[player.X][player.Y] = PLAYER;
        
        
        moveCount = 0;
        movesLabel.setText(messages.getString("game.movements") + ": " + moveCount);
        
        Musica.getInstance().play(3);
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
            UserFile.setNivelCompletado(usuarioActual, 3);
            
            int puntos = 1500 - (moveCount * 5); 
            if (puntos < 150) puntos = 150; 
            UserFile.actualizarPuntos(usuarioActual, puntos);
        }
        
        String tiempoFormateado = formatTime(elapsedTime);
        JOptionPane.showMessageDialog(frame, 
         messages.getString("dialog.levelCompleted") + "\n" +
            messages.getString("game.movements") + ": " + moveCount + "\n" +
            messages.getString("game.timer") + ": " + tiempoFormateado,
            messages.getString("dialog.congratulations"), JOptionPane.INFORMATION_MESSAGE);
        
        JOptionPane.showMessageDialog(frame, 
            messages.getString("dialog.nextLevelAvailable") + "\n",
            "", JOptionPane.INFORMATION_MESSAGE);   

        new SeleccionarNiveles(usuarioActual);
        frame.dispose();
        Musica.getInstance().play(0);
    }
}
