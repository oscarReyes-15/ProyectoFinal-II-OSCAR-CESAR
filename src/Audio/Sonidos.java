/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Audio;

/**
 *
 * @author LENOVO
 */
public class Sonidos extends Audio{
    // atributos
    private static Sonidos instancia; // instancia estatica global
    
    private Sonidos () {
        direccion = new String [4];
        direccion[0] = "src\\CarpetaDeSonidos\\Step.mp3";
        direccion[1] = "src\\CarpetaDeSonidos\\boxSound.mp3";
        direccion[2] = "src\\CarpetaDeSonidos\\Win.mp3";
        direccion[3] = "src\\CarpetaDeSonidos\\Btn.mp3";
    }
    
    static public Sonidos getInstance () {
        if (instancia == null ){
            instancia = new Sonidos ();
        }
        return instancia;
    } 
   
}
