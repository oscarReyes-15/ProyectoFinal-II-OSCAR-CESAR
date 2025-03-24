package User;

import java.io.*;

public class GameHistory {
    
    private static final String HISTORY_FILE = "/historial.dat";
    
    /**
     * Guarda el registro de una partida en el historial del usuario
     * 
     * @param usuario Nombre de usuario
     * @param nivel Nivel jugado
     * @param tiempoSegundos Tiempo de duración en segundos
     * @param completado Si el nivel fue completado o no
     * @return true si se guardó correctamente, false en caso contrario
     */
    public static boolean registrarPartida(String usuario, int nivel, long tiempoSegundos, boolean completado) {
        File directorio = new File(usuario);
        if (!directorio.exists()) {
            return false;
        }
        
        File historialFile = new File(usuario + HISTORY_FILE);
        
        try {
            // Leer partidas existentes y almacenarlas temporalmente
            ByteArrayOutputStream tempData = new ByteArrayOutputStream();
            DataOutputStream tempOutput = new DataOutputStream(tempData);
            
            // Primero escribimos la nueva partida
            tempOutput.writeInt(nivel);
            tempOutput.writeLong(tiempoSegundos);
            tempOutput.writeBoolean(completado);
            
            // Luego copiamos las partidas existentes (hasta un máximo de 9 para mantener solo 10)
            if (historialFile.exists()) {
                try (DataInputStream entrada = new DataInputStream(new FileInputStream(historialFile))) {
                    int contador = 0;
                    while (entrada.available() > 0 && contador < 9) {
                        tempOutput.writeInt(entrada.readInt());  // nivel
                        tempOutput.writeLong(entrada.readLong());  // tiempo
                        tempOutput.writeBoolean(entrada.readBoolean());  // completado
                        contador++;
                    }
                } catch (IOException e) {
                    System.err.println("Error al leer historial existente: " + e.getMessage());
                }
            }
            
            // Ahora escribimos todo al archivo
            try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(historialFile))) {
                salida.write(tempData.toByteArray());
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error al registrar partida: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene todas las partidas del historial de un usuario
     * 
     * @param usuario Nombre de usuario
     * @return Arreglo con información de las partidas [nivel, tiempo, completado]
     */
    public static Object[][] obtenerHistorial(String usuario) {
        File historialFile = new File(usuario + HISTORY_FILE);
        
        if (!historialFile.exists()) {
            return new Object[0][3];
        }
        
        try {
            // Primero contamos cuántas partidas hay
            int numPartidas = 0;
            try (DataInputStream contador = new DataInputStream(new FileInputStream(historialFile))) {
                while (contador.available() > 0) {
                    contador.readInt();  // nivel
                    contador.readLong();  // tiempo
                    contador.readBoolean();  // completado
                    numPartidas++;
                }
            }
            
            // Ahora leemos las partidas
            Object[][] partidas = new Object[numPartidas][3];
            try (DataInputStream entrada = new DataInputStream(new FileInputStream(historialFile))) {
                for (int i = 0; i < numPartidas; i++) {
                    partidas[i][0] = entrada.readInt();  // nivel
                    partidas[i][1] = entrada.readLong();  // tiempo
                    partidas[i][2] = entrada.readBoolean();  // completado
                }
            }
            
            return partidas;
        } catch (IOException e) {
            System.err.println("Error al obtener historial: " + e.getMessage());
            return new Object[0][3];
        }
    }
    
    /**
     * Formatea el tiempo en segundos a formato hh:mm:ss
     * 
     * @param segundos Tiempo en segundos
     * @return Cadena formateada
     */
    public static String formatearTiempo(long segundos) {
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long segs = segundos % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segs);
    }
    
    /**
     * Elimina el historial de partidas de un usuario
     * 
     * @param usuario Nombre de usuario
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public static boolean eliminarHistorial(String usuario) {
        File historialFile = new File(usuario + HISTORY_FILE);
        
        if (historialFile.exists()) {
            return historialFile.delete();
        }
        
        return true;
    }
}