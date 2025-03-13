/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import javax.swing.Action;

/**
 *
 * @author LENOVO
 */
public class inputs {
    private String actionName;
    private Runnable action;

    public inputs(String actionName, Runnable action) {
        this.actionName = actionName;
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public Runnable getAction() {
        return action;
    }
    
}
