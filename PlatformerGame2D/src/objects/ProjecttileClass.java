package objects;

import java.awt.geom.Rectangle2D;

import main.GameClass;
import static utilts.ConstantsClass.Projecttiles.CANNON_BALL_HEIGHT;
import static utilts.ConstantsClass.Projecttiles.CANNON_BALL_WIDTH;
import static utilts.ConstantsClass.Projecttiles.SPEED;

public class ProjecttileClass {
    private Rectangle2D.Float hitbox;
    // speed * dir => dir -1 left, dir 1 right
    private int dir;
    private boolean active = true;


    public ProjecttileClass(int x, int y, int dir) {

        int xOffset = (int)(-3 * GameClass.SCALE);
        int yOffset = (int)(5 * GameClass.SCALE);


        if (dir == 1) {
            xOffset = (int)(29 * GameClass.SCALE);
        } 

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
        this.dir = dir;
    }

    public void updatePos() {
        hitbox.x += dir * SPEED;

    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;

    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean  isActive() {

        return active;
    }

}