/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MenuGUI;

import Sounds.Sonidos;
import java.awt.Graphics;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author LENOVO
 */
public class waitArea extends JWindow{
    private Sonidos sonido;
    private Image imgFondo;
    
    public waitArea(){
    this.setLayout(null);
        
        int anchoVentana = 800;
        int altoVentana = 450;

        ImageIcon imagenFondo = new ImageIcon("src/imagescan/inicioim.png");
        imgFondo = imagenFondo.getImage().getScaledInstance(anchoVentana, altoVentana, Image.SCALE_SMOOTH);

      
        this.setSize(anchoVentana, altoVentana);
        this.setLocationRelativeTo(null);
        
        sonido = new Sonidos();
        sonido.setSound(2);
        sonido.Loop();
        
        this.setVisible(true);
        
        try {
            Thread.sleep(9000);
        } catch (InterruptedException ex) {
            Logger.getLogger(waitArea.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Pantallainit(sonido);
        this.dispose();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(imgFondo, 0, 0, getWidth(), getHeight(), this);
    }
 
    
    
}
