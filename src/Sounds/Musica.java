/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sounds;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;
/**
 *
 * @author LENOVO
 */
public class Musica {
    private static Musica instancia;
    FileInputStream FIS;
    BufferedInputStream BIS;
    AdvancedPlayer player;
    String[] direccion = {
        "src/Sounds/BitMusic.mp3",
        "src/Sounds/RockMusic.mp3",};
    CustomAudioDevice CAD;
    public static float volume = (float) 0.7;
    public static boolean uDone = false;

    private Musica () {}
    
    public static Musica getInstance () {
        if (instancia == null){
            instancia = new Musica();
            
        }
        return instancia;
    }
    
    
    public void play(int n) {
        System.out.println("play");
        if (player == null) {
            try {
                System.out.println(volume);
                FIS = new FileInputStream(direccion[n]);
                BIS = new BufferedInputStream(FIS);
                CAD = new CustomAudioDevice();
                
                player = new AdvancedPlayer(BIS, CAD);
                System.out.println(volume);
                new Thread() {
                    @Override
                    public void run() { 
                        try {
                            
                            System.out.println("Playig");
                            System.out.println(volume);
                            initVol();
                            player.play();
                            try {
                                Thread.sleep(3000);
                                play(n);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (JavaLayerException ex) {
                            Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();
            } catch (JavaLayerException | FileNotFoundException ex) {
                Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void initVol () {
        float vol = volume;
        System.out.println(vol + " vol " + volume +" volume ");
        new Thread () {
            @Override
            public void run() {
                try { 
                    System.out.println(volume + " init vol");
                    while (!uDone){
                        Thread.sleep (100);
                        System.out.println(volume);
                        setVolume(volume);
                        System.out.println(volume);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
    }

    
    public void stop () {
        if (player != null) {
            player.close();
            player = null;
            uDone = false;
        }
    }
    
    static class CustomAudioDevice extends JavaSoundAudioDevice {
        private FloatControl gainControl;

        @Override
        protected void createSource() throws JavaLayerException {
            Throwable t = null;
            try {
                Line line = AudioSystem.getLine(getSourceLineInfo());
                if (line instanceof SourceDataLine) {
                    source = (SourceDataLine) line;
                    //source.open(fmt, millisecondsToBytes(fmt, 2000));
                    source.open(fmt);
                    source.start();
                    System.out.println("gottem");
                    Musica.uDone = true;
                }
            } catch (RuntimeException | LineUnavailableException | LinkageError ex) {
                t = ex;
            }
            if (source == null) {
                throw new JavaLayerException("cannot obtain source audio line", t);
            }else {
                try {
                    gainControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                } catch (IllegalArgumentException e) {
                    System.err.println("Volume control not supported");
                }
            }
        }

        public void setVolume(float volume) {
            System.out.println((gainControl != null) + " jfskdjfh ");
            if (gainControl != null) {
                System.out.println("setting");
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                float gain = min + (max - min) * volume;
                System.out.println(gain);
                gainControl.setValue(gain);
            } 
        }
    }
    
    public boolean setVolume (float vol) {
        System.out.println((CAD != null) + "print");
        if (CAD != null) {  
            volume = vol;
            CAD.setVolume(vol);
            return true;
        }
        return false;
    }
}
