/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import Objects.jugador;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author LENOVO
 */
public class inputHandler implements KeyListener {

    // dependencias
    private final jugador Player;

    //datos
    float speed = 100;

    public inputHandler(jugador Player) {
        this.Player = Player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();
            if (code == KeyEvent.VK_W){Player.moveY(speed);}
            if (code == KeyEvent.VK_S){Player.moveY(-speed);}
            if (code == KeyEvent.VK_D){Player.moveX(speed);}
            if (code == KeyEvent.VK_A){Player.moveX(-speed);}

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
