/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import Juego.Nivel;
import javax.swing.ImageIcon;

/*
    Clase base para todos los objetos
    Todos contienen: 
    - x 
    - y 
    - tile size (funcion para calcular segun el ratio de pantalla)
    - imagen

 */

public class Object {
    // atributos 
    protected int x, y;
    protected int tileSize = 64;
    protected ImageIcon image;
    protected int[][] bounds = new int[4][2]; // [no. bound][0 = x, 1 = y]
    
    // dependencia
    Nivel nivel;
    
    public Object(int x, int y, Nivel nivel) {
        this.x = x;
        this.y = y;
        this.nivel = nivel;
        setBounds();
    }

    public final void setBounds () {
        // esquina alta izquierda
        bounds[0][0] = x;
        bounds[0][1] = y;
        
        // esquina baja izquierda
        bounds[1][0] = x;
        bounds[1][1] = y + tileSize;
        
        // esquina baja derecha
        bounds[2][0] = x + tileSize;
        bounds[2][1] = y + tileSize;
        
        // esquina alta derecha
        bounds[3][0] = x + tileSize;
        bounds[3][1] = y;
        
    }
    
    public boolean isInBound (int speed, String coord) {
        setBounds();
        for (int [] bound : bounds){
            
            switch (coord) {
                
                case "x" -> {
                    int x2 = bound[0] + speed;
                    if (x2 > nivel.game.getWidth() || x2 < 0){
                        return false;
                    } 
                }
                case "y" -> {
                    int y2 = bound[1] + speed;
                    if (y2 > nivel.game.getHeight() || y2 < 0){
                        return false;
                    } 
                }
            }

        }
        return true;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTileSize() {
        return tileSize;
    }

    public ImageIcon getImage() {
        return image;
    }
    
}

