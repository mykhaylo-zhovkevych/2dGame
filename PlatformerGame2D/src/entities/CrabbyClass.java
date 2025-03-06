package entities;

import main.GameClass;
import static utilts.ConstantsClass.EnemyConstants.*;

public class CrabbyClass extends Enemy{

	public CrabbyClass(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		// 22 / 19 is the size of the hitbox that 
		initHitbox(x, y, (int)(22 * GameClass.SCALE),(int)(19 * GameClass.SCALE));
		
	}

}
