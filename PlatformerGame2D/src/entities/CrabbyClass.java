package entities;

import main.GameClass;
import static utilts.ConstantsClass.EnemyConstants.*;

public class CrabbyClass extends Enemy{

	public CrabbyClass(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		// 22 / 19 is the size of the hitbox that 
		initHitbox(x, y, (int)(22 * GameClass.SCALE),(int)(19 * GameClass.SCALE));
		
	}

	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAmimationTick();
		
	}

	private void updateMove(int[][] lvlData, Player player) {
		if(firstUpdate) 
		firstUpdateCheck(lvlData);
			
			if (inAir) 
					updateInAir(lvlData);
				else {

				switch(enemyState) {

					case IDLE: 
						newState(RUNNING);
						break;
					case RUNNING:

						if(canSeePlayer(lvlData, player))
							turnTowardsPlayer(player);
						if(isPlayerCloseforAttack(player))
							newState(ATTACK);

						move(lvlData);
						break;
							
								
					}
				}
			}

}
