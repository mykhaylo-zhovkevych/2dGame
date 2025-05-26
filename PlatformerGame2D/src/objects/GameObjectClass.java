package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.GameClass;
import static utilts.ConstantsClass.ANI_SEED;
import static utilts.ConstantsClass.ObjectConstants.*;

public class GameObjectClass {
    
    protected int x, y, objType;
    protected Rectangle2D.Float hitbox; 
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObjectClass(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
        
    }


    protected void updateAmimationTick() {
		aniTick++;
		if (aniTick >= ANI_SEED) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(objType)) {
				aniIndex = 0;
                if (objType == BARREL || objType == BOX) {
                    doAnimation = false;
                    active = false;
                }
			}
		}
	}

    public void reset() {
        aniIndex = 0;
        aniTick = 0;
        active = true;

        if (objType == BARREL || objType == BOX) 
            doAnimation = false;
        else        
            doAnimation = true;
    }


    protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)(width * GameClass.SCALE), (int)(height * GameClass.SCALE));
		
	}

    public void drawHitbox(Graphics g, int xLvlOffset) {
		// For debugging the hitbox
		g.setColor(Color.RED);
		g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}


    public int getObjType() {
		return objType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}
    // when the object gets destroyed, i dont deactivated it inside the GameObkjectClass, but inside the ObjectManager
	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getAniIndex() {
		return aniIndex;
	}


    
    
}
