package SubMenuOption;
import java.util.*;
import User.UserFile;

public class LanguageManager {
    private static Locale currentLocale = new Locale("es", "ES");
    private static final Map<String, String> spanishMessages = new HashMap<>();
    private static final Map<String, String> englishMessages = new HashMap<>();
    private static final Map<String, String> italianMessages= new HashMap<>();
    private static ResourceBundle messages;
    
    static {
        initializeSpanishMessages();
        initializeEnglishMessages();
        initializeItalianMessages();
        
        // Default to Spanish
        messages = createCustomBundle(spanishMessages);
    }
    
    private static void initializeSpanishMessages() {
        spanishMessages.put("title.main", "Menu Principal");
        spanishMessages.put("button.play", "1. Jugar");
        spanishMessages.put("button.profile", "2. Mi Perfil");
        spanishMessages.put("button.ranking", "3. Clasificacion");
        spanishMessages.put("button.settings", "4. Ajustes");
        spanishMessages.put("button.logout", "5. Cerrar Sesion");
        spanishMessages.put("dialog.confirmLogout", "¿Desea cerrar sesión?");
        spanishMessages.put("dialog.confirmation", "Confirmación");
        
        // Ajustes
        spanishMessages.put("title.settings", "Ajustes");
        spanishMessages.put("button.language", "Idioma");
        spanishMessages.put("label.volume", "Volumen");
        spanishMessages.put("button.controls", "Controles");
        spanishMessages.put("button.ncontrols", "Cambiar Controles");
        spanishMessages.put("button.back", "Volver");
        spanishMessages.put("dialog.selectLanguage", "Seleccione un idioma");
        spanishMessages.put("dialog.changeLanguage", "Cambiar Idioma");
        spanishMessages.put("dialog.languageChanged", "Idioma cambiado a");
        
        // Control Settings - Added missing entries
        spanishMessages.put("title.controls", "Configuración de Controles");
        spanishMessages.put("button.save", "Guardar");
        spanishMessages.put("button.default", "Controles Predeterminados");
        spanishMessages.put("dialog.defaultControls", "Los controles se han restablecido a los valores predeterminados (WASD + R). Haga clic en Guardar para aplicar.");
        spanishMessages.put("dialog.controlsSaved", "Controles guardados correctamente");
        spanishMessages.put("dialog.info", "Información");
        
        // Controles
        spanishMessages.put("controls.title", "Controles");
        spanishMessages.put("controls.moveUp", "Mover Arriba");
        spanishMessages.put("controls.moveDown", "Mover Abajo");
        spanishMessages.put("controls.moveLeft", "Mover Izquierda");
        spanishMessages.put("controls.moveRight", "Mover Derecha");
        spanishMessages.put("controls.reset", "Reiniciar");
        spanishMessages.put("profile.reset", "Reiniciar : R");
        
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
        spanishMessages.put("profile.name", "Nombre");
        spanishMessages.put("profile.points", "Puntos");
        spanishMessages.put("profile.lastLogin", "Ultima Sesion");
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
        spanishMessages.put("button.lastGames", "Ver últimas partidas");
        spanishMessages.put("dialog.lastgames", "Últimas Partidas");
    spanishMessages.put("dialog.completed", "Completado");
    spanishMessages.put("dialog.notCompleted", "No Completado");
    spanishMessages.put("level", "Nivel");
    spanishMessages.put("time", "Tiempo");
    spanishMessages.put("results", "Resultado");
    spanishMessages.put("No hay partidas registradas", "No hay partidas registradas");
        
        //Seleccion Avatar
        spanishMessages.put("title.selectAvatar", "Seleccionar Avatar");
        spanishMessages.put("button.returnWithoutChanging", "Regresar sin cambiar");
        spanishMessages.put("dialog.avatarSelected", "Avatar seleccionado correctamente");
        spanishMessages.put("dialog.avatarError", "Error al copiar el avatar seleccionado");
        spanishMessages.put("dialog.profileError", "Error al regresar al perfil");
        spanishMessages.put("dialog.success", "Éxito");
        spanishMessages.put("dialog.error", "Error");
        
        //Juego 
        spanishMessages.put("game.level", "Nivel");
        spanishMessages.put("game.restart", "Reiniciar");
        spanishMessages.put("game.exit", "Salir");
        spanishMessages.put("game.timer", "Tiempo");
        spanishMessages.put("game.movements", "Movimientos");
        spanishMessages.put("dialog.finish", "Felicidades, has completado todo el juego!");

        
        
        // Level completion messages
        spanishMessages.put("dialog.levelCompleted", "¡Nivel completado!");
        spanishMessages.put("dialog.congratulations", "Felicitaciones");
        spanishMessages.put("dialog.nextLevelAvailable", "¡Ya puedes avanzar al siguiente nivel!");
        spanishMessages.put("dialog.confirmExit", "Desea salir del nivel?");
        spanishMessages.put("dialog.exitGame", "Salir del Juego");
        
        //Ranking
        spanishMessages.put("title.ranking", "RANKING DE JUGADORES");
        spanishMessages.put("ranking.noPlayers", "No hay jugadores registrados.");
        spanishMessages.put("ranking.position", "Pos"); 
        spanishMessages.put("ranking.user", "Usuario");
        spanishMessages.put("ranking.points", "Puntos");
        spanishMessages.put("ranking.level", "Nivel");
        spanishMessages.put("ranking.totalTime", "Tiempo Total");
        
       // Para UserLogic
spanishMessages.put("error.noActivePlayer", "No hay un jugador activo.");
spanishMessages.put("error.title", "Error");
spanishMessages.put("password.current", "Ingrese su contraseña actual:");
spanishMessages.put("password.incorrect", "Contraseña incorrecta.");
spanishMessages.put("password.tryAgain", "¿Desea intentar nuevamente?");
spanishMessages.put("password.new", "Ingrese su nueva contraseña:");
spanishMessages.put("password.changeSuccess", "Contraseña cambiada exitosamente.");
spanishMessages.put("password.changeError", "Error al cambiar la contraseña.");
spanishMessages.put("account.deleteConfirm", "¿Seguro que deseas borrar tu cuenta?");
spanishMessages.put("account.deleteSuccess", "Cuenta eliminada con éxito.");
spanishMessages.put("account.deleteError", "Error al eliminar la cuenta.");
spanishMessages.put("dialog.confirmation", "Confirmación");

    }
    
    private static void initializeEnglishMessages() {
        
        englishMessages.put("title.main", "Main Menu");
        englishMessages.put("button.play", "1. Play");
        englishMessages.put("button.profile", "2. My Profile");
        englishMessages.put("button.ranking", "3. Ranking");
        englishMessages.put("button.settings", "4. Settings");
        englishMessages.put("button.logout", "5. Logout");
        englishMessages.put("dialog.confirmLogout", "Do you want to log out?");
        englishMessages.put("dialog.confirmation", "Confirmation");
        
        // Settings
        englishMessages.put("title.settings", "Settings");
        englishMessages.put("button.language", "Language");
        englishMessages.put("label.volume", "Volume");
        englishMessages.put("button.controls", "Controls");
        englishMessages.put("button.ncontrols", "Change Controls");
        englishMessages.put("button.back", "Back");
        englishMessages.put("dialog.selectLanguage", "Select a language");
        englishMessages.put("dialog.changeLanguage", "Change Language");
        englishMessages.put("dialog.languageChanged", "Language changed to");
        
        // Control Settings - Added missing entries
        englishMessages.put("title.controls", "Control Settings");
        englishMessages.put("button.save", "Save");
        englishMessages.put("button.default", "Default Controls");
        englishMessages.put("dialog.defaultControls", "Controls have been reset to default (WASD + R). Click Save to apply.");
        englishMessages.put("dialog.controlsSaved", "Controls saved successfully");
        englishMessages.put("dialog.info", "Information");
        
        // Controls
        englishMessages.put("controls.title", "Controls");
        englishMessages.put("controls.moveUp", "Move Up");
        englishMessages.put("controls.moveDown", "Move Down");
        englishMessages.put("controls.moveLeft", "Move Left");
        englishMessages.put("controls.moveRight", "Move Right");
        englishMessages.put("controls.reset", "Restart");
        englishMessages.put("profile.reset", "Restart : R");
        
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
        englishMessages.put("profile.name", "Name");
        englishMessages.put("profile.points", "Points");
        englishMessages.put("profile.creationDate", "Creation date");
        englishMessages.put("profile.lastLogin", "Last Login");
        englishMessages.put("profile.maxLevel", "Maximum level");
        englishMessages.put("profile.gamesPlayed", "Games played");
        englishMessages.put("profile.timePlayed", "Time played");
        englishMessages.put("profile.notAvailable", "Not available");
        englishMessages.put("button.changeAvatar", "Change Avatar");
        englishMessages.put("button.changePassword", "Change Password");
        englishMessages.put("button.deleteAccount", "Delete Account");
        englishMessages.put("dialog.confirmDeleteAccount", "Are you sure you want to delete your account?");
        englishMessages.put("dialog.accountDeleted", "Account successfully deleted");
        englishMessages.put("button.lastGames", "View last games");
    
        englishMessages.put("dialog.lastgames", "Last Games");
    englishMessages.put("dialog.completed", "Completed");
    englishMessages.put("dialog.notCompleted", "Not Completed");
    englishMessages.put("level", "Level");
    englishMessages.put("time", "Time");
    englishMessages.put("results", "Result");
    englishMessages.put("No hay partidas registradas", "No games recorded");
        
        

        
        // Avatar selection 
        englishMessages.put("title.selectAvatar", "Select Avatar");
        englishMessages.put("button.returnWithoutChanging", "Return without changing");
        englishMessages.put("dialog.avatarSelected", "Avatar successfully selected");
        englishMessages.put("dialog.avatarError", "Error copying the selected avatar");
        englishMessages.put("dialog.profileError", "Error returning to profile");
        englishMessages.put("dialog.success", "Success");
        englishMessages.put("dialog.error", "Error");
        
        //Game 
        englishMessages.put("game.level", "Level");
        englishMessages.put("game.restart", "Restart");
        englishMessages.put("game.exit", "Exit");
        englishMessages.put("game.timer", "Time");
        englishMessages.put("game.movements", "Movements");
        englishMessages.put("dialog.finish", "Congratulations, you have completed the entire game!");

        
        // Level completion messages
        englishMessages.put("dialog.levelCompleted", "Level completed!");
        englishMessages.put("dialog.congratulations", "Congratulations");
        englishMessages.put("dialog.nextLevelAvailable", "You can now proceed to the next level!");
        englishMessages.put("dialog.confirmExit", "Do you want to exit the level?");
        englishMessages.put("dialog.exitGame", "Exit Game");
        
        //Ranking 
        englishMessages.put("title.ranking", "PLAYER RANKING");
        englishMessages.put("ranking.noPlayers", "No registered players.");
        englishMessages.put("ranking.position", "Pos");
        englishMessages.put("ranking.user", "User");
        englishMessages.put("ranking.points", "Points");
        englishMessages.put("ranking.level", "Level");
        englishMessages.put("ranking.totalTime", "Total Time");
        
      // Para UserLogic
englishMessages.put("error.noActivePlayer", "There is no active player.");
englishMessages.put("error.title", "Error");
englishMessages.put("password.current", "Enter your current password:");
englishMessages.put("password.incorrect", "Incorrect password.");
englishMessages.put("password.tryAgain", "Would you like to try again?");
englishMessages.put("password.new", "Enter your new password:");
englishMessages.put("password.changeSuccess", "Password changed successfully.");
englishMessages.put("password.changeError", "Error changing password.");
englishMessages.put("account.deleteConfirm", "Are you sure you want to delete your account?");
englishMessages.put("account.deleteSuccess", "Account deleted successfully.");
englishMessages.put("account.deleteError", "Error deleting account.");
englishMessages.put("dialog.confirmation", "Confirmation");
    }
    
    private static void initializeItalianMessages() {
        italianMessages.put("title.main", "Menu Principale");
        italianMessages.put("button.play", "1. Giocare");
        italianMessages.put("button.profile", "2. Il Mio Profilo");
        italianMessages.put("button.ranking", "3. Classifiche");
        italianMessages.put("button.settings", "4. Configurazione");
        italianMessages.put("button.logout", "5. Uscire");
        italianMessages.put("dialog.confirmLogout", "Vuoi uscire?");
        italianMessages.put("dialog.confirmation", "Conferma");
        
        // Settings
        italianMessages.put("title.settings", "Impostazioni");
        italianMessages.put("button.language", "Lingua");
        italianMessages.put("label.volume", "Volume");
        italianMessages.put("button.controls", "Controlli");
        italianMessages.put("button.ncontrols", "Cambiare i Controlli");
        italianMessages.put("button.back", "Indietro");
        italianMessages.put("dialog.selectLanguage", "Seleziona una lingua");
        italianMessages.put("dialog.changeLanguage", "Cambia Lingua");
        italianMessages.put("dialog.languageChanged", "Lingua cambiata in");
        
        // Control Settings - Added missing entries
        italianMessages.put("title.controls", "Impostazioni Controlli");
        italianMessages.put("button.save", "Salvare");
        italianMessages.put("button.default", "Controlli Predefiniti");
        italianMessages.put("dialog.defaultControls", "I controlli sono stati reimpostati ai valori predefiniti (WASD + R). Fare clic su Salva per applicare.");
        italianMessages.put("dialog.controlsSaved", "Controlli salvati con successo");
        italianMessages.put("dialog.info", "Informazione");
        
        // Controls
        italianMessages.put("controls.title", "Controlli");
        italianMessages.put("controls.moveUp", "Muovi Su");
        italianMessages.put("controls.moveDown", "Muovi Giù");
        italianMessages.put("controls.moveLeft", "Muovi a Sinistra");
        italianMessages.put("controls.moveRight", "Muovi a Destra");
        italianMessages.put("controls.reset", "Ricomincia");
        italianMessages.put("profile.reset", "Ricomincia : R");
        
        // Levels
        italianMessages.put("title.selectLevel", "Seleziona Livello");
        italianMessages.put("level.1", "Livello 1");
        italianMessages.put("level.2", "Livello 2");
        italianMessages.put("level.3", "Livello 3");
        italianMessages.put("level.4", "Livello 4");
        italianMessages.put("level.5", "Livello 5");
        italianMessages.put("dialog.levelInDevelopment", "Questo livello è in fase di sviluppo");
        
        // Profile 
        italianMessages.put("title.profile", "Il Mio Profilo");
        italianMessages.put("profile.user", "Utente");
        italianMessages.put("profile.name", "Nome");
        italianMessages.put("profile.points", "Punti");
            italianMessages.put("profile.creationDate", "Data di creazione");
        italianMessages.put("profile.maxLevel", "Livello massimo");
        italianMessages.put("profile.gamesPlayed", "Partite giocate");
        italianMessages.put("profile.timePlayed", "Tempo di gioco");
        italianMessages.put("profile.notAvailable", "Non disponibile");
        italianMessages.put("button.changeAvatar", "Cambia Avatar");
        italianMessages.put("button.changePassword", "Cambia Password");
        italianMessages.put("button.deleteAccount", "Elimina Account");
        italianMessages.put("dialog.confirmDeleteAccount", "Sei sicuro di voler eliminare il tuo account?");
        italianMessages.put("dialog.accountDeleted", "Account eliminato con successo");
            italianMessages.put("button.lastGames", "Visualizza ultime partite");
  italianMessages.put("dialog.lastgames", "Ultime Partite");
    italianMessages.put("dialog.completed", "Completato");
    italianMessages.put("dialog.notCompleted", "Non Completato");
    italianMessages.put("level", "Livello");
    italianMessages.put("time", "Tempo");
    italianMessages.put("results", "Risultato");
    italianMessages.put("No hay partidas registradas", "Nessuna partita registrata");
       italianMessages.put("profile.lastLogin", "Ultimo Acceso");
        
        // Avatar selection 
        italianMessages.put("title.selectAvatar", "Seleziona Avatar");
        italianMessages.put("button.returnWithoutChanging", "Torna indietro senza cambiare");
        italianMessages.put("dialog.avatarSelected", "Avatar selezionato con successo");
        italianMessages.put("dialog.avatarError", "Errore durante la copia dell'avatar selezionato");
        italianMessages.put("dialog.profileError", "Errore durante il ritorno al profilo");
        italianMessages.put("dialog.success", "Successo");
        italianMessages.put("dialog.error", "Errore");
        
        //Game 
        italianMessages.put("game.level", "Livello");
        italianMessages.put("game.restart", "Ricomincia");
        italianMessages.put("game.exit", "Uscire");
        italianMessages.put("game.timer", "Tempo");
        italianMessages.put("game.movements", "Movimenti");
        italianMessages.put("dialog.finish", "Complimenti, hai completato l'intero gioco!");

        
        // Level completion messages
        italianMessages.put("dialog.levelCompleted", "Livello completato!");
        italianMessages.put("dialog.congratulations", "Congratulazioni");
        italianMessages.put("dialog.nextLevelAvailable", "Ora puoi procedere al livello successivo!");
        italianMessages.put("dialog.confirmExit", "Vuoi uscire dal livello?");
        italianMessages.put("dialog.exitGame", "Uscire dal Gioco");
        
        //Ranking
        italianMessages.put("title.ranking", "CLASSIFICA DEI GIOCATORI");
        italianMessages.put("ranking.noPlayers", "Nessun giocatore registrato.");
        italianMessages.put("ranking.position", "Pos");
        italianMessages.put("ranking.user", "Utente");
        italianMessages.put("ranking.points", "Punti");
        italianMessages.put("ranking.level", "Livello");
        italianMessages.put("ranking.totalTime", "Tempo Totale");
        
       // Para UserLogic
italianMessages.put("error.noActivePlayer", "Non c'è nessun giocatore attivo.");
italianMessages.put("error.title", "Errore");
italianMessages.put("password.current", "Inserisci la tua password attuale:");
italianMessages.put("password.incorrect", "Password errata.");
italianMessages.put("password.tryAgain", "Vuoi riprovare?");
italianMessages.put("password.new", "Inserisci la tua nuova password:");
italianMessages.put("password.changeSuccess", "Password cambiata con successo.");
italianMessages.put("password.changeError", "Errore durante il cambio della password.");
italianMessages.put("account.deleteConfirm", "Sei sicuro di voler eliminare il tuo account?");
italianMessages.put("account.deleteSuccess", "Account eliminato con successo.");
italianMessages.put("account.deleteError", "Errore durante l'eliminazione dell'account.");
italianMessages.put("dialog.confirmation", "Conferma");
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
        } else if(language.equals("Español")){
            currentLocale = new Locale("es", "ES");
            messages = createCustomBundle(spanishMessages);
        } else{
            currentLocale = new Locale("it", "IT");
            messages = createCustomBundle(italianMessages);
        }
    }
    
    public static void setLanguageForUser(String language, String usuario) {
        String langCode;
        
        if (language.equals("English")) {
            langCode = "en";
            currentLocale = new Locale("en", "US");
            messages = createCustomBundle(englishMessages);
        } else if(language.equals("Español")){
            langCode = "es";
            currentLocale = new Locale("es", "ES");
            messages = createCustomBundle(spanishMessages);
        } else {
            langCode = "it";
            currentLocale = new Locale("it", "IT");
            messages = createCustomBundle(italianMessages);
        }
        
        // Use the new LanguageFileManager instead of UserFile
        LanguageFileManager.saveLanguagePreference(usuario, langCode);
    }
    
    public static void loadLanguageForUser(String usuario) {
        // Use the new LanguageFileManager instead of UserFile
        String language = LanguageFileManager.getLanguagePreference(usuario);
        
        if (language.equals("en")) {
            currentLocale = new Locale("en", "US");
            messages = createCustomBundle(englishMessages);
        } else if(language.equals("es")) {
            currentLocale = new Locale("es", "ES");
            messages = createCustomBundle(spanishMessages);
        } else {
            currentLocale = new Locale("it", "IT");
            messages = createCustomBundle(italianMessages);
        }
    }
    
    public static ResourceBundle getMessages() {
        return messages;
    }
    
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
    
    public static void refreshMessageBundle() {
        String language = currentLocale.getLanguage();
        
        if (language.equals("en")) {
            messages = createCustomBundle(englishMessages);
        } else if (language.equals("es")) {
            messages = createCustomBundle(spanishMessages);
        } else {
            messages = createCustomBundle(italianMessages);
        }
    }
}