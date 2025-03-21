package Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public abstract class Juego extends GameMovement implements ActionListener {
    
    public Juego(String usuario, int nivel) {
        super(usuario, nivel);
    }
    
    protected void setupFrame(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 700);
        
        this.setBounds(50, 50, COLS * CELL_SIZE, ROWS * CELL_SIZE);
        frame.add(this);
        
        levelLabel = new JLabel("Nivel " + currentLevel);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        levelLabel.setBounds(50, 10, 100, 30);
        frame.add(levelLabel);
        
        movesLabel = new JLabel("Movimientos: 0");
        movesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movesLabel.setBounds(200, 10, 150, 30);
        frame.add(movesLabel);
        
        timerLabel = new JLabel("Tiempo: 00:00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setBounds(200, 650, 200, 30);
        frame.add(timerLabel);
        
        resetBtn = new JButton("Reiniciar");
        resetBtn.setBounds(400, 10, 100, 30);
        resetBtn.addActionListener(this);
        frame.add(resetBtn);
        
        menuBtn = new JButton("Menú");
        menuBtn.setBounds(520, 10, 100, 30);
        menuBtn.addActionListener(this);
        frame.add(menuBtn);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopTimer();
                super.windowClosing(e);
            }
        });
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.requestFocus();
    }
    
    protected void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("src/images/fondogame.png"));
            wallImage = ImageIO.read(new File("src/images/muro1.png"));
            boxImage = ImageIO.read(new File("src/images/caja.png"));
            playerImage = ImageIO.read(new File("src/images/avatar1.png"));
        } catch (IOException e) {
            System.out.println("Error loading game images: " + e.getMessage());
            this.setBackground(new Color(233, 149, 65));
        }
    }
    
    @Override
    protected void resetLevel() {
        stopTimer();
        elapsedTime = 0;
        startTimer();
        
        initializeLevel();
        moveCount = 0;
        movesLabel.setText("Movimientos: " + moveCount);
        gameCompleted = false;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, COLS * CELL_SIZE, ROWS * CELL_SIZE, this);
        }
        
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= COLS; x++) {
            g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, ROWS * CELL_SIZE);
        }
        
        for (int y = 0; y <= ROWS; y++) {
            g.drawLine(0, y * CELL_SIZE, COLS * CELL_SIZE, y * CELL_SIZE);
        }
        
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                int cellX = x * CELL_SIZE;
                int cellY = y * CELL_SIZE;
                
                switch (board[x][y]) {
                    case WALL:
                        if (wallImage != null) {
                            g.drawImage(wallImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(100, 100, 100));
                            g.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
                        }
                        break;
                    case BOX:
                        if (boxImage != null) {
                            g.drawImage(boxImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(150, 75, 0));
                            g.fillRect(cellX + 5, cellY + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                        }
                        break;
                    case TARGET:
                        if (targetImage != null) {
                            g.drawImage(targetImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(200, 0, 0));
                            g.fillOval(cellX + 15, cellY + 15, CELL_SIZE - 30, CELL_SIZE - 30);
                        }
                        break;
                    case PLAYER:
                        if (playerImage != null) {
                            g.drawImage(playerImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(0, 0, 200));
                            g.fillOval(cellX + 10, cellY + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                        }
                        break;
                    case BOX_ON_TARGET:
                        if (targetImage != null) {
                            g.drawImage(targetImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(200, 0, 0));
                            g.fillOval(cellX + 15, cellY + 15, CELL_SIZE - 30, CELL_SIZE - 30);
                        }
                        
                        if (boxImage != null) {
                            g.drawImage(boxImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(150, 75, 0));
                            g.fillRect(cellX + 10, cellY + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                        }
                        break;
                    case PLAYER_ON_TARGET:
                        if (targetImage != null) {
                            g.drawImage(targetImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(200, 0, 0));
                            g.fillOval(cellX + 15, cellY + 15, CELL_SIZE - 30, CELL_SIZE - 30);
                        }
                        
                        if (playerImage != null) {
                            g.drawImage(playerImage, cellX, cellY, CELL_SIZE, CELL_SIZE, this);
                        } else {
                            g.setColor(new Color(0, 0, 200));
                            g.fillOval(cellX + 10, cellY + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                        }
                        break;
                }
            }
        }
        
        if (gameCompleted) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, COLS * CELL_SIZE, ROWS * CELL_SIZE);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String message = "¡Nivel Completado!";
            FontMetrics fm = g.getFontMetrics();
            int messageWidth = fm.stringWidth(message);
            int messageX = (COLS * CELL_SIZE - messageWidth) / 2;
            g.drawString(message, messageX, ROWS * CELL_SIZE / 2);
            
            // Stop the timer when game is completed
            stopTimer();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetBtn) {
            resetLevel();
            this.requestFocus();
        } else if (e.getSource() == menuBtn) {
            int option = JOptionPane.showConfirmDialog(
                frame,
                "¿Desea salir del nivel y volver al menú?",
                "Volver al menú",
                JOptionPane.YES_NO_OPTION);
                
            if (option == JOptionPane.YES_OPTION) {
                stopTimer();
                frame.dispose();
                if (usuarioActual != null) {
                    new InitGUI.Menu(usuarioActual);
                }
            } else {
                this.requestFocus(); 
            }
        }
    }
}