package Menu;

import User.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class Ranking extends JFrame implements ActionListener {
    private JButton salirbtn;
    private JLabel fondo, titleLabel;
    private JTextArea rankingArea;
    private JScrollPane scrollPane;
    private String usuarioActual;
    private JPanel rankingPanel;
    
    public Ranking(String usuario) {
        this.usuarioActual = usuario;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ranking de Jugadores");
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        rankingPanel = new JPanel();
        rankingPanel.setLayout(null);
        rankingPanel.setBounds(150, 30, 500, 280);
        rankingPanel.setBackground(new Color(0, 20, 60, 200));
        rankingPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        this.add(rankingPanel);
        
        titleLabel = new JLabel("RANKING DE JUGADORES");
        titleLabel.setBounds(100, 10, 300, 30);
        titleLabel.setForeground(new Color(255, 255, 200));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rankingPanel.add(titleLabel);
        
        // Make sure the text area is completely non-editable
        rankingArea = new JTextArea();
        rankingArea.setEditable(false);
        rankingArea.setFont(new Font("Monospace", Font.PLAIN, 14)); // Using monospace font for better alignment
        rankingArea.setForeground(Color.WHITE);
        rankingArea.setBackground(new Color(0, 40, 80, 200)); // Increased opacity for better readability
        rankingArea.setMargin(new Insets(10, 10, 10, 10));
        rankingArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Use default cursor instead of text cursor
        rankingArea.setFocusable(false); // Prevents focus and selection
        
        scrollPane = new JScrollPane(rankingArea);
        scrollPane.setBounds(50, 50, 400, 180);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 1));
        rankingPanel.add(scrollPane);
        
        displayRanking();
        
        salirbtn = new JButton("Volver");
        salirbtn.setBounds(325, 340, 150, 40);      
        salirbtn.addActionListener(this);
        this.add(salirbtn);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui1.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        this.add(fondo);
        
        this.setVisible(true);
    }
   
    private void displayRanking() {
        ArrayList<UserScore> userScores = getAllUserScores();
        
        // Ensure consistent sorting by points (primary) and level (secondary)
        Collections.sort(userScores, Collections.reverseOrder());
        
        StringBuilder sb = new StringBuilder();
        
        if (userScores.isEmpty()) {
            sb.append("No hay jugadores registrados.");
        } else {
            // Improved spacing for better alignment
            sb.append(String.format("%-4s %-20s %-10s %-10s %-15s\n", 
                      "Pos", "Usuario", "Puntos", "Nivel", "Tiempo Total"));
            sb.append("----------------------------------------------------------\n");
            
            int position = 1;
            for (UserScore score : userScores) {
                String userName = score.username;
                String highlight = "";
                
                // Destacar usuario actual
                if (userName.equals(usuarioActual)) {
                    highlight = " ➤"; // Usar un indicador más visible
                }
                
                String formattedLine = String.format("%-4d %-20s %-10d %-10d %-15s%s\n", 
                      position, userName, score.points, score.maxLevel,
                      UserFile.getTiempoFormateado(score.username), highlight);
                
                if (position <= 3) {
                    sb.append(formattedLine);
                } else {
                    sb.append(formattedLine);
                }
                position++;
            }
        }
        
        rankingArea.setText(sb.toString());
    }
    
    private ArrayList<UserScore> getAllUserScores() {
        ArrayList<UserScore> userScores = new ArrayList<>();
        
        File currentDir = new File(".");
        File[] directories = currentDir.listFiles(File::isDirectory);
        
        if (directories != null) {
            for (File dir : directories) {
                String username = dir.getName();
                File dataFile = new File(username + "/datos.bin");
                
                if (dataFile.exists()) {
                    User user = UserFile.cargarUsuario(username);
                    if (user != null) {
                        int maxLevel = UserFile.getNivelMaximo(username);
                        userScores.add(new UserScore(username, user.getPuntos(), maxLevel));
                    }
                }
            }
        }
        
        return userScores;
    }
    
    private static class UserScore implements Comparable<UserScore> {
        String username;
        int points;
        int maxLevel;
        
        public UserScore(String username, int points, int maxLevel) {
            this.username = username;
            this.points = points;
            this.maxLevel = maxLevel;
        }
        
        @Override
        public int compareTo(UserScore other) {
            // Primary sort by points
            int result = Integer.compare(this.points, other.points);
            if (result == 0) {
                // Secondary sort by level if points are equal
                result = Integer.compare(this.maxLevel, other.maxLevel);
            }
            
            return result;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salirbtn) {
            new Menu(usuarioActual);
            this.dispose();
        }
    }
}