package Objects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Box class
public class Box extends GameObject {
    private static final long serialVersionUID = 1L;
    private boolean onTarget;
    
    public Box(int x, int y) {
        super(x, y, ObjectConstants.BOX);
        this.onTarget = false;
    }
    
    public boolean isOnTarget() {
        return onTarget;
    }
    
    public void setOnTarget(boolean onTarget) {
        this.onTarget = onTarget;
    }
    
    @Override
    public void loadSprite() {
        try {
            this.sprite = ImageIO.read(new File("src/GameImages/caja.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}