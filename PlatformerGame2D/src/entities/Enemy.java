package entities;

import main.GameClass;
import static utilts.ConstantsClass.Directions.*;
import static utilts.ConstantsClass.EnemyConstants.*;
import static utilts.HelpMethodsClass.*;

// Non-initializable class because it serves as a base for specific enemy classes 
public abstract class Enemy extends Entity{
	private int aniIndex, enemyState, enemyType;
	private int aniTick, aniSpeed = 25;
	private boolean firstUpdate = true;
	private boolean inAir;
	private float fallSpeed;
	private float gravity = 0.04f * GameClass.SCALE;
	private float walkSpeed = 0.35f * GameClass.SCALE;
	private int walkDir = LEFT;
	

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x,y,width,height);
	}
	
	private void updateAmimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
			}
		}
	}


	public void update(int[][] lvlData) {
		updateMove(lvlData);
		updateAmimationTick();
		
	}

	private void updateMove(int[][] lvlData) {
		if(firstUpdate) {
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
			firstUpdate = false;
		}
			
			if (inAir) {
				if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
					hitbox.y += fallSpeed;
					fallSpeed += gravity;
				} 
				// but at some point it will return false because the entity will hit the ground
				else {
					inAir = false;
					hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
				}
				// here will the patrolling be implemented
				} else {

				switch(enemyState) {

					case IDLE: 
						enemyState = RUNNING;
						break;
					case RUNNING:

						float xSpeed = 0;
						if (walkDir == LEFT) 
							xSpeed = -walkSpeed;
						else 
							xSpeed = walkSpeed;

						if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
							if (IsFloor(hitbox, xSpeed, lvlData)) {
								hitbox.x += xSpeed;
								return;
							} 
							// If it can't move or would fall off(if either of them returns false), it turns around
							changeWalkDir();
							
							break;
							
								
							}
						}
					}
								
	private void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;

	}								
							
	public int getAniIndex() {
		return aniIndex;
	}
	public int getEnemyState() {
		return enemyState;
	}

}