import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
    
    private JButton borrarCuentaBtn;
    private JButton salirBtn;
    private String usuarioActual;

    public Menu(String usuario) {
        this.usuarioActual = usuario;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Menu Principal");
        this.setLayout(null);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(177, 37, 7));

        borrarCuentaBtn = new JButton("1. Borrar Cuenta");
        borrarCuentaBtn.setBounds(125, 100, 150, 30);
        borrarCuentaBtn.addActionListener(this);
        this.add(borrarCuentaBtn);

        salirBtn = new JButton("2. Cerrar Sesión");
        salirBtn.setBounds(125, 160, 150, 30);
        salirBtn.addActionListener(this);
        this.add(salirBtn);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == borrarCuentaBtn) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas borrar tu cuenta?", 
                                                             "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                eliminarCuenta();
                JOptionPane.showMessageDialog(this, "Cuenta eliminada con éxito.");
                new MainMenu();
                this.dispose();
            }
        } else if (e.getSource() == salirBtn) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesión?", 
                                                             "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                new MainMenu();
                this.dispose();
            }
        }
    }

    private void eliminarCuenta() {
        if (usuarioActual != null) {
            File carpetaUsuario = new File(usuarioActual);
            if (carpetaUsuario.exists()) {
                eliminarDirectorio(carpetaUsuario);
            }
        }
    }

    private void eliminarDirectorio(File directorio) {
        if (directorio.isDirectory()) {
            for (File archivo : directorio.listFiles()) {
                eliminarDirectorio(archivo);
            }
        }
        directorio.delete();
    }
}
