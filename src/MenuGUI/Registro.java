package MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import User.*;

public class Registro extends JFrame implements ActionListener {
    private JTextField usuario;
    private JPasswordField password;
    private JButton registrarBtn, regresarBtn;
    
    public Registro() {
        this.setTitle("Registro de Usuario");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        
        registrarBtn = new JButton("Registrar");
        registrarBtn.setBounds(50, 160, 130, 30);
        registrarBtn.addActionListener(this);
        this.add(registrarBtn);
        
        regresarBtn = new JButton("Regresar");
        regresarBtn.setBounds(200, 160, 130, 30);
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);
        
        this.setVisible(true);
    }
    
  @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == registrarBtn) {
        try {
            String usuarioInput = usuario.getText().trim();
            String passwordInput = new String(password.getPassword()).trim();
            
            if (usuarioInput.isEmpty() || passwordInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese usuario y contraseña");
                return;
            }
            
            if (UserFile.existeUsuario(usuarioInput)) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe. Intente con otro nombre.");
                return;
            }
            
            boolean registroExitoso = UserFile.guardarDatosUsuario(usuarioInput, passwordInput);
            
            if (registroExitoso) {
                JOptionPane.showMessageDialog(this, "Cuenta creada exitosamente");
                try {
                    Menu menuPrincipal = new Menu(usuarioInput);
                    menuPrincipal.setVisible(true);
                    this.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error al abrir el menú: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Error al abrir el menú: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear la cuenta");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error durante el registro: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error durante el registro: " + ex.getMessage());
            ex.printStackTrace();
        }
    } else if (e.getSource() == regresarBtn) {
          new MainMenu();
           
            this.dispose();
           }
     }
}