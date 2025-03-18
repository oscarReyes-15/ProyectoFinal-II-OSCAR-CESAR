package MenuGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pantallainit extends JFrame implements ActionListener {
    private JButton jugar;
    private JLabel fondo;

    public Pantallainit() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Menu Principal");
        this.setLayout(null);
        
        int anchoVentana = 800;
        int altoVentana = 450;

        ImageIcon imagenFondo = new ImageIcon("src/imagescan/inicioim.png");
        Image imgFondo = imagenFondo.getImage().getScaledInstance(anchoVentana, altoVentana, Image.SCALE_SMOOTH);
        fondo = new JLabel(new ImageIcon(imgFondo));
        fondo.setBounds(0, 0, anchoVentana, altoVentana);

        jugar = new JButton("Jugar");
        jugar.setBounds(320,250,150,30);
        jugar.addActionListener(this);
        this.add(jugar);
        this.setSize(anchoVentana, altoVentana);
        this.setLocationRelativeTo(null);

        this.add(jugar);
        this.add(fondo);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugar) {
            new MainMenu(); 
            this.dispose();
        }
    }



    public static void main(String[] args) {
        new Pantallainit();
    }
}
