package objects;

import main.GameClass;

public class CannonClass extends GameObjectClass {
    
    private int tileY;

    public CannonClass(int x, int y, int objType) {

        super (x, y, objType);
        tileY = y / GameClass.TILES_SIZE;
        initHitbox(40, 26);
        // this is for the cannon to be on the ground
        hitbox.x += (int)(4 * GameClass.SCALE);
        hitbox.y += (int)(6*GameClass.SCALE);


    }


    public void update() {
        if (doAnimation) {
            updateAmimationTick();
        }
    }

    public int getTileY() {
        return tileY;
    }

}

