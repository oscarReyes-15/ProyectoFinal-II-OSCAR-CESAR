package Niveles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import User.*;
import java.util.Locale;

public class Nivel1 extends JPanel implements ActionListener {
    
    private static final int CELL_SIZE = 60;
    private static final int ROWS = 10;
    private static final int COLS = 10;
    
    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int BOX = 2;
    private static final int TARGET = 3;
    private static final int PLAYER = 4;
    private static final int BOX_ON_TARGET = 5;
    private static final int PLAYER_ON_TARGET = 6;
    
    private int[][] board = new int[COLS][ROWS];
    private int playerX, playerY;
    private int moveCount = 0;
    private boolean gameCompleted = false;
    
    private JFrame frame;
    private JButton resetBtn;
    private JButton menuBtn; // New button to return to menu
    private JLabel movesLabel;
    private JLabel levelLabel;
    private Image backgroundImage;
    private Image wallImage;
    private Image boxImage;
    private Image targetImage;
    private Image playerImage;
    private String usuarioActual; // Store current user
    
    public Nivel1() {
        this(null);
    }
    
    public Nivel1(String usuario) {
        this.usuarioActual = usuario;
        this.setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        
        try {
            backgroundImage = ImageIO.read(new File("src/images/fondogame.png"));
        } catch (IOException e) {
            System.out.println("Error loading background image: " + e.getMessage());
            this.setBackground(new Color(233, 149, 65));
        }
        
        loadImages();
        
        frame = new JFrame("Sokoban - Nivel 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 700);
        
        this.setBounds(50, 50, COLS * CELL_SIZE, ROWS * CELL_SIZE);
        frame.add(this);
        
        levelLabel = new JLabel("Nivel 1");
        levelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        levelLabel.setBounds(50, 10, 100, 30);
        frame.add(levelLabel);
        
        movesLabel = new JLabel("Movimientos: 0");
        movesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movesLabel.setBounds(200, 10, 150, 30);
        frame.add(movesLabel);
        
        resetBtn = new JButton("Reiniciar");
        resetBtn.setBounds(400, 10, 100, 30);
        resetBtn.addActionListener(this);
        frame.add(resetBtn);
        
        // Add menu button
        menuBtn = new JButton("Menú");
        menuBtn.setBounds(520, 10, 100, 30);
        menuBtn.addActionListener(this);
        frame.add(menuBtn);
        UserFile.incrementarPartidasJugadas(usuarioActual);
        initializeLevel();
        
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameCompleted) return;
                
                int key = e.getKeyCode();
                
                switch (key) {
                    
                    case KeyEvent.VK_UP:
                        movePlayer(0, -1);
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayer(0, 1);
                        break;
                    case KeyEvent.VK_LEFT:
                        movePlayer(-1, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayer(1, 0);
                        break;
                     case KeyEvent.VK_W:
                        movePlayer(0, -1);
                        break;
                    case KeyEvent.VK_S:
                        movePlayer(0, 1);
                        break;
                    case KeyEvent.VK_A:
                        movePlayer(-1, 0);
                        break;
                    case KeyEvent.VK_D:
                        movePlayer(1, 0);
                        break;
                    case KeyEvent.VK_R:
                        resetLevel();
                        break;
                }
                
                repaint();
                checkWinCondition();
            }
        });
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.requestFocus();
    }
    
    private void loadImages() {
        try {
            wallImage = ImageIO.read(new File("src/images/muro1.png"));
            boxImage = ImageIO.read(new File("src/images/caja.png"));
            playerImage = ImageIO.read(new File("src/images/avatar1.png"));
        } catch (IOException e) {
            System.out.println("Error loading game images: " + e.getMessage());
        }
    }
    
    private void initializeLevel() {
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
        
        playerX = 5;
        playerY = 5;
        board[playerX][playerY] = PLAYER;
        
        moveCount = 0;
        movesLabel.setText("Movimientos: " + moveCount);
        
        gameCompleted = false;
    }
    
    private void resetLevel() {
        initializeLevel();
        repaint();
    }
    
    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;
        
        if (newX < 0 || newX >= COLS || newY < 0 || newY >= ROWS) {
            return;
        }
        
        int targetPos = board[newX][newY];
        
        if (targetPos == WALL) {
            return;
        }
        
        if (targetPos == BOX || targetPos == BOX_ON_TARGET) {
            int nextX = newX + dx;
            int nextY = newY + dy;
            
            if (nextX < 0 || nextX >= COLS || nextY < 0 || nextY >= ROWS) {
                return;
            }
            
            int nextPos = board[nextX][nextY];
            
            if (nextPos == EMPTY || nextPos == TARGET) {
                board[nextX][nextY] = (nextPos == TARGET) ? BOX_ON_TARGET : BOX;
                
                board[newX][newY] = (targetPos == BOX_ON_TARGET) ? TARGET : EMPTY;
                
                board[playerX][playerY] = (board[playerX][playerY] == PLAYER_ON_TARGET) ? TARGET : EMPTY;
                playerX = newX;
                playerY = newY;
                board[playerX][playerY] = (targetPos == BOX_ON_TARGET) ? PLAYER_ON_TARGET : PLAYER;
                
                moveCount++;
                movesLabel.setText("Movimientos: " + moveCount);
            }
        } else {
            board[playerX][playerY] = (board[playerX][playerY] == PLAYER_ON_TARGET) ? TARGET : EMPTY;
            playerX = newX;
            playerY = newY;
            board[playerX][playerY] = (targetPos == TARGET) ? PLAYER_ON_TARGET : PLAYER;
            
            moveCount++;
            movesLabel.setText("Movimientos: " + moveCount);
        }
    }
    
    private void checkWinCondition() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (board[x][y] == BOX) {
                    return;
                }
            }
        }
        
        gameCompleted = true;
        
        // Update user statistics
        if (usuarioActual != null) {
            // Update completed level
            UserFile.setNivelCompletado(usuarioActual, 1);
            
            
            // Add points based on moves (less moves = more points)
            int puntos = 1000 - (moveCount * 5);
            if (puntos < 100) puntos = 100; // Minimum points
            UserFile.actualizarPuntos(usuarioActual, puntos);
        }
        
        JOptionPane.showMessageDialog(frame, "¡Nivel completado!\nMovimientos: " + moveCount, 
                                     "Felicitaciones", JOptionPane.INFORMATION_MESSAGE);
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
                frame.dispose();
                if (usuarioActual != null) {
                    new MenuGUI.Menu(usuarioActual);
                } 
                
            } else {
                this.requestFocus(); // Return focus to game panel
            }
        }
    }

  }
