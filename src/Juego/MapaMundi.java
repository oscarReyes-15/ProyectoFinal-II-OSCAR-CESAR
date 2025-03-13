/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import MenuGUI.MainMenu;
import User.User;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

/*
    Inicializar cada mundo
    - inicializarMundos (con la cantMundos)

    Mostrar botones para cada mundo
    - crearBtns (para cada mundo)

    

*/

public final class MapaMundi {
    // atributos
    Mundo[] mundos;
    JPanel mapaMundi;
    int cantMundos = 2;
    JButton[] btnMnds;
    ImageIcon bg;
    
    // dependencias 
    User user;
    gameManager game;
    
    // Constructor

    public MapaMundi(User user, gameManager game) {
        //dependcias
        this.user = user;
        this.game = game;
        
        // atributos
        mundos = new Mundo[cantMundos];
        btnMnds = new JButton[mundos.length];
        mapaMundi = new JPanel ();
        
        //
        bg = new ImageIcon ("C:\\Users\\LENOVO\\OneDrive - Universidad Tecnologica Centroamericana\\Trimestre #3\\Programacion II\\GarbageTests\\SodokanV1\\src\\Images\\avatar4.png");
        
        //
        initMundos();
        crearBtns();
        addMnds();
    }
    
    private void initMundos () {
        for (int i = 0; i < mundos.length; i++){
            mundos[i] = new Mundo((i+1), user, game, Color.black);
        }
    }
    
    private void crearBtns () {
        for (int i = 0; i < btnMnds.length; i++){
            btnMnds[i] = new JButton("Mundo " + (i+1));
            
            btnMnds[i].setBackground(mundos[i].completado == true? Color.GREEN : Color.GRAY);
            
            int n = i;
            btnMnds[i].addActionListener(e -> {
                game.select(mundos[n]);
                mapaMundi.removeAll();
            });
        }
    }
    
    private void addMnds () {
        mapaMundi.removeAll();
        for (JButton btn : btnMnds) {
            mapaMundi.add(btn);
        }
        JButton salir = new JButton ("salir");
        salir.addActionListener(e -> {new MainMenu(); game.f.dispose();});
        mapaMundi.add(salir);
    }
    
    public void refresh () {     
        for (int i = 0; i < mundos.length; i++) {
            if (mundos[i].isComplete()){
                btnMnds[i].setBackground(Color.green);
            }
        }    
        mapaMundi.revalidate();
        mapaMundi.repaint();
    }
    
    public JPanel getMapa () {        
        addMnds();
        return mapaMundi;
    }
    
}

