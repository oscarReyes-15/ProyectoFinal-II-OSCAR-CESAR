
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private JButton loginbtn;
    private JButton crearcuentabtn;
    private JButton salirbtn;

    
    public MainMenu() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Prueba");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(177, 37, 7));

        loginbtn = new JButton("1. Login");
        loginbtn.setBounds(125, 50, 150, 40);
        loginbtn.addActionListener(this);
        this.add(loginbtn);

        crearcuentabtn = new JButton("2. Crear Cuenta");
        crearcuentabtn.setBounds(125, 110, 150, 40);
        crearcuentabtn.addActionListener(this);
        this.add(crearcuentabtn);

        salirbtn = new JButton("3. Salir");
        salirbtn.setBounds(125, 170, 150, 40);
        salirbtn.addActionListener(this);
        this.add(salirbtn);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginbtn) {
            new Login();
            this.dispose();
        } else if (e.getSource() == crearcuentabtn) {
            new Registro();
            this.dispose();
        } else if (e.getSource() == salirbtn) {
            JOptionPane.showMessageDialog(null, "Saliendo");
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        new MainMenu();
        
    }
}
