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
    // dependencias
    User user;
    gameManager game;
    Mundo mundo;
    
    // atributos del nivel
    int fps = 15;
    boolean isDesBloqueado = true;
    boolean completado = false;
    int moves;
    int stars = 0;
    int code;
    int[] spawnPoint = {1, 20};
    
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
        
        Graphics2D g2 = (Graphics2D) g;
        
        player.draw(g2, this);       
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
        
        
         update(getGraphics());
        while (nivelThread != null){
            try {
                // 1. actualizar informacion de objetos
                updateGame();
                System.out.println("rendering");
                // 2. actualizar visual
                update(getGraphics());

            
                // 3. ESPERA - WAIT
                Thread.sleep(1000 / fps);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Nivel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    public void refresh () {
        revalidate();
        repaint();
    }
}

/*
    funciones de juego
    - refresh
    - desbloquear nivel
    - run (hace el render)
    - cargar nivel (carga el nivel en gameManager y corre la funcion run)
    - spawnObjects (debe de renderizar objetos segun un mapa de enteros (int) )
    - displayPause
    - reiniciar Juego (nullify el thread y crea uno nuevo, efectivamente reiniciando la partida ( !!! debe reiniciar atributos de juego y jugador !!! ))
    - salirNivel (sale del nivel)
    - ganarNivel (cuando todas las cajas esten en su lugar (ejecuta salir nivel) )

*/