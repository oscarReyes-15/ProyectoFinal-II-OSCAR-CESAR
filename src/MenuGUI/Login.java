package MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;

public class Login extends JFrame implements ActionListener {
    private JTextField usuario;
    private JPasswordField password;
    private JButton iniciarSesionBtn, regresarMenuBtn;
    
    public Login() {
        this.setTitle("Inicio de Sesión");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(177, 37, 7));
        
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setBounds(50, 50, 100, 25);
        usuarioLabel.setForeground(Color.white);
        this.add(usuarioLabel);
        
        usuario = new JTextField();
        usuario.setBounds(150, 50, 150, 25);
        this.add(usuario);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(50, 100, 100, 25);
        passwordLabel.setForeground(Color.white);
        this.add(passwordLabel);
        
        password = new JPasswordField();
        password.setBounds(150, 100, 150, 25);
        this.add(password);
        
        iniciarSesionBtn = new JButton("Iniciar Sesión");
        iniciarSesionBtn.setBounds(50, 160, 130, 30);
        iniciarSesionBtn.addActionListener(this);
        this.add(iniciarSesionBtn);
        
        regresarMenuBtn = new JButton("Regresar");
        regresarMenuBtn.setBounds(200, 160, 100, 30);
        regresarMenuBtn.addActionListener(this);
        this.add(regresarMenuBtn);
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciarSesionBtn) {
            String usuarioInput = usuario.getText().trim();
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