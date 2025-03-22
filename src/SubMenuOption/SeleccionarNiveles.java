package SubMenuOption;
import Menu.Menu;
import Niveles.*;
import User.UserFile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

public class SeleccionarNiveles extends JFrame implements ActionListener {
    private JButton lvl1btn, lvl2btn, lvl3btn, lvl4btn, lvl5btn, backBtn;
    private String usuarioActual;
    private JLabel fondo, titleLabel;
    private ResourceBundle messages;
    
    public SeleccionarNiveles(String usuario) {
        this.usuarioActual = usuario;
        this.messages = LanguageManager.getMessages();
        
        this.setTitle(messages.getString("title.selectLevel"));
        this.setSize(800, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondoniveles2.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        int buttonWidth = 150;
        int buttonHeight = 80;
        
        lvl1btn = createLevelButton("1", 47, 22, buttonWidth, buttonHeight, true);
        
        boolean level1Completed = UserFile.hasCompletedLevel(usuarioActual, 1);
        boolean level2Completed = UserFile.hasCompletedLevel(usuarioActual, 2);
        boolean level3Completed = UserFile.hasCompletedLevel(usuarioActual, 3);
        boolean level4Completed = UserFile.hasCompletedLevel(usuarioActual, 4);
        
        lvl2btn = createLevelButton("2", 165, 230, buttonWidth, buttonHeight, level1Completed);
        lvl3btn = createLevelButton("3", 415, 270, buttonWidth, buttonHeight, level2Completed);
        lvl4btn = createLevelButton("4", 533, 90, buttonWidth, buttonHeight, level3Completed);
        lvl5btn = createLevelButton("5", 630, 250, buttonWidth, buttonHeight, level4Completed);
        
        backBtn = new JButton(messages.getString("button.back"));
        backBtn.setBounds(20, 380, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);
        
        this.add(fondo);
        
        this.setVisible(true);
    }
    
    private JButton createLevelButton(String level, int x, int y, int width, int height, boolean unlocked) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        ImageIcon iconOriginal = new ImageIcon("src/imagesbtn/" + level + ".png");
        Image imgEscalada = iconOriginal.getImage().getScaledInstance(width - 20, height - 20, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imgEscalada);
        
        button.setIcon(iconoEscalado);
        
        button.setBackground(unlocked ? new Color(6, 0, 53) : new Color(80, 80, 80));
        
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(unlocked ? new Color(148, 116, 231) : Color.GRAY, 3),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
        if (!unlocked) {
            JPanel lockPanel = new JPanel(null);
            lockPanel.setOpaque(false);
            
            JLabel lockLabel = new JLabel("\uD83D\uDD12"); 
            lockLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            lockLabel.setForeground(Color.WHITE);
            lockLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lockLabel.setBounds(0, 0, width, height);
            
            lockPanel.add(lockLabel);
            lockPanel.setBounds(0, 0, width, height);
            button.setLayout(null);
            button.add(lockPanel);
        }
        
        button.addActionListener(this);
        button.setEnabled(unlocked); 
        add(button);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.dispose();
            new Menu(usuarioActual);
        } else if (e.getSource() == lvl1btn) {
            new Nivel1(usuarioActual); 
            this.dispose();
        } else if (e.getSource() == lvl2btn) {
            if (UserFile.hasCompletedLevel(usuarioActual, 1)) {
                new Nivel2(usuarioActual); 
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Debes completar el Nivel 1 primero.", 
                    "Nivel bloqueado", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == lvl3btn) {
            if (UserFile.hasCompletedLevel(usuarioActual, 2)) {
                new Nivel3(usuarioActual); 
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Debes completar el Nivel 2 primero.", 
                    "Nivel bloqueado", 
                    JOptionPane.WARNING_MESSAGE);
            }    
        } else if (e.getSource() == lvl4btn) {
            if (UserFile.hasCompletedLevel(usuarioActual, 3)) {
                new Nivel4(usuarioActual); 
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Debes completar el Nivel 3 primero.", 
                    "Nivel bloqueado", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == lvl5btn) {
            // Check if level 4 is completed
            if (UserFile.hasCompletedLevel(usuarioActual, 4)) {
                new Nivel5(usuarioActual); 
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Debes completar el Nivel 4 primero.", 
                    "Nivel bloqueado", 
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}