package MenuGUI;

import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author danilos
 */
public class Ajustes extends JFrame implements ActionListener, ChangeListener {
    
    private JButton volverBtn, idiomaBtn, controlesBtn;
    private JSlider volumenSlider;
    private JLabel fondo, volumenLabel;
    private String usuarioActual;
    
    public Ajustes(String usuario) {
        this.usuarioActual = usuario;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ajustes");
        this.setLayout(null);
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        
        // Cargar imagen de fondo (misma que en Menu)
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
 
        // Botón de Idioma
        idiomaBtn = new JButton("Idioma");
        idiomaBtn.setBounds(325, 120, 150, 30);
        idiomaBtn.addActionListener(this);
        this.add(idiomaBtn);
        
        // Control de Volumen con Slider
        volumenLabel = new JLabel("Volumen:");
        volumenLabel.setBounds(325, 170, 150, 30);
        volumenLabel.setForeground(java.awt.Color.WHITE);
        this.add(volumenLabel);
        
        volumenSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumenSlider.setBounds(325, 200, 150, 50);
        volumenSlider.setMajorTickSpacing(25);
        volumenSlider.setMinorTickSpacing(5);
        volumenSlider.setPaintTicks(true);
        volumenSlider.setPaintLabels(true);
        volumenSlider.setOpaque(false);
        volumenSlider.addChangeListener(this);
        this.add(volumenSlider);
        
        // Botón de Controles
        controlesBtn = new JButton("Controles");
        controlesBtn.setBounds(325, 270, 150, 30);
        controlesBtn.addActionListener(this);
        this.add(controlesBtn);
        
        // Botón para volver al menú principal
        volverBtn = new JButton("Volver");
        volverBtn.setBounds(325, 350, 150, 30);
        volverBtn.addActionListener(this);
        this.add(volverBtn);
        
        this.add(fondo);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == idiomaBtn) {
            String[] opciones = {"Español", "English"};
            String seleccion = (String) JOptionPane.showInputDialog(
                    this,
                    "Seleccione un idioma:",
                    "Cambiar Idioma",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);
            
            if (seleccion != null) {
                JOptionPane.showMessageDialog(this, "Idioma cambiado a: " + seleccion);
                // Aquí implementarías la lógica para cambiar el idioma
            }
        } else if (e.getSource() == controlesBtn) {
            JOptionPane.showMessageDialog(this,
                    "Controles del juego:\n\n" +
                    "- W / Flecha arriba: Mover arriba\n" +
                    "- S / Flecha abajo: Mover abajo\n" +
                    "- A / Flecha izquierda: Mover izquierda\n" +
                    "- D / Flecha derecha: Mover derecha\n" +
                    "" +
                    "",
                    "", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == volverBtn) {
            new Menu(usuarioActual);
            this.dispose();
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == volumenSlider) {
            int valor = volumenSlider.getValue();
            volumenLabel.setText("Volumen: " + valor + "%");
            // Aquí implementarías la lógica para ajustar el volumen del juego
        }
    }
}