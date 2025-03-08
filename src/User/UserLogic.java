package User;

import MenuGUI.MainMenu;
import java.io.File;
import javax.swing.*;

public class UserLogic {
    
    private String usuarioActual;
    
    public UserLogic(String usuario) {
        this.usuarioActual = usuario;
    }
    
    public static boolean existeUsuario(String usuario) {
        File directorio = new File(usuario);
        return directorio.exists();
    }
    
    public boolean eliminarCuenta() {
        if (usuarioActual == null) {
            return false;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Seguro que deseas borrar tu cuenta?", 
                "Confirmación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            File carpetaUsuario = new File(usuarioActual);
            if (carpetaUsuario.exists()) {
                if (eliminarDirectorio(carpetaUsuario)) {
                    JOptionPane.showMessageDialog(null, "Cuenta eliminada con éxito.");
                    usuarioActual = null; 
                    new MainMenu();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "Error al eliminar la cuenta.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return false;
    }
    
    private boolean eliminarDirectorio(File directorio) {
        boolean success = true;
        if (directorio.isDirectory()) {
            File[] files = directorio.listFiles();
            if (files != null) {
                for (File archivo : files) {
                    success = success && eliminarDirectorio(archivo);
                }
            }
        }
        return success && directorio.delete();
    }
}