package User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserFile {
    
    private static final String USER_DATA_FILE = "/datos.bin";
    private static final String USER_STATS_FILE = "/stats.bin";
    private static final String USER_GAME_STATS_FILE = "/gamestats.bin";
    
    
    public static boolean guardarDatosUsuario(String usuario, String password) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        File archivo = new File(usuario + USER_DATA_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeUTF(usuario);
            salida.writeUTF(password);
            guardarEstadisticasUsuario(usuario, 0, LocalDate.now());
            guardarEstadisticasJuego(usuario, 0, 0); // Initialize game stats
            return true;
        } catch (IOException ex) {
            System.err.println("Error al guardar datos de usuario: " + ex.getMessage());
            return false;
        }
    }
    
 
    public static boolean guardarEstadisticasUsuario(String usuario, int puntos, LocalDate fechaCreacion) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        File archivo = new File(usuario + USER_STATS_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeInt(puntos);
            salida.writeUTF(fechaCreacion.format(DateTimeFormatter.ISO_LOCAL_DATE));
            return true;
        } catch (IOException ex) {
            System.err.println("Error al guardar estadísticas de usuario: " + ex.getMessage());
            return false;
        }
    }
    
    // New method to save game statistics
    public static boolean guardarEstadisticasJuego(String usuario, int nivelMaximo, int partidasJugadas) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        File archivo = new File(usuario + USER_GAME_STATS_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeInt(nivelMaximo);
            salida.writeInt(partidasJugadas);
            return true;
        } catch (IOException ex) {
            System.err.println("Error al guardar estadísticas de juego: " + ex.getMessage());
            return false;
        }
    }
    
    // Method to load game statistics
    public static int[] cargarEstadisticasJuego(String usuario) {
        File archivo = new File(usuario + USER_GAME_STATS_FILE);
        int[] stats = new int[2]; // [nivelMaximo, partidasJugadas]
        
        if (!archivo.exists()) {
            return new int[]{0, 0};
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            stats[0] = entrada.readInt(); // nivelMaximo
            stats[1] = entrada.readInt(); // partidasJugadas
            return stats;
        } catch (IOException ex) {
            System.err.println("Error al cargar estadísticas de juego: " + ex.getMessage());
            return new int[]{0, 0};
        }
    }
    
    // Method to set completed level
    public static boolean setNivelCompletado(String usuario, int nivel) {
        int[] stats = cargarEstadisticasJuego(usuario);
        if (nivel > stats[0]) {
            stats[0] = nivel;
            return guardarEstadisticasJuego(usuario, stats[0], stats[1]);
        }
        return true;
    }
    
    // Method to increment played games count
    public static boolean incrementarPartidasJugadas(String usuario) {
        int[] stats = cargarEstadisticasJuego(usuario);
        stats[1]++;
        return guardarEstadisticasJuego(usuario, stats[0], stats[1]);
    }
    
    // Method to get max unlocked level
    public static int getNivelMaximo(String usuario) {
        return cargarEstadisticasJuego(usuario)[0];
    }
    
    // Method to get played games count
    public static int getPartidasJugadas(String usuario) {
        return cargarEstadisticasJuego(usuario)[1];
    }

    public static boolean actualizarPuntos(String usuario, int puntosNuevos) {
        User user = cargarUsuario(usuario);
        if (user != null) {
            user.addPuntos(puntosNuevos);
            return guardarEstadisticasUsuario(usuario, user.getPuntos(), user.getFechaCreacion());
        }
        return false;
    }
    
   
    public static boolean cambiarContraseña(String usuario, String nuevaPassword) {
      
        User user = cargarUsuario(usuario);
        if (user == null) {
            return false;
        }
        
        return guardarDatosUsuario(usuario, nuevaPassword) && 
               guardarEstadisticasUsuario(usuario, user.getPuntos(), user.getFechaCreacion());
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
        File archivoCredenciales = new File(usuario + USER_DATA_FILE);
        File archivoEstadisticas = new File(usuario + USER_STATS_FILE);
        
        if (!archivoCredenciales.exists() || !archivoEstadisticas.exists()) {
            return null;
        }
        
        try {
          
            String password;
            try (DataInputStream entradaCredenciales = new DataInputStream(new FileInputStream(archivoCredenciales))) {
                entradaCredenciales.readUTF(); 
                password = entradaCredenciales.readUTF();
            }
            
            int puntos;
            LocalDate fechaCreacion;
            try (DataInputStream entradaEstadisticas = new DataInputStream(new FileInputStream(archivoEstadisticas))) {
                puntos = entradaEstadisticas.readInt();
                fechaCreacion = LocalDate.parse(entradaEstadisticas.readUTF(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
            
            User user = new User(usuario, password);
            user.addPuntos(puntos);
            
            
            return user;
        } catch (IOException e) {
            System.err.println("Error al cargar usuario: " + e.getMessage());
            return null;
        }
    }
    
   
    public static boolean existeUsuario(String usuario) {
        File directorio = new File(usuario);
        File archivoDatos = new File(usuario + USER_DATA_FILE);
        return directorio.exists() && archivoDatos.exists();
    }
}