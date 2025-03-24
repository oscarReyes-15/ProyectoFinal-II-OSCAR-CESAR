
package User;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private String usuario;
    private String password;
    private String nombre;
    private int puntos;
    private LocalDateTime fechaCreacion; // Cambiado de LocalDate a LocalDateTime
    private LocalDateTime ultimoLogin;   // Nuevo campo para el último login
    private long tiempoJugado; 
    
    public User(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.puntos = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimoLogin = LocalDateTime.now();
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
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }
    
    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public long getTiempoJugado() {
        return tiempoJugado;
    }
    
    public void setTiempoJugado(long segundos) {
        this.tiempoJugado = segundos;
    }
    
    public void addTiempoJugado(long segundos) {
        this.tiempoJugado += segundos;
    }
    
    public String getTiempoFormateado() {
        long hours = tiempoJugado / 3600;
        long minutes = (tiempoJugado % 3600) / 60;
        long seconds = tiempoJugado % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}