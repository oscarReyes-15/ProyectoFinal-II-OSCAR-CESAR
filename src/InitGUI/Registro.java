package InitGUI;
import Menu.Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import User.*;

public class Registro extends JFrame implements ActionListener {
    private JTextField usuario, nombreApellido;
    private JPasswordField password;
    private JButton registrarBtn, regresarBtn;
    private JLabel fondo;
    
    public Registro() {
        this.setTitle("Registro de Usuario");
        this.setSize(800, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        JLabel nombreApellidoLabel = new JLabel("Nombre:");
        nombreApellidoLabel.setBounds(250, 100, 150, 25);
        nombreApellidoLabel.setForeground(Color.white);
        this.add(nombreApellidoLabel);
        
        nombreApellido = new JTextField();
        nombreApellido.setBounds(350, 100, 200, 25);
        this.add(nombreApellido);
        
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
        
        registrarBtn = new JButton("Registrar");
        registrarBtn.setBounds(250, 260, 130, 30);
        registrarBtn.addActionListener(this);
        this.add(registrarBtn);
        
        regresarBtn = new JButton("Regresar");
        regresarBtn.setBounds(420, 260, 130, 30);
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);
        
        this.add(fondo);
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registrarBtn) {
            try {
                String nombreApellidoInput = nombreApellido.getText().trim();
                String usuarioInput = usuario.getText().trim();
                String passwordInput = new String(password.getPassword()).trim();
                
                if (nombreApellidoInput.isEmpty() || usuarioInput.isEmpty() || passwordInput.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos");
                    return;
                }
                
                if (UserFile.existeUsuario(usuarioInput)) {
                    JOptionPane.showMessageDialog(this, "El usuario ya existe. Intente con otro nombre.");
                    return;
                }
                
                boolean registroExitoso = UserFile.guardarDatosUsuario(usuarioInput, passwordInput, nombreApellidoInput);
                
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