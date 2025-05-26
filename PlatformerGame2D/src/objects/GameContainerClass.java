package objects;
import static utilts.ConstantsClass.ObjectConstants.*;

import main.GameClass;
public class GameContainerClass extends GameObjectClass {

    public GameContainerClass(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }
    //Edited By Rudy
    private void createHitbox(){

        if(objType == BOX){
            initHitbox(25, 18);

            xDrawOffset = (int) (7* GameClass.SCALE);
            yDrawOffset = (int) (12* GameClass.SCALE);

        } else{
            initHitbox(23, 25);

            xDrawOffset = (int) (8* GameClass.SCALE);
            yDrawOffset = (int) (5* GameClass.SCALE);
        }
        hitbox.y += yDrawOffset + (int) (GameClass.SCALE *2);
        hitbox.x += xDrawOffset / 2;
    }

    public void update(){
        if(doAnimation){
            updateAmimationTick();
        }
    }


}