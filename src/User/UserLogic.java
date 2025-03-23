package User;

import InitGUI.*;
import SubMenuOption.*;
import java.io.File;
import javax.swing.*;

public class UserLogic {

    private String usuarioActual;

    public UserLogic(String usuario) {
        this.usuarioActual = usuario;
    }
    public void cambiarContrasena() {
    if (usuarioActual == null) {
        JOptionPane.showMessageDialog(null, 
            LanguageManager.getMessages().getString("dialog.noActivePlayer"), 
            LanguageManager.getMessages().getString("dialog.error"), 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    String contraActual = JOptionPane.showInputDialog(
        LanguageManager.getMessages().getString("dialog.enterCurrentPassword"));
}

    public boolean eliminarCuenta() {
    int confirmacion = JOptionPane.showConfirmDialog(null,
            LanguageManager.getMessages().getString("dialog.confirmDeleteAccount"),
            LanguageManager.getMessages().getString("dialog.confirmation"), 
            JOptionPane.YES_NO_OPTION);
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
