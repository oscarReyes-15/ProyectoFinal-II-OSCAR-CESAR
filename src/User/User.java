
package User;

import java.time.LocalDate;

public class User {
    private String usuario;
    private String password;
    private String nombre;
    private int puntos;
    private LocalDate fechaCreacion;
    private long tiempoJugado; // Added field for play time in seconds
    
    public User(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.puntos = 0;
        this.fechaCreacion = LocalDate.now();
        this.nombre = "";
        this.tiempoJugado = 0;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void addPuntos(int puntos) {
        this.puntos += puntos;
    }
    
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // New methods for time tracking
    public long getTiempoJugado() {
        return tiempoJugado;
    }
    
    public void setTiempoJugado(long segundos) {
        this.tiempoJugado = segundos;
    }
    
    public void addTiempoJugado(long segundos) {
        this.tiempoJugado += segundos;
    }
    
    // Method to get formatted time string
    public String getTiempoFormateado() {
        long hours = tiempoJugado / 3600;
        long minutes = (tiempoJugado % 3600) / 60;
        long seconds = tiempoJugado % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
