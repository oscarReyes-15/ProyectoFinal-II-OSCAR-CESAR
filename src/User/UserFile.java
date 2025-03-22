package User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import SubMenuOption.LanguageManager;

public class UserFile {
    
    private static final String USER_DATA_FILE = "/datos.bin";
    
    public static boolean guardarDatosUsuario(String usuario, String password) {
        return guardarDatosUsuario(usuario, password, "", null, null);
    }
    
    public static boolean guardarDatosUsuario(String usuario, String password, String nombre) {
        return guardarDatosUsuario(usuario, password, nombre, null, null);
    }
    
    public static boolean guardarDatosUsuario(String usuario, String password, String nombre, 
                                             String language, char[] controls) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        // Get current controls if not provided
        if (controls == null) {
            controls = new char[] {'W', 'S', 'A', 'D', 'R'}; // Default controls
        }
        
        // Get current language if not provided
        if (language == null) {
            language = LanguageManager.getCurrentLocale().getLanguage();
        }
        
        File archivo = new File(usuario + USER_DATA_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            // User credentials
            salida.writeUTF(usuario);
            salida.writeUTF(password);
            salida.writeUTF(nombre);
            
            // User statistics
            salida.writeInt(0); // puntos
            salida.writeUTF(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)); // fechaCreacion
            salida.writeInt(0); // nivelMaximo
            salida.writeInt(0); // partidasJugadas
            salida.writeLong(0); // tiempoTotal
            
            // Language preference
            salida.writeUTF(language);
            
            // Control settings
            for (char key : controls) {
                salida.writeChar(key);
            }
            
            return true;
        } catch (IOException ex) {
            System.err.println("Error al guardar datos de usuario: " + ex.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarDatosUsuario(String usuario, int puntos, LocalDate fechaCreacion, 
                                               int nivelMaximo, int partidasJugadas, long tiempoTotal,
                                               String language, char[] controls) {
        // Load existing data first
        User user = cargarUsuario(usuario);
        if (user == null) {
            return false;
        }
        
        File archivo = new File(usuario + USER_DATA_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            // User credentials
            salida.writeUTF(usuario);
            salida.writeUTF(user.getPassword());
            salida.writeUTF(user.getNombre());
            
            // User statistics
            salida.writeInt(puntos);
            salida.writeUTF(fechaCreacion.format(DateTimeFormatter.ISO_LOCAL_DATE));
            salida.writeInt(nivelMaximo);
            salida.writeInt(partidasJugadas);
            salida.writeLong(tiempoTotal);
            
            // Language preference
            salida.writeUTF(language);
            
            // Control settings
            for (char key : controls) {
                salida.writeChar(key);
            }
            
            return true;
        } catch (IOException ex) {
            System.err.println("Error al actualizar datos de usuario: " + ex.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarTiempoJugado(String usuario, long segundosAdicionales) {
        User user = cargarUsuario(usuario);
        String language = getLanguageForUser(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            long tiempoActual = getTiempoTotal(usuario);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                              stats[1], stats[2], tiempoActual + segundosAdicionales,
                              language, controls);
        }
        return false;
    }
    
    public static boolean setNivelCompletado(String usuario, int nivel) {
        int[] stats = getEstadisticasUsuario(usuario);
        User user = cargarUsuario(usuario);
        String language = getLanguageForUser(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null && nivel > stats[1]) {
            stats[1] = nivel;
            return actualizarDatosUsuario(usuario, stats[0], user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           language, controls);
        }
        return true;
    }
    
    public static boolean incrementarPartidasJugadas(String usuario) {
        int[] stats = getEstadisticasUsuario(usuario);
        User user = cargarUsuario(usuario);
        String language = getLanguageForUser(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            stats[2]++;
            return actualizarDatosUsuario(usuario, stats[0], user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           language, controls);
        }
        return false;
    }
    
    public static boolean actualizarPuntos(String usuario, int puntosNuevos) {
        User user = cargarUsuario(usuario);
        String language = getLanguageForUser(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            user.addPuntos(puntosNuevos);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           language, controls);
        }
        return false;
    }
    
    public static boolean cambiarContraseña(String usuario, String nuevaPassword) {
        User user = cargarUsuario(usuario);
        String language = getLanguageForUser(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           language, controls);
        }
        return false;
    }
    
    public static boolean verificarCredenciales(String usuario, String password) {
        File archivo = new File(usuario + USER_DATA_FILE);
        if (!archivo.exists()) {
            return false;
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            String nombreArchivo = entrada.readUTF();
            String passwordArchivo = entrada.readUTF();
            return usuario.equals(nombreArchivo) && password.equals(passwordArchivo);
        } catch (IOException e) {
            System.err.println("Error al verificar credenciales: " + e.getMessage());
            return false;
        }
    }
    
    public static User cargarUsuario(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        
        if (!archivo.exists()) {
            return null;
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readUTF(); // usuario
            String password = entrada.readUTF();
            String nombre = entrada.readUTF();
            
            int puntos = entrada.readInt();
            LocalDate fechaCreacion = LocalDate.parse(entrada.readUTF(), DateTimeFormatter.ISO_LOCAL_DATE);
            
            User user = new User(usuario, password);
            user.setNombre(nombre);
            user.addPuntos(puntos);
            user.setFechaCreacion(fechaCreacion);
            
            return user;
        } catch (IOException e) {
            System.err.println("Error al cargar usuario: " + e.getMessage());
            return null;
        }
    }
    
    public static int[] getEstadisticasUsuario(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        int[] stats = new int[3]; // puntos, nivelMaximo, partidasJugadas
        
        if (!archivo.exists()) {
            return new int[]{0, 0, 0};
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readUTF(); // usuario
            entrada.readUTF(); // password
            entrada.readUTF(); // nombre
            
            stats[0] = entrada.readInt(); // puntos
            entrada.readUTF(); // fechaCreacion
            stats[1] = entrada.readInt(); // nivelMaximo
            stats[2] = entrada.readInt(); // partidasJugadas
            
            return stats;
        } catch (IOException e) {
            System.err.println("Error al cargar estadísticas de usuario: " + e.getMessage());
            return new int[]{0, 0, 0};
        }
    }
    
    public static long getTiempoTotal(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        
        if (!archivo.exists()) {
            return 0;
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readUTF(); // usuario
            entrada.readUTF(); // password
            entrada.readUTF(); // nombre
            entrada.readInt(); // puntos
            entrada.readUTF(); // fechaCreacion
            entrada.readInt(); // nivelMaximo
            entrada.readInt(); // partidasJugadas
            
            return entrada.readLong(); // tiempoTotal
        } catch (IOException e) {
            System.err.println("Error al cargar tiempo de usuario: " + e.getMessage());
            return 0;
        }
    }
    
    public static String getLanguageForUser(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        
        if (!archivo.exists()) {
            return "es"; // Default to Spanish
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readUTF(); // usuario
            entrada.readUTF(); // password
            entrada.readUTF(); // nombre
            entrada.readInt(); // puntos
            entrada.readUTF(); // fechaCreacion
            entrada.readInt(); // nivelMaximo
            entrada.readInt(); // partidasJugadas
            entrada.readLong(); // tiempoTotal
            
            return entrada.readUTF(); // language
        } catch (IOException e) {
            System.err.println("Error al cargar idioma de usuario: " + e.getMessage());
            return "es"; // Default to Spanish
        }
    }
    
    public static char[] getControlsForUser(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        char[] controls = new char[] {'W', 'S', 'A', 'D', 'R'}; // Default controls
        
        if (!archivo.exists()) {
            return controls;
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readUTF(); // usuario
            entrada.readUTF(); // password
            entrada.readUTF(); // nombre
            entrada.readInt(); // puntos
            entrada.readUTF(); // fechaCreacion
            entrada.readInt(); // nivelMaximo
            entrada.readInt(); // partidasJugadas
            entrada.readLong(); // tiempoTotal
            entrada.readUTF(); // language
            
            for (int i = 0; i < 5; i++) {
                controls[i] = entrada.readChar();
            }
            
            return controls;
        } catch (IOException e) {
            System.err.println("Error al cargar controles de usuario: " + e.getMessage());
            return controls; // Return default controls
        }
    }
    
    public static boolean setLanguageForUser(String usuario, String language) {
        User user = cargarUsuario(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           language, controls);
        }
        return false;
    }
    
    public static boolean setControlsForUser(String usuario, char[] controls) {
        User user = cargarUsuario(usuario);
        String language = getLanguageForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           language, controls);
        }
        return false;
    }
    
    public static boolean existeUsuario(String usuario) {
        File directorio = new File(usuario);
        File archivoDatos = new File(usuario + USER_DATA_FILE);
        return directorio.exists() && archivoDatos.exists();
    }
    
    public static int getNivelMaximo(String usuario) {
        int[] stats = getEstadisticasUsuario(usuario);
        return stats[1];
    }
    
    public static int getPartidasJugadas(String usuario) {
        int[] stats = getEstadisticasUsuario(usuario);
        return stats[2];
    }
    
    public static String getTiempoFormateado(String usuario) {
        long seconds = getTiempoTotal(usuario);
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
    
    public static boolean hasCompletedLevel(String username, int level) {
        if (username == null) {
            return false;
        }
        
        int maxLevel = getNivelMaximo(username);
        return maxLevel >= level;
    }
}