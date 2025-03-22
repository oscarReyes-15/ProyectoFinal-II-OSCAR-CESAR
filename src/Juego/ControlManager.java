package Juego;
import java.io.*;
import javax.swing.JOptionPane;
import User.UserFile;

public class ControlManager {
    private static final char DEFAULT_UP_KEY = 'W';
    private static final char DEFAULT_DOWN_KEY = 'S';
    private static final char DEFAULT_LEFT_KEY = 'A'; 
    private static final char DEFAULT_RIGHT_KEY = 'D';
    private static final char DEFAULT_RESET_KEY = 'R';
    
    public static void saveControls(String usuario, char upKey, char downKey, char leftKey, char rightKey, char resetKey) {
        char[] controls = new char[] {
            upKey,
            downKey,
            leftKey,
            rightKey,
            resetKey
        };
        
        UserFile.setControlsForUser(usuario, controls);
    }
    
    public static char[] loadControls(String usuario) {
        char[] controls = UserFile.getControlsForUser(usuario);
        
        if (controls == null || controls.length < 5) {
            return getDefaultControls();
        }
        
        return controls;
    }
    
    public static char[] getDefaultControls() {
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
    
    public static void resetToDefaultControls(String usuario) {
        char[] defaults = getDefaultControls();
        saveControls(usuario, defaults[0], defaults[1], defaults[2], defaults[3], defaults[4]);
    }
}