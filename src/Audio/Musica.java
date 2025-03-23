/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author LENOVO
 */
public class Musica extends Audio{
    // atributos
    static private Musica instancia; // instancia estatica global
    public boolean loopOn = false;
    
    private Musica () {
        volume = (float) 0.7;
        direccion = new String[4];
        direccion[0] = "src\\CarpetaDeMusica\\mainMusic.mp3";
        direccion[1] = "src\\CarpetaDeMusica\\8BitMusic.mp3";
        direccion[2] = "src\\CarpetaDeMusica\\thinkMusic.mp3";
        direccion[3] = "src\\CarpetaDeMusica\\thinkMusic2.mp3";
    }
    
    static public Musica getInstance () {
        if (instancia == null ){
            instancia = new Musica ();
        }
        return instancia;
    }
    
}
