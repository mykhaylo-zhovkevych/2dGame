package objects;

import main.GameClass;

public class PotionClass extends GameObjectClass {

    public PotionClass(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(7, 14);
        xDrawOffset = (int)(3 * GameClass.SCALE);
        yDrawOffset = (int)(2 * GameClass.SCALE);

    }

    public void update() {
        updateAmimationTick();
    }

 
    
}
