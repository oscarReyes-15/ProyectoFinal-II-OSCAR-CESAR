package MenuGUI;
import java.io.*;
import java.util.*;

public class LanguageManager {
    private static Locale currentLocale = new Locale("es", "ES");
    private static final Map<String, String> spanishMessages = new HashMap<>();
    private static final Map<String, String> englishMessages = new HashMap<>();
    private static ResourceBundle messages;
    private static final String LANGUAGE_FILE = "language.dat";
    
    static {
        initializeSpanishMessages();
        initializeEnglishMessages();
        
        loadLanguagePreference();
    }
    
    private static void initializeSpanishMessages() {
        // Menu
        spanishMessages.put("title.main", "Menu Principal");
        spanishMessages.put("button.play", "1. Jugar");
        spanishMessages.put("button.profile", "2. Mi Perfil");
        spanishMessages.put("button.ranking", "3. Ranking");
        spanishMessages.put("button.settings", "4. Ajustes");
        spanishMessages.put("button.logout", "5. Cerrar Sesion");
        spanishMessages.put("dialog.multiplayer", "Función Multiplayer en desarrollo");
        spanishMessages.put("dialog.confirmLogout", "¿Desea cerrar sesión?");
        spanishMessages.put("dialog.confirmation", "Confirmación");
        
        // Ajustes
        spanishMessages.put("title.settings", "Ajustes");
        spanishMessages.put("button.language", "Idioma");
        spanishMessages.put("label.volume", "Volumen");
        spanishMessages.put("button.controls", "Controles");
        spanishMessages.put("button.back", "Volver");
        spanishMessages.put("dialog.selectLanguage", "Seleccione un idioma");
        spanishMessages.put("dialog.changeLanguage", "Cambiar Idioma");
        spanishMessages.put("dialog.languageChanged", "Idioma cambiado a");
        
        // Controles
        spanishMessages.put("controls.title", "Controles");
        spanishMessages.put("controls.moveUp", "Mover Arriba: Flecha Arriba / W");
        spanishMessages.put("controls.moveDown", "Mover Abajo: Flecha Abajo / S" );
        spanishMessages.put("controls.moveLeft", "Mover Izquierda: Flecha Izquierda / A" );
        spanishMessages.put("controls.moveRight", "Mover Derecha: Flecha Derecha / D" );
        spanishMessages.put("controls.reset", "Reiniciar : R" );
        spanishMessages.put("profile.reset", "Reiniciar : R" );
        
        // Niveles
        spanishMessages.put("title.selectLevel", "Seleccionar Nivel");
        spanishMessages.put("level.1", "Nivel 1");
        spanishMessages.put("level.2", "Nivel 2");
        spanishMessages.put("level.3", "Nivel 3");
        spanishMessages.put("level.4", "Nivel 4");
        spanishMessages.put("level.5", "Nivel 5");
        spanishMessages.put("dialog.levelInDevelopment", "Este nivel está en desarrollo");
        
        // Mi Perfil
        spanishMessages.put("title.profile", "Mi Perfil");
        spanishMessages.put("profile.user", "Usuario");
        spanishMessages.put("profile.points", "Puntos");
        spanishMessages.put("profile.creationDate", "Fecha de creación");
        spanishMessages.put("profile.maxLevel", "Nivel máximo");
        spanishMessages.put("profile.gamesPlayed", "Partidas jugadas");
        spanishMessages.put("profile.timePlayed", "Tiempo Jugado");
        spanishMessages.put("profile.notAvailable", "No disponible");
        spanishMessages.put("button.changeAvatar", "Cambiar Avatar");
        spanishMessages.put("button.changePassword", "Cambiar Contraseña");
        spanishMessages.put("button.deleteAccount", "Eliminar Cuenta");
        spanishMessages.put("dialog.confirmDeleteAccount", "¿Está seguro que desea eliminar su cuenta?");
        spanishMessages.put("dialog.accountDeleted", "Cuenta eliminada correctamente");
        
        //Seleccion Avatar
        spanishMessages.put("title.selectAvatar", "Seleccionar Avatar");
        spanishMessages.put("button.returnWithoutChanging", "Regresar sin cambiar");
        spanishMessages.put("dialog.avatarSelected", "Avatar seleccionado correctamente");
        spanishMessages.put("dialog.avatarError", "Error al copiar el avatar seleccionado");
        spanishMessages.put("dialog.profileError", "Error al regresar al perfil");
        spanishMessages.put("dialog.success", "Éxito");
        spanishMessages.put("dialog.error", "Error");
    }
    
    private static void initializeEnglishMessages() {
        // Menu
        englishMessages.put("title.main", "Main Menu");
        englishMessages.put("button.play", "1. Play");
        englishMessages.put("button.profile", "2. My Profile");
        englishMessages.put("button.ranking", "3. Ranking");
        englishMessages.put("button.settings", "4. Settings");
        englishMessages.put("button.logout", "5. Logout");
        englishMessages.put("dialog.multiplayer", "Multiplayer feature under development");
        englishMessages.put("dialog.confirmLogout", "Do you want to log out?");
        englishMessages.put("dialog.confirmation", "Confirmation");
        
        // Settings
        englishMessages.put("title.settings", "Settings");
        englishMessages.put("button.language", "Language");
        englishMessages.put("label.volume", "Volume");
        englishMessages.put("button.controls", "Controls");
        englishMessages.put("button.back", "Back");
        englishMessages.put("dialog.selectLanguage", "Select a language");
        englishMessages.put("dialog.changeLanguage", "Change Language");
        englishMessages.put("dialog.languageChanged", "Language changed to");
        
        // Controls
        englishMessages.put("controls.title", "Controls");
        englishMessages.put("controls.moveUp", "Move Up: Arrow Up / W");
        englishMessages.put("controls.moveDown", "Move Down: Arrow Down / S" );
        englishMessages.put("controls.moveLeft", "Move Left: Left Arrow / A" );
        englishMessages.put("controls.moveRight", "Move Right: Right Arrow / D" );
        englishMessages.put("controls.reset", "Restart: R" );
        
        // Levels
        englishMessages.put("title.selectLevel", "Select Level");
        englishMessages.put("level.1", "Level 1");
        englishMessages.put("level.2", "Level 2");
        englishMessages.put("level.3", "Level 3");
        englishMessages.put("level.4", "Level 4");
        englishMessages.put("level.5", "Level 5");
        englishMessages.put("dialog.levelInDevelopment", "This level is under development");
        
        // Profile 
        englishMessages.put("title.profile", "My Profile");
        englishMessages.put("profile.user", "User");
        englishMessages.put("profile.points", "Points");
        englishMessages.put("profile.creationDate", "Creation date");
        englishMessages.put("profile.maxLevel", "Maximum level");
        englishMessages.put("profile.gamesPlayed", "Games played");
        englishMessages.put("profile.timePlayed", "Time played");
        englishMessages.put("profile.notAvailable", "Not available");
        englishMessages.put("button.changeAvatar", "Change Avatar");
        englishMessages.put("button.changePassword", "Change Password");
        englishMessages.put("button.deleteAccount", "Delete Account");
        englishMessages.put("dialog.confirmDeleteAccount", "Are you sure you want to delete your account?");
        englishMessages.put("dialog.accountDeleted", "Account successfully deleted");
        
        // Avatar selection 
        englishMessages.put("title.selectAvatar", "Select Avatar");
        englishMessages.put("button.returnWithoutChanging", "Return without changing");
        englishMessages.put("dialog.avatarSelected", "Avatar successfully selected");
        englishMessages.put("dialog.avatarError", "Error copying the selected avatar");
        englishMessages.put("dialog.profileError", "Error returning to profile");
        englishMessages.put("dialog.success", "Success");
        englishMessages.put("dialog.error", "Error");
    }
    
    private static ResourceBundle createCustomBundle(final Map<String, String> messagesMap) {
        return new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return messagesMap.getOrDefault(key, key);
            }
            
            @Override
            public Enumeration<String> getKeys() {
                return Collections.enumeration(messagesMap.keySet());
            }
        };
    }
    

    public static void setLanguage(String language) {
        if (language.equals("English")) {
            currentLocale = new Locale("en", "US");
            messages = createCustomBundle(englishMessages);
        } else {
            currentLocale = new Locale("es", "ES");
            messages = createCustomBundle(spanishMessages);
        }
        
        saveLanguagePreference();
    }
    
    private static void saveLanguagePreference() {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(LANGUAGE_FILE))) {
            out.writeUTF(currentLocale.getLanguage());
            out.writeUTF(currentLocale.getCountry());
        } catch (IOException e) {
            System.err.println("Error saving language preference: " + e.getMessage());
        }
    }
    
   
    private static void loadLanguagePreference() {
        File file = new File(LANGUAGE_FILE);
        if (file.exists()) {
            try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
                String language = in.readUTF();
                String country = in.readUTF();
                currentLocale = new Locale(language, country);
                
                if (language.equals("en")) {
                    messages = createCustomBundle(englishMessages);
                } else {
                    messages = createCustomBundle(spanishMessages);
                }
            } catch (IOException e) {
                System.err.println("Error loading language preference: " + e.getMessage());
                messages = createCustomBundle(spanishMessages);
            }
        } else {
            messages = createCustomBundle(spanishMessages);
        }
    }
    
    
    public static void setLanguageForUser(String language, String usuario) {
        setLanguage(language);
        
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(usuario + "_language.dat"))) {
            out.writeUTF(currentLocale.getLanguage());
            out.writeUTF(currentLocale.getCountry());
        } catch (IOException e) {
            System.err.println("Error saving language preference for user: " + e.getMessage());
        }
    }
    

    public static void loadLanguageForUser(String usuario) {
        File file = new File(usuario + "_language.dat");
        if (file.exists()) {
            try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
                String language = in.readUTF();
                String country = in.readUTF();
                currentLocale = new Locale(language, country);
                
                if (language.equals("en")) {
                    messages = createCustomBundle(englishMessages);
                } else {
                    messages = createCustomBundle(spanishMessages);
                }
            } catch (IOException e) {
                System.err.println("Error loading language preference for user: " + e.getMessage());
                messages = createCustomBundle(spanishMessages);
            }
        } else {
            loadLanguagePreference();
        }
    }
    
    public static ResourceBundle getMessages() {
        return messages;
    }
    
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}