package User;
import InitGUI.*;
import SubMenuOption.*;
import java.io.File;
import javax.swing.*;
import java.util.ResourceBundle;

public class UserLogic {
    private String usuarioActual;
    private ResourceBundle messages;
    
    public UserLogic(String usuario) {
        this.usuarioActual = usuario;
        this.messages = LanguageManager.getMessages();
    }
    
    public void cambiarContrasena() {
         if (usuarioActual == null) {
             JOptionPane.showMessageDialog(null, 
                 messages.getString("error.noActivePlayer"), 
                 messages.getString("error.title"), 
                 JOptionPane.ERROR_MESSAGE);
             return;
         }
 
         String contraActual = JOptionPane.showInputDialog(messages.getString("password.current"));
 
         if (contraActual == null) {
             return;
         }
 
         if (!UserFile.verificarCredenciales(usuarioActual, contraActual)) {
             JOptionPane.showMessageDialog(null, 
                 messages.getString("password.incorrect"), 
                 messages.getString("error.title"), 
                 JOptionPane.ERROR_MESSAGE);
                 
             int opcion = JOptionPane.showConfirmDialog(null,
                     messages.getString("password.tryAgain"),
                     messages.getString("password.incorrect"), 
                     JOptionPane.YES_NO_OPTION);
                     
             if (opcion == JOptionPane.YES_OPTION) {
                 cambiarContrasena();
             }
             return;
         }
         
         String nuevaContra = JOptionPane.showInputDialog(messages.getString("password.new"));
         
         if (nuevaContra == null) {
             return;
         }
         
         if (UserFile.cambiarContraseña(usuarioActual, nuevaContra)) {
             JOptionPane.showMessageDialog(null, messages.getString("password.changeSuccess"));
         } else {
             JOptionPane.showMessageDialog(null, 
                 messages.getString("password.changeError"), 
                 messages.getString("error.title"), 
                 JOptionPane.ERROR_MESSAGE);
         }
    }
    
    public boolean eliminarCuenta() {
         if (usuarioActual == null) {
             return false;
         }
 
         int confirmacion = JOptionPane.showConfirmDialog(null,
                 messages.getString("account.deleteConfirm"),
                 messages.getString("dialog.confirmation"), 
                 JOptionPane.YES_NO_OPTION);
 
         if (confirmacion == JOptionPane.YES_OPTION) {
             File carpetaUsuario = new File(usuarioActual);
             if (carpetaUsuario.exists()) {
                 if (eliminarDirectorio(carpetaUsuario)) {
                     JOptionPane.showMessageDialog(null, messages.getString("account.deleteSuccess"));
                     usuarioActual = null;
                     new MainMenu();
                     return true;
                 } else {
                     JOptionPane.showMessageDialog(null,
                             messages.getString("account.deleteError"),
                             messages.getString("error.title"), 
                             JOptionPane.ERROR_MESSAGE);
                 }
             }
         }
         return false;
     }
     
    public static boolean existeUsuario(String usuario) {
        File directorio = new File(usuario);
        return directorio.exists();
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