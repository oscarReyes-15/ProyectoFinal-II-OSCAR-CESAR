package SubMenuOption;

import java.io.*;


public class LanguageFileManager {
    
    private static final String LANGUAGE_FILE_SUFFIX = "/language.dat";
    

    public static boolean saveLanguagePreference(String username, String language) {
        if (username == null || language == null) {
            return false;
        }
        
        File directory = new File(username);
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        File languageFile = new File(username + LANGUAGE_FILE_SUFFIX);
        
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(languageFile))) {
            out.writeUTF(language);
            return true;
        } catch (IOException ex) {
            System.err.println("Error saving language preference: " + ex.getMessage());
            return false;
        }
    }
    
 
    public static String getLanguagePreference(String username) {
        if (username == null) {
            return "es"; // Default to Spanish
        }
        
        File languageFile = new File(username + LANGUAGE_FILE_SUFFIX);
        
        if (!languageFile.exists()) {
            return "es"; // Default to Spanish
        }
        
        try (DataInputStream in = new DataInputStream(new FileInputStream(languageFile))) {
            return in.readUTF();
        } catch (IOException ex) {
            System.err.println("Error reading language preference: " + ex.getMessage());
            return "es"; // Default to Spanish on error
        }
    }
 
    public static boolean languageFileExists(String username) {
        if (username == null) {
            return false;
        }
        
        File languageFile = new File(username + LANGUAGE_FILE_SUFFIX);
        return languageFile.exists();
    }

    public static boolean deleteLanguageFile(String username) {
        if (username == null) {
            return false;
        }
        
        File languageFile = new File(username + LANGUAGE_FILE_SUFFIX);
        
        if (languageFile.exists()) {
            return languageFile.delete();
        }
        
        return true; 
    }
}