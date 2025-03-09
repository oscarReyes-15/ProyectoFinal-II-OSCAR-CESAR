package User;
import java.time.LocalDate;

public class User {
    
    private final String usuario;
    private String contra;
    private int puntos;
    private LocalDate fechaCreacion;
    private static int contadorJugadores = 0;
    
    public User(String usuario, String contra) {
        this.usuario = usuario;
        this.contra = contra;
        this.puntos = 0;
        this.fechaCreacion = LocalDate.now();
        contadorJugadores++;
    }
    
    public int getContadorJugadores() {
        return contadorJugadores;
    }
    
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDate fecha) {
        this.fechaCreacion = fecha;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public String getContra() {
        return contra;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    public void addPuntos(int puntos) {
        this.puntos += puntos;
    }
    
    public void setContra(String nuevaContra) {
        this.contra = nuevaContra;
    }
}