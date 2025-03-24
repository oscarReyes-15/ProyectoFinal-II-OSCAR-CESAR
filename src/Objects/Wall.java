package Objects;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


// Implementación para la pared
public class Wall extends GameObject {
    private static final long serialVersionUID = 1L;
    
    public Wall(int x, int y) {
        super(x, y, ObjectConstants.WALL);
    }
    
    @Override
    public void loadSprite() {
        try {
            this.sprite = ImageIO.read(new File("src/GameImages/muro1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}