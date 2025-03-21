
package User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserFile {
    
    private static final String USER_DATA_FILE = "/datos.bin";
    private static final String USER_STATS_FILE = "/stats.bin";
    
    public static boolean guardarDatosUsuario(String usuario, String password) {
        return guardarDatosUsuario(usuario, password, "");
    }
    
    public static boolean guardarDatosUsuario(String usuario, String password, String nombre) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        File archivo = new File(usuario + USER_DATA_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeUTF(usuario);
            salida.writeUTF(password);
            salida.writeUTF(nombre); 
            guardarEstadisticasUsuario(usuario, 0, LocalDate.now(), 0, 0, 0); // Added 0 for tiempoTotal
            return true;
        } catch (IOException ex) {
            System.err.println("Error al guardar datos de usuario: " + ex.getMessage());
            return false;
        }
    }
    
    public static boolean guardarEstadisticasUsuario(String usuario, int puntos, LocalDate fechaCreacion, 
                                                    int nivelMaximo, int partidasJugadas, long tiempoTotal) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        File archivo = new File(usuario + USER_STATS_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeInt(puntos);
            salida.writeUTF(fechaCreacion.format(DateTimeFormatter.ISO_LOCAL_DATE));
            salida.writeInt(nivelMaximo);
            salida.writeInt(partidasJugadas);
            salida.writeLong(tiempoTotal); // Added time tracking in seconds
            return true;
        } catch (IOException ex) {
            System.err.println("Error al guardar estadísticas de usuario: " + ex.getMessage());
            return false;
        }
    }
    
    public static int[] cargarEstadisticasUsuario(String usuario) {
        File archivo = new File(usuario + USER_STATS_FILE);
        int[] stats = new int[3]; 
        LocalDate fechaCreacion = LocalDate.now();
        
        if (!archivo.exists()) {
            return new int[]{0, 0, 0};
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            stats[0] = entrada.readInt(); // puntos
            fechaCreacion = LocalDate.parse(entrada.readUTF(), DateTimeFormatter.ISO_LOCAL_DATE);
            stats[1] = entrada.readInt(); // nivel máximo
            stats[2] = entrada.readInt(); // partidas jugadas
            
            // Try to read time if it exists
            try {
                long tiempoTotal = entrada.readLong();
                // We could expand the array to include this but for compatibility we'll keep it as is
            } catch (EOFException e) {
                // Older file format without time data, ignore
            }
            
            return stats;
        } catch (IOException ex) {
            System.err.println("Error al cargar estadísticas de usuario: " + ex.getMessage());
            return new int[]{0, 0, 0};
        }
    }
    
    public static long getTiempoTotal(String usuario) {
        File archivo = new File(usuario + USER_STATS_FILE);
        
        if (!archivo.exists()) {
            return 0;
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readInt(); // Skip puntos
            entrada.readUTF(); // Skip fechaCreacion
            entrada.readInt(); // Skip nivelMaximo
            entrada.readInt(); // Skip partidasJugadas
            
            try {
                return entrada.readLong(); // Read tiempoTotal
            } catch (EOFException e) {
                // Older file format without time data
                return 0;
            }
        } catch (IOException ex) {
            System.err.println("Error al cargar tiempo de usuario: " + ex.getMessage());
            return 0;
        }
    }
    
    public static boolean actualizarTiempoJugado(String usuario, long segundosAdicionales) {
        User user = cargarUsuario(usuario);
        int[] stats = cargarEstadisticasUsuario(usuario);
        long tiempoActual = getTiempoTotal(usuario);
        
        if (user != null) {
            return guardarEstadisticasUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                              stats[1], stats[2], tiempoActual + segundosAdicionales);
        }
        return false;
    }
    
    public static boolean setNivelCompletado(String usuario, int nivel) {
        int[] stats = cargarEstadisticasUsuario(usuario);
        User user = cargarUsuario(usuario);
        long tiempoTotal = getTiempoTotal(usuario);
        
        if (nivel > stats[1]) {
            stats[1] = nivel;
            return guardarEstadisticasUsuario(usuario, stats[0], user.getFechaCreacion(), stats[1], stats[2], tiempoTotal);
        }
        return true;
    }
    
    public static boolean incrementarPartidasJugadas(String usuario) {
        int[] stats = cargarEstadisticasUsuario(usuario);
        User user = cargarUsuario(usuario);
        long tiempoTotal = getTiempoTotal(usuario);
        stats[2]++;
        return guardarEstadisticasUsuario(usuario, stats[0], user.getFechaCreacion(), stats[1], stats[2], tiempoTotal);
    }
    
    public static boolean actualizarPuntos(String usuario, int puntosNuevos) {
        User user = cargarUsuario(usuario);
        int[] stats = cargarEstadisticasUsuario(usuario);
        long tiempoTotal = getTiempoTotal(usuario);
        
        if (user != null) {
            user.addPuntos(puntosNuevos);
            return guardarEstadisticasUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), stats[1], stats[2], tiempoTotal);
        }
        return false;
    }
    
    public static boolean cambiarContraseña(String usuario, String nuevaPassword) {
        User user = cargarUsuario(usuario);
        int[] stats = cargarEstadisticasUsuario(usuario);
        long tiempoTotal = getTiempoTotal(usuario);
        
        if (user == null) {
            return false;
        }
        
        String nombre = "";
        try {
            nombre = user.getNombre();
        } catch (Exception e) {
            nombre = "";
        }
        
        return guardarDatosUsuario(usuario, nuevaPassword, nombre) && guardarEstadisticasUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                                        stats[1], stats[2], tiempoTotal);
    }
    
    // Rest of the methods remain unchanged
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
            String nombre = "";
            try (DataInputStream entradaCredenciales = new DataInputStream(new FileInputStream(archivoCredenciales))) {
                entradaCredenciales.readUTF(); 
                password = entradaCredenciales.readUTF();
                
                try {
                    nombre = entradaCredenciales.readUTF();
                } catch (EOFException e) {
                    nombre = "";
                }
            }
            
            int puntos;
            LocalDate fechaCreacion;
            try (DataInputStream entradaEstadisticas = new DataInputStream(new FileInputStream(archivoEstadisticas))) {
                puntos = entradaEstadisticas.readInt();
                fechaCreacion = LocalDate.parse(entradaEstadisticas.readUTF(), DateTimeFormatter.ISO_LOCAL_DATE);
               
                entradaEstadisticas.readInt(); 
                entradaEstadisticas.readInt(); 
            }
            
            User user = new User(usuario, password);
            
            try {
                user.setNombre(nombre);
            } catch (Exception e) {
            }
            
            user.addPuntos(puntos);
            user.setFechaCreacion(fechaCreacion);
            
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
    // Add these methods to your UserFile class

/**
 * Gets the maximum level reached by the user
 * @param usuario Username to check
 * @return Maximum level reached or 0 if not found
 */
public static int getNivelMaximo(String usuario) {
    int[] stats = cargarEstadisticasUsuario(usuario);
    return stats[1]; // The nivel máximo is at index 1 in the stats array
}

public static int getPartidasJugadas(String usuario) {
    int[] stats = cargarEstadisticasUsuario(usuario);
    return stats[2]; // The partidas jugadas is at index 2 in the stats array
}


public static String getTiempoFormateado(String usuario) {
    long seconds = getTiempoTotal(usuario);
    long hours = seconds / 3600;
    long minutes = (seconds % 3600) / 60;
    long remainingSeconds = seconds % 60;
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
}

}

