package Niveles;

import Audio.Musica;
import Audio.Sonidos;
import Juego.Juego;
import SubMenuOption.SeleccionarNiveles;
import javax.swing.*;
import User.*;

public class Nivel2 extends Juego {
    

    public Nivel2() {
        this(null);
    }
    
    public Nivel2(String usuario) {
        super(usuario, 2);
        
        loadImages();
        setupFrame("Sokoban - " + messages.getString("level.2"));
        
        if (usuarioActual != null) {
            UserFile.incrementarPartidasJugadas(usuarioActual);
        }
        
        initializeLevel();
        this.requestFocus();
    }
    
    @Override
    protected void initializeLevel() {
        
        for (int x = 0; x < COLS ; x++) {
            for (int y = 0; y < ROWS ; y++) {
                board[x][y] = WALL  ;
            }
        }
        
        board[1][1] = TARGET;
        board[2][1] = EMPTY;
        board[3][1] = EMPTY;
        board[1][2] = BOX;
        board[2][2] = BOX;
        board[1][3] = EMPTY;
        board[2][3] = EMPTY;
        board[3][3] = EMPTY;
        board[1][5] = EMPTY;
        board[2][5] = EMPTY;
        board[1][4] = EMPTY;
        board[2][4] = BOX;
        board[3][4] = EMPTY;
        board[1][6] = EMPTY;
        board[1][7] = TARGET;
        board[2][7] = EMPTY;
        board[1][8] = TARGET;
        board[2][8] = EMPTY;
        
        player.X = 2;
        player.Y = 1;
        board[player.X][player.Y] = PLAYER;
        
        
        moveCount = 0;
        movesLabel.setText(messages.getString("game.movements") + ": " + moveCount);
        
        gameCompleted = false;
        Musica.getInstance().audioStop();
        Musica.getInstance().play(3);
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
            UserFile.setNivelCompletado(usuarioActual, 2);
            
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