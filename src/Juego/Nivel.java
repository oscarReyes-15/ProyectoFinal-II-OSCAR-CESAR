package Juego;

import javax.swing.*;
import Objects.*;
import User.User;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Nivel extends JPanel implements Runnable {
    public User user;
    public gameManager game;
    public Mundo mundo;
    
    // atributos del nivel
    int fps = 30;
    boolean isDesBloqueado = true;
    boolean completado = false;
    int moves;
    int stars = 0;
    int code;
    int[] spawnPoint = {0, 16};
    boolean running = false;
    
    private NivelMap nivelMap;
    private int tileSize = 32; 
    
    // objetos de nivel
    jugador player;
    inputHandler input;
    Thread nivelThread;
    JLabel pop;
    
    public Nivel(int code, User user, gameManager game, Mundo mundo, Color color) {
        this.code = code;
        this.user = user;
        this.game = game;
        this.mundo = mundo;
        setBackground(color);
        
        nivelMap = new NivelMap(code);
    }
    
    public int getSpawnX() {
        return nivelMap.getPlayerX();
    }
    
    public int getSpawnY() {
        return nivelMap.getPlayerY();
    }
    
    public boolean isComplete() {
        return completado;
    }
    
    public void cargar() {
        if (isDesBloqueado) {
            input = new inputHandler();
            player = new jugador(user, input, this);
            
            moves = player.getMoves();
            
            pop = new JLabel(player.getImage());
            
            this.addKeyListener((KeyListener) input);
            this.setFocusable(true);
            this.requestFocus();

            running = true;
            nivelThread = new Thread(this);
            nivelThread.start();
        } 
    }
    
    public void desbloquearNivel() {
        isDesBloqueado = true;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
    
    public void updateGame() {
        // Check for player movement
        if (input.upPressed) {
            if (nivelMap.movePlayer(0, -1)) {
                player.incrementMoves();
            }
            input.upPressed = false;
        }
        if (input.downPressed) {
            if (nivelMap.movePlayer(0, 1)) {
                player.incrementMoves();
            }
            input.downPressed = false;
        }
        if (input.leftPressed) {
            if (nivelMap.movePlayer(-1, 0)) {
                player.incrementMoves();
            }
            input.leftPressed = false;
        }
        if (input.rightPressed) {
            if (nivelMap.movePlayer(1, 0)) {
                player.incrementMoves();
            }
            input.rightPressed = false;
        }
        
        // Check if level is complete
        if (nivelMap.isLevelComplete()) {
            ganarNivel();
        }
        
        moves = player.getMoves();
    }
    
    public void ganarNivel() {
        completado = true;
        running = false;
        JOptionPane.showMessageDialog(this, "¡Nivel completado!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        salirNivel();
    }
    
    public void salirNivel() {
        nivelThread = null;
        game.select(mundo);
        removeAll();
    }
    
    @Override
    public void run() {
        setDoubleBuffered(true);

        SwingUtilities.invokeLater(() -> {
            JButton salir = new JButton("Salir");
            salir.addActionListener(e -> {
                nivelThread = null;
                game.select(mundo);
                removeAll();
            });
            add(salir);
            refresh();
        });

        while (running) {
            // 1. actualizar informacion de objetos
            updateGame();

            // 2. actualizar visual
            repaint();
            
            try {
                // 3. ESPERA - WAIT
                Thread.sleep(1000 / fps);
            } catch (InterruptedException ex) {
                Logger.getLogger(Nivel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw level map
        nivelMap.draw(g2, tileSize);
        
        // Note: you might not need to draw the player separately since it's included in the map
        // But if you want custom player rendering, you can do it here
        // player.draw(g2, this);
    }
    
    public void refresh() {
        revalidate();
        repaint();
    }
}