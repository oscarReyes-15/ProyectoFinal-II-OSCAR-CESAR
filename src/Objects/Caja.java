/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import Juego.Nivel;

/*
    Objeto escenial en el juego    
    - tiene funcion de mover (con sus propias restricciones)
    - getters de posicion e imagen 

*/
public class Caja extends Object {
    // dependencia
    Nivel nivel;
    
    // constructor 
    public Caja(Nivel nivel) {
        super(nivel.getX(), nivel.getY(), this.nivel = nivel);
    }
    
}

