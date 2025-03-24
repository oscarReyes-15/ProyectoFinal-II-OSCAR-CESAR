package Objects;


import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Implementación para el objetivo
public class Target extends GameObject {
    private static final long serialVersionUID = 1L;
    
    public Target(int x, int y) {
        super(x, y, ObjectConstants.TARGET);
    }
    
    @Override
    public void loadSprite() {
        try {
            this.sprite = ImageIO.read(new File("src/GameImages/target.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
