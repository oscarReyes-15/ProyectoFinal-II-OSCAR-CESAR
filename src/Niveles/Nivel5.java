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
public class Nivel5 extends Juego{
    
    public Nivel5() {
        this(null);
    }
    
    public Nivel5(String usuario) {
        super(usuario, 5);
        
        loadImages();
        setupFrame("Sokoban - " + messages.getString("level.5"));
        
        if (usuarioActual != null) {
            UserFile.incrementarPartidasJugadas(usuarioActual);
        }
        
        initializeLevel();
        this.requestFocus();
    }
    
    @Override
    protected void initializeLevel() {
        
        for (int x = 0; x < COLS ; x++) {
            for (int y = 0; y < ROWS; y++) {
                board[x][y] = WALL;
            }
        }
        
        
        board[4][2] =  TARGET;
        board[5][2] =  TARGET;
        board[4][3] = EMPTY;
        board[5][3] = TARGET;
        board[3][4] = EMPTY;
        board[4][4] = EMPTY;
        board[5][4] = BOX;
        board[6][4] = TARGET;
        board[3][5] = EMPTY;
        board[4][5] = BOX;
        board[5][5] = EMPTY;
        board[6][5] = EMPTY;
        board[2][6] = EMPTY;
        board[3][6] = EMPTY;
        board[5][6] = BOX;
        board[6][6] = BOX;
        board[7][6] = EMPTY;
        board[2][7] = EMPTY;
        board[3][7] = EMPTY;
        board[4][7] = EMPTY;
        board[5][7] = EMPTY;
        board[6][7] = EMPTY;
        board[7][7] = EMPTY;        
        
       
        
        player.X = 6;
        player.Y = 7;
        board[player.X][player.Y] = PLAYER;       
        
        moveCount = 0;
        movesLabel.setText(messages.getString("game.movements") + ": " + moveCount);
        
        Musica.getInstance().play(2);
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
            UserFile.setNivelCompletado(usuarioActual, 5);
            
            int puntos = 1500 - (moveCount * 5); 
            if (puntos < 150) puntos = 150; 
            UserFile.actualizarPuntos(usuarioActual, puntos);
            GameHistory.registrarPartida(usuarioActual, 5, elapsedTime / 1000, true);

        }
        
         String tiempoFormateado = formatTime(elapsedTime);
        JOptionPane.showMessageDialog(frame, 
         messages.getString("dialog.levelCompleted") + "\n" +
            messages.getString("game.movements") + ": " + moveCount + "\n" +
            messages.getString("game.timer") + ": " + tiempoFormateado,
            messages.getString("dialog.congratulations"), JOptionPane.INFORMATION_MESSAGE);
        
        JOptionPane.showMessageDialog(frame, 
            messages.getString("dialog.finish") + "\n",
            "", JOptionPane.INFORMATION_MESSAGE);   

        new SeleccionarNiveles(usuarioActual);
        frame.dispose();
        Musica.getInstance().play(0);
    }
}

