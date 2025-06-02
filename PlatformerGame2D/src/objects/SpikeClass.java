package objects;

import main.GameClass;

public class SpikeClass extends GameObjectClass {
    

    public SpikeClass(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int) (GameClass.SCALE * 16);
        hitbox.y += yDrawOffset;
    }

}
