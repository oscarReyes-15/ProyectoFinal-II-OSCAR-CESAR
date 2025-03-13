package Juego;

import javax.swing.*;
import Objects.*;
import User.User;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
    funciones de juego
        Rendering
        - refresh
        - cargar nivel (carga el nivel en gameManager y corre la funcion run)
        - run (hace el render)
        - spawnObjects (debe de renderizar objetos segun un mapa de enteros (int) )
        - salirNivel (sale del nivel)
        - displayPause
        - reiniciar Juego (nullify el thread y crea uno nuevo, efectivamente reiniciando la partida ( !!! debe reiniciar atributos de juego y jugador !!! ))

        informacion de juego
        - desbloquear nivel
        - ganarNivel (cuando todas las cajas esten en su lugar (ejecuta salir nivel y completar nivel) )
        - completar nivel (set completado com true solo al ganar, no al salir)

*/

public class Nivel extends JPanel implements Runnable {
    // dependencias
    public User user;
    public gameManager game;
    public Mundo mundo;
    
    // atributos del nivel
    int fps = 24;
    boolean isDesBloqueado = true;
    boolean completado = false;
    int moves;
    int stars = 0;
    int code;
    int[] spawnPoint = {0, 16};
    boolean running = false;
    
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
        
    }
    
    public int getSpawnX () {
        return spawnPoint[0];
    }
    
    public int getSpawnY () {
        return spawnPoint[1];
    }
    
    public boolean isComplete() {
        return completado;
    }

    
    public void cargar () {
        if (isDesBloqueado){
            input = new inputHandler();
            player = new jugador (user, input, this);
            
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
    
    public void desbloquearNivel () {
        isDesBloqueado = true;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
             
    }
    

    
    public void updateGame () {
        player.move();
        moves = player.getMoves();
    }
    
    @Override
    public void run() {
        
        setDoubleBuffered(true);

        SwingUtilities.invokeLater(() -> {
            JButton salir = new JButton ("salir");
            salir.addActionListener(e -> {
                nivelThread = null;
                game.select(mundo);
                removeAll();
            });
            add(salir);
            refresh();
        });

        final long OPTIMAL_TIME = 1_000_000_000 / fps; // Time per frame in nanoseconds

        long lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / (double) OPTIMAL_TIME;
            lastTime = now;

            while (delta >= 1) {
                // 1. actualizar informacion de objetos
                updateGame();
                delta--;
            }
            
            // 2. actualizar visual
            repaint();
        }
        
    }
    
    public void render (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.yellow);
        g2.drawImage(player.getImage().getImage(), 0, 0, getWidth(), getHeight(), this);
        player.draw(g2, this);  
    }
    
    public void refresh () {
        revalidate();
        repaint();
    }
}

