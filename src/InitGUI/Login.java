package InitGUI;

import Menu.MainMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;

public class Login extends JFrame implements ActionListener {
    private JTextField usuario;
    private JPasswordField password;
    private JButton iniciarSesionBtn, regresarMenuBtn;
    private JLabel fondo;
    
    public Login() {
        this.setTitle("Inicio de Sesión");
        this.setSize(800, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setBounds(250, 150, 100, 25);
        usuarioLabel.setForeground(Color.white);
        this.add(usuarioLabel);
        
        usuario = new JTextField();
        usuario.setBounds(350, 150, 200, 25);
        this.add(usuario);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(250, 200, 100, 25);
        passwordLabel.setForeground(Color.white);
        this.add(passwordLabel);
        
        password = new JPasswordField();
        password.setBounds(350, 200, 200, 25);
        this.add(password);
        
        iniciarSesionBtn = new JButton("Iniciar Sesión");
        iniciarSesionBtn.setBounds(250, 260, 130, 30);
        iniciarSesionBtn.addActionListener(this);
        this.add(iniciarSesionBtn);
        
        regresarMenuBtn = new JButton("Regresar");
        regresarMenuBtn.setBounds(420, 260, 130, 30);
        regresarMenuBtn.addActionListener(this);
        this.add(regresarMenuBtn);
        
        this.add(fondo);
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciarSesionBtn) {
            String usuarioInput = usuario.getText().trim().toLowerCase(); 
            String passwordInput = new String(password.getPassword()).trim();
            
            if (usuarioInput.isEmpty() || passwordInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese usuario y contraseña");
                return;
            }
            
            if (UserFile.verificarCredenciales(usuarioInput, passwordInput)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
                new Menu(usuarioInput);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        } else if (e.getSource() == regresarMenuBtn) {
            new MainMenu();
            this.dispose();
        } 
    }
}