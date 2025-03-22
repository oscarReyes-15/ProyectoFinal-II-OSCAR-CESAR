package Menu;

import User.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class Ranking extends JFrame implements ActionListener {
    private JButton salirbtn;
    private JLabel fondo;
    private JTextArea rankingArea;
    private JScrollPane scrollPane;
    private String usuarioActual;
    
    public Ranking(String usuario) {
        this.usuarioActual = usuario;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ranking de Jugadores");
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        rankingArea = new JTextArea();
        rankingArea.setEditable(false);
        rankingArea.setFont(new Font("Arial", Font.BOLD, 14));
        
        scrollPane = new JScrollPane(rankingArea);
        scrollPane.setBounds(200, 50, 400, 200);
        this.add(scrollPane);
        
        displayRanking();
        
        salirbtn = new JButton("Volver");
        salirbtn.setBounds(325, 270, 150, 40);
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
        
        Collections.sort(userScores, Collections.reverseOrder());
        
        StringBuilder sb = new StringBuilder();
        sb.append("===== RANKING DE JUGADORES =====\n\n");
        
        if (userScores.isEmpty()) {
            sb.append("No hay jugadores registrados.");
        } else {
            sb.append(String.format("%-5s %-20s %-10s %-10s %-15s\n", 
                      "Pos", "Usuario", "Puntos", "Nivel", "Tiempo Total"));
            sb.append("--------------------------------------------------\n");
            
            int position = 1;
            for (UserScore score : userScores) {
                String userName = score.username;
                if (userName.equals(usuarioActual)) {
                    userName = userName + " *";
                }
                
                sb.append(String.format("%-5d %-20s %-10d %-10d %-15s\n", 
                          position, userName, score.points, score.maxLevel,
                          UserFile.getTiempoFormateado(score.username)));
                position++;
            }
            
            sb.append("\n* Usuario actual");
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
                        int[] stats = UserFile.cargarEstadisticasUsuario(username);
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
            int result = Integer.compare(this.points, other.points);
            if (result == 0) {
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