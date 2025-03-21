/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sounds;

/**
 *
 * @author LENOVO
 */
import javax.sound.sampled.*;
import java.net.URL;


public final class Sonidos {
    //Sounds
    public Clip clip;
    public URL soundLink[] = new URL[6];
    public float vol = (float)0.5;
    
    public Sonidos(){
        soundLink[0] = getClass().getResource("/Sounds/mainMusic.wav"); 
        soundLink[1] = getClass().getResource("/Sounds/AHH.wav"); 
        soundLink[2] = getClass().getResource("/Sounds/rockMusic.wav"); 
        soundLink[3] = getClass().getResource("/Sounds/butt.wav"); 
    }
        
    public void setSound(int i){
        
        try{
            if (soundLink[i] == null) { 
                throw new NullPointerException("soundLink[" + i + "] is null");
            }
            AudioInputStream AIS = AudioSystem.getAudioInputStream(soundLink[i]);
            clip = AudioSystem.getClip();
            clip.open(AIS);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Loop(){
        
        if (clip != null){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void Play(int n){
        setSound(n);
        
        if (clip != null){
            clip.start();
        }
    }
    
    public void Stop(){
        if (clip != null){
            clip.stop();
            clip.setFramePosition(0);
            clip = null;
        }
    }
    
    public void setVolume(float volume) {
        if (clip != null) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                // Volume should be between 0.0 (mute) and 1.0 (full volume)
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                float newGain = min + (max - min) * volume; 
                gainControl.setValue(newGain);
            } catch (Exception e) {
                System.out.println("Volume control not supported.");
            }
        }
    }
}

