package User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import SubMenuOption.LanguageManager;
import SubMenuOption.LanguageFileManager;

public class UserFile {
    
    private static final String USER_DATA_FILE = "/datos.bin";
    
    public static boolean guardarDatosUsuario(String usuario, String password) {
        return guardarDatosUsuario(usuario, password, "", null);
    }
    
    public static boolean guardarDatosUsuario(String usuario, String password, String nombre) {
        return guardarDatosUsuario(usuario, password, nombre, null);
    }
    
    public static boolean guardarDatosUsuario(String usuario, String password, String nombre, char[] controls) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        
        if (controls == null) {
            controls = new char[] {'W', 'S', 'A', 'D', 'R'}; 
        }
        
        // Save default language in separate file
        String language = LanguageManager.getCurrentLocale().getLanguage();
        LanguageFileManager.saveLanguagePreference(usuario, language);
        
        File archivo = new File(usuario + USER_DATA_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeUTF(usuario);
            salida.writeUTF(password);
            salida.writeUTF(nombre);
            
            salida.writeInt(0); 
            salida.writeUTF(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)); // fechaCreacion
            salida.writeInt(0); 
            salida.writeInt(0); 
            salida.writeLong(0); 
            
            for (char key : controls) {
                salida.writeChar(key);
            }
            
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean actualizarDatosUsuario(String usuario, int puntos, LocalDate fechaCreacion, 
                                               int nivelMaximo, int partidasJugadas, long tiempoTotal,
                                               char[] controls) {
        User user = cargarUsuario(usuario);
        if (user == null) {
            return false;
        }

        File archivo = new File(usuario + USER_DATA_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeUTF(usuario);
            salida.writeUTF(user.getPassword());
            salida.writeUTF(user.getNombre());
            
            salida.writeInt(puntos);
            salida.writeUTF(fechaCreacion.format(DateTimeFormatter.ISO_LOCAL_DATE));
            salida.writeInt(nivelMaximo);
            salida.writeInt(partidasJugadas);
            salida.writeLong(tiempoTotal);
            
            for (char key : controls) {
                salida.writeChar(key);
            }
            
            return true;
        } catch (IOException ex) {
            System.err.println("Error al actualizar datos de usuario: " + ex.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarDatosUsuario(String usuario, String password, String nombre, int puntos, 
                                               LocalDate fechaCreacion, int nivelMaximo, int partidasJugadas, 
                                               long tiempoTotal, char[] controls) {
        File archivo = new File(usuario + USER_DATA_FILE);
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo))) {
            salida.writeUTF(usuario);
            salida.writeUTF(password);
            salida.writeUTF(nombre);
            
            salida.writeInt(puntos);
            salida.writeUTF(fechaCreacion.format(DateTimeFormatter.ISO_LOCAL_DATE));
            salida.writeInt(nivelMaximo);
            salida.writeInt(partidasJugadas);
            salida.writeLong(tiempoTotal);
            
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
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            long tiempoActual = getTiempoTotal(usuario);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                              stats[1], stats[2], tiempoActual + segundosAdicionales,
                              controls);
        }
        return false;
    }
    
    public static boolean setNivelCompletado(String usuario, int nivel) {
        int[] stats = getEstadisticasUsuario(usuario);
        User user = cargarUsuario(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null && nivel > stats[1]) {
            stats[1] = nivel;
            return actualizarDatosUsuario(usuario, stats[0], user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           controls);
        }
        return true;
    }
    
    public static boolean incrementarPartidasJugadas(String usuario) {
        int[] stats = getEstadisticasUsuario(usuario);
        User user = cargarUsuario(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            stats[2]++;
            return actualizarDatosUsuario(usuario, stats[0], user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           controls);
        }
        return false;
    }
    
    public static boolean actualizarPuntos(String usuario, int puntosNuevos) {
        User user = cargarUsuario(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            user.addPuntos(puntosNuevos);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           controls);
        }
        return false;
    }
    
    public static boolean cambiarContraseña(String usuario, String nuevaPassword) {
        User user = cargarUsuario(usuario);
        char[] controls = getControlsForUser(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            
            // Guardamos los datos con la nueva contraseña
            return actualizarDatosUsuario(usuario, nuevaPassword, user.getNombre(), user.getPuntos(), 
                       user.getFechaCreacion(), stats[1], stats[2], getTiempoTotal(usuario),
                       controls);
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
            entrada.readUTF();
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
            return null;
        }
    }
    
    public static int[] getEstadisticasUsuario(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        int[] stats = new int[3]; 
        
        if (!archivo.exists()) {
            return new int[]{0, 0, 0};
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readUTF(); 
            entrada.readUTF(); 
            entrada.readUTF(); 
            
            stats[0] = entrada.readInt(); 
            entrada.readUTF();
            stats[1] = entrada.readInt();
            stats[2] = entrada.readInt(); 
            
            return stats;
        } catch (IOException e) {
            return new int[]{0, 0, 0};
        }
    }
    
    public static long getTiempoTotal(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        
        if (!archivo.exists()) {
            return 0;
        }
        
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(archivo))) {
            entrada.readUTF(); 
            entrada.readUTF(); 
            entrada.readUTF(); 
            entrada.readInt(); 
            entrada.readUTF(); 
            entrada.readInt(); 
            entrada.readInt(); 
            
            return entrada.readLong(); 
        } catch (IOException e) {
            return 0;
        }
    }
    
    public static char[] getControlsForUser(String usuario) {
        File archivo = new File(usuario + USER_DATA_FILE);
        char[] controls = new char[] {'W', 'S', 'A', 'D', 'R'}; 
        
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
            
            for (int i = 0; i < 5; i++) {
                controls[i] = entrada.readChar();
            }
            
            return controls;
        } catch (IOException e) {
            System.err.println("Error al cargar controles de usuario: " + e.getMessage());
            return controls; // Return default controls
        }
    }
    
    public static boolean setControlsForUser(String usuario, char[] controls) {
        User user = cargarUsuario(usuario);
        
        if (user != null) {
            int[] stats = getEstadisticasUsuario(usuario);
            
            return actualizarDatosUsuario(usuario, user.getPuntos(), user.getFechaCreacion(), 
                           stats[1], stats[2], getTiempoTotal(usuario),
                           controls);
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