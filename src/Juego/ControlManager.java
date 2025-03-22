package Juego;

import java.io.*;
import javax.swing.JOptionPane;

public class ControlManager {
    private static final String CONTROLS_FILE_SUFFIX = "_controls.dat";
    
    private static final char DEFAULT_UP_KEY = 'W';
    private static final char DEFAULT_DOWN_KEY = 'S';
    private static final char DEFAULT_LEFT_KEY = 'A'; 
    private static final char DEFAULT_RIGHT_KEY = 'D';
    private static final char DEFAULT_RESET_KEY = 'R';
    
    public static void saveControls(String usuario, char upKey, char downKey, char leftKey, char rightKey, char resetKey) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(usuario + CONTROLS_FILE_SUFFIX))) {
            out.writeChar(upKey);
            out.writeChar(downKey);
            out.writeChar(leftKey);
            out.writeChar(rightKey);
            out.writeChar(resetKey);
        } catch (IOException e) {
            System.err.println("Error saving controls for user " + usuario + ": " + e.getMessage());
        }
    }
    
    public static char[] loadControls(String usuario) {
        File file = new File(usuario + CONTROLS_FILE_SUFFIX);
        char[] controls = new char[5];
        
        if (file.exists()) {
            try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
                controls[0] = in.readChar(); 
                controls[1] = in.readChar(); 
                controls[2] = in.readChar();
                controls[3] = in.readChar(); 
                controls[4] = in.readChar(); 
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Error loading controls for user " + usuario + ": " +e.getMessage());
                controls = getDefaultControls();
            }
        } else {
            controls = getDefaultControls();
        }
        
        return controls;
    }
    
    private static char[] getDefaultControls() {
        return new char[] {
            DEFAULT_UP_KEY,
            DEFAULT_DOWN_KEY,
            DEFAULT_LEFT_KEY,
            DEFAULT_RIGHT_KEY,
            DEFAULT_RESET_KEY
        };
    }
    
    public static void applyUserControls(String usuario) {
        char[] controls = loadControls(usuario);
        ControlSettings.UP_KEY = controls[0];
        ControlSettings.DOWN_KEY = controls[1];
        ControlSettings.LEFT_KEY = controls[2];
        ControlSettings.RIGHT_KEY = controls[3];
        ControlSettings.RESET_KEY = controls[4];
    }
}