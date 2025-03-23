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
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**clase base de audio
 *  Funciones a ser heredadas:
 *  - play -> sonido
 *  - set volume
 *  
 * @author LENOVO
 */
public class Audio implements Runnable {
    // atributos
    static public float volume = (float) 1;
    protected String[] direccion;
    protected boolean uDone = false;
    int song;
    
    // variables reproductores
    protected Thread audioThread;
    protected AdvancedPlayer audioPlayer;
    protected FileInputStream FIS;
    protected BufferedInputStream BIS;
    protected CustomAudioDevice CAD;
    
    protected Audio () {} // Invasion de privacidad instanciar
    
    public void play (int n) {
        audioStop();        
        song = n;
        audioThread = new Thread(this);
        audioThread.start();
    }
    
    public void audioStop () {
        if (audioPlayer != null) {
            audioPlayer.close();
            audioPlayer = null;
        }
        audioThread = null;
    }
    
    public void setVolume (float vol) {
        System.out.println((CAD != null) + "print");
        System.out.println(volume + ": current volumen");
        if (CAD != null) {  
            CAD.setVolume(vol);
        }
    }

     public void initVol () {
        new Thread () {
            @Override
            public void run() {
                try { 
                    while (!uDone){
                        Thread.sleep (100);
                        setVolume(volume);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
    }

    @Override
    public void run() {
        try {
            FIS = new FileInputStream(direccion[song]);
            BIS = new BufferedInputStream(FIS);
            CAD = new CustomAudioDevice();
            audioPlayer = new AdvancedPlayer(BIS, CAD);

            initVol();
            audioPlayer.play();
            uDone = false;
            audioStop();
        } catch (JavaLayerException | FileNotFoundException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected class CustomAudioDevice extends JavaSoundAudioDevice {
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
                    
                }
            } catch (RuntimeException | LineUnavailableException | LinkageError ex) {
                t = ex;
            }
            if (source == null) {
                throw new JavaLayerException("cannot obtain source audio line", t);
            } else try {
                    gainControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                    uDone = true;
                } catch (IllegalArgumentException e) {
                    System.err.println("Volume control not supported");
                }
        }
        
        public void setVolume(float volume) {
            if (gainControl != null) {
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                float gain = min + (max - min) * volume;
                gainControl.setValue(gain);
                System.out.println("hello");
            } 
        }

    }
}
