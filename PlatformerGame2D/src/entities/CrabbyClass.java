package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.GameClass;
import static utilts.ConstantsClass.Directions.*;
import static utilts.ConstantsClass.EnemyConstants.*;

public class CrabbyClass extends Enemy{

	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;


	public CrabbyClass(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		// 22 / 19 is the size of the hitbox that 
		initHitbox(x, y, (int)(22 * GameClass.SCALE),(int)(19 * GameClass.SCALE));
		initAttackBox();
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x,y,(int)(82 * GameClass.SCALE),(int)(19 * GameClass.SCALE));
		attackBoxOffsetX = (int)(GameClass.SCALE * 30);


	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAmimationTick();
		updateAttackBox();
		
	}

	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
	
	}

	private void updateBehavior(int[][] lvlData, Player player) {
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
					case ATTACK: 
						if(aniIndex == 0) 
							attackChecked = false;
							
						if (aniIndex == 3 && !attackChecked) {
							checkEnemyHit(attackBox, player);
						}
						break;
					case HIT:
						break;
					}
				}
			}

	public void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	// the crab is faces to the left by the default 
	public int flipX() {
		if(walkDir == RIGHT) 
			return width;
		else 
			return 0;
	}

	public int flipW() {
		if (walkDir == RIGHT) 
			return -1;
		else
			return 1;
	}
}