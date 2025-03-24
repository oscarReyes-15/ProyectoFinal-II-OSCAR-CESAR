package Niveles;

import Audio.*;
import Juego.*;
import SubMenuOption.*;
import javax.swing.*;
import User.*;
import java.util.ResourceBundle;

public class Nivel1 extends Juego {
    private ResourceBundle messages;
    
    public Nivel1() {
        this(null);
    }
    
    public Nivel1(String usuario) {
        super(usuario, 1);
        
        messages = LanguageManager.getMessages();
        
        loadImages();
        setupFrame("Sokoban - " + messages.getString("level.1"));
        
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
        
        player.X = 5;
        player.Y = 5;
        board[player.X][player.Y] = PLAYER;
        
        moveCount = 0;
        movesLabel.setText(messages.getString("game.movements") + ": " + moveCount);
        
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
            if (puntosPorMovimientos < 100) puntosPorMovimientos = 100;
            
            GameHistory.registrarPartida(usuarioActual, 1, elapsedTime / 1000, true);
            UserFile.actualizarPuntos(usuarioActual, puntosPorMovimientos);
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