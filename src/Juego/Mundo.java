package Juego;

import User.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class Mundo extends JPanel {
    // dependencias
    User user;
    gameManager game;
    Color color;
    
    // atributos
    JButton[] nivelesBtn;
    Nivel[] niveles;
    int code;
    boolean completado = false;
    int cantNivel = 5;
    
    public Mundo(int code, User user, gameManager game, Color color) {
        this.code = code;
        this.user = user;
        this.game = game;
        this.color = color;
        nivelesBtn = new JButton[cantNivel];
        niveles = new Nivel[cantNivel];
        initNiveles();
    }

    public void cargar () {
        crearBtns();
        setBackground(color);
        JButton salir = new JButton ("regresar");
        salir.addActionListener(e -> {game.select(game.MM.getMapa()); removeAll();});
        add(salir);
    }

    private void initNiveles () {
        for (int i = 0; i < niveles.length; i++){
            niveles[i] = new Nivel((i+1), user, game, this, Color.black);
        }
    }
    
    private void crearBtns () {
        for (int i = 0; i < nivelesBtn.length; i++) {
            nivelesBtn[i] = new JButton ("Nivel " + (i + 1));
            int n = i;
            nivelesBtn[i].addActionListener((ActionEvent e) -> {
                if (niveles[n].isDesBloqueado){
                    System.out.println("Nivel " + (n+1) + " Mundo " + code);
                    niveles[n].completado = true;
                    
                    game.select(niveles[n]);
                    removeAll();  
                }                           
            });
            nivelesBtn[i].setBackground(niveles[i].isComplete() == true? Color.green: Color.LIGHT_GRAY);
            add(nivelesBtn[i]);
        }
    }
    
    public boolean isComplete () {
        for (Nivel nivel : niveles){
            if (nivel.completado == false){
                return false;
            }
        }
        completado = true;
        return true;
    }
    
    public void refresh () { 
        revalidate ();
        repaint();
    }
}
/*

    Inicializar cada nivel
    - inicializarNivel (con la cantNivel)

    Mostrar botones para cada nivel
    - crearBtns (para cada nivel)

    
    
*/
