package Objects;

import java.io.Serializable;
import java.awt.Image;
import java.awt.image.BufferedImage;

// Clase base para todos los objetos del juego
public abstract class GameObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int x;
    protected int y;
    protected transient Image sprite; // transient porque Image no es serializable
    protected int type;
    
    public GameObject(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    // Métodos getters y setters
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    
    // Método para cargar la imagen después de deserializar
    public abstract void loadSprite();
    
    public Image getSprite() { return sprite; }
}




