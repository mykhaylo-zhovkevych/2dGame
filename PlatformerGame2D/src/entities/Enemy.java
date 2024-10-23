package entities;

import static utilts.ConstantsClass.EnemyConstants.*;

// Non-initializable class because it serves as a base for specific enemy classes 
public abstract class Enemy extends Entity{
	private int aniIndex, enemyState, enemyType;
	private int aniTick, aniSpeed = 25;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x,y,width,height);
	}
	
	private void updateAmimationTick() {
		aniTick ++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(enemyType, enemyType)) {
				aniIndex = 0;
			}
		}
	}
	
	public void update() {
		updateAmimationTick();
	}
	
	public int getAniIndex() {
		return aniIndex;
	}
	public int getEnemyState() {
		return enemyState;
	}

}
