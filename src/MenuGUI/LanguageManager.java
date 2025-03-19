package MenuGUI;
import java.util.*;

public class LanguageManager {
    private static Locale currentLocale = new Locale("es", "ES");
    private static final Map<String, String> spanishMessages = new HashMap<>();
    private static final Map<String, String> englishMessages = new HashMap<>();
    private static ResourceBundle messages;
    
    static {
        initializeSpanishMessages();
        initializeEnglishMessages();
        
        messages = createCustomBundle(spanishMessages);
    }
    
    private static void initializeSpanishMessages() {
        spanishMessages.put("title.main", "Menu Principal");
        spanishMessages.put("button.play", "1. Jugar");
        spanishMessages.put("button.profile", "2. Mi Perfil");
        spanishMessages.put("button.ranking", "3. Ranking");
        spanishMessages.put("button.settings", "4. Ajustes");
        spanishMessages.put("button.logout", "5. Cerrar Sesion");
        spanishMessages.put("dialog.multiplayer", "Función Multiplayer en desarrollo");
        spanishMessages.put("dialog.confirmLogout", "¿Desea cerrar sesión?");
        spanishMessages.put("dialog.confirmation", "Confirmación");
        spanishMessages.put("title.settings", "Ajustes");
        spanishMessages.put("button.language", "Idioma");
        spanishMessages.put("label.volume", "Volumen");
        spanishMessages.put("button.controls", "Controles");
        spanishMessages.put("button.back", "Volver");
        spanishMessages.put("dialog.selectLanguage", "Seleccione un idioma");
        spanishMessages.put("dialog.changeLanguage", "Cambiar Idioma");
        spanishMessages.put("dialog.languageChanged", "Idioma cambiado a");
        spanishMessages.put("controls.title", "Controles");
        spanishMessages.put("controls.moveUp", "Mover Arriba: Flecha Arriba / W");
        spanishMessages.put("controls.moveDown", "Mover Abajo: Flecha Abajo / S" );
        spanishMessages.put("controls.moveLeft", "Mover Izquierda: Flecha Izquierda / A" );
        spanishMessages.put("controls.moveRight", "Mover Derecha: Flecha Derecha / D" );
        spanishMessages.put("controls.reset", "Reiniciar : R" );

    }
    
    private static void initializeEnglishMessages() {
        englishMessages.put("title.main", "Main Menu");
        englishMessages.put("button.play", "1. Play");
        englishMessages.put("button.profile", "2. My Profile");
        englishMessages.put("button.ranking", "3. Ranking");
        englishMessages.put("button.settings", "4. Settings");
        englishMessages.put("button.logout", "5. Logout");
        englishMessages.put("dialog.multiplayer", "Multiplayer feature under development");
        englishMessages.put("dialog.confirmLogout", "Do you want to log out?");
        englishMessages.put("dialog.confirmation", "Confirmation");
        englishMessages.put("title.settings", "Settings");
        englishMessages.put("button.language", "Language");
        englishMessages.put("label.volume", "Volume");
        englishMessages.put("button.controls", "Controls");
        englishMessages.put("button.back", "Back");
        englishMessages.put("dialog.selectLanguage", "Select a language");
        englishMessages.put("dialog.changeLanguage", "Change Language");
        englishMessages.put("dialog.languageChanged", "Language changed to");
        englishMessages.put("controls.title", "Controls");
        englishMessages.put("controls.moveUp", "Move Up: Arrow Up");
        englishMessages.put("controls.moveDown", "Move Down: Arrow Down / S" );
        englishMessages.put("controls.moveLeft", "Move Left : Left Arrow / A" );
        englishMessages.put("controls.moveRight", "Move Right: Right Arrow / D" );
        englishMessages.put("controls.reset", "Restrart : R" );

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
    }
    
    public static ResourceBundle getMessages() {
        return messages;
    }
    
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}