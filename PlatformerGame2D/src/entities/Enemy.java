package entities;

import main.GameClass;
import static utilts.ConstantsClass.Directions.*;
import static utilts.ConstantsClass.EnemyConstants.*;
import static utilts.HelpMethodsClass.*;

// Non-initializable class because it serves as a base for specific enemy classes 
public abstract class Enemy extends Entity{
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected float fallSpeed;
	protected float gravity = 0.04f * GameClass.SCALE;
	protected float walkSpeed = 0.35f * GameClass.SCALE;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = GameClass.TILES_SIZE;
	

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x,y,width,height);
	}

	protected void firstUpdateCheck(int[][] lvlData){
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
			firstUpdate = false;
	}

	protected void updateInAir(int[][] lvlData){
		if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		} 
		else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			tileY = (int) (hitbox.y / GameClass.TILES_SIZE);
		}
	}

	protected void  move(int[][] lvlData){
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
	}

	protected void turnTowardsPlayer(Player player){
		if(player.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

protected boolean canSeePlayer(int[][] lvlData, Player player){
	int playerTileY = (int) (player.getHitbox().y / GameClass.TILES_SIZE);
	if (playerTileY == tileY)
		if(isPlayerInRange(player)){
			if(IsSightClear(lvlData,hitbox,player.hitbox, tileY))
				return true;
			}

		return false;

	}

	private boolean isPlayerInRange(Player player){
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}

	protected boolean isPlayerCloseforAttack(Player player){
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}

	protected void newState(int enemyState){
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;

	}
	
	protected void updateAmimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
				if(enemyState == ATTACK)
					enemyState = IDLE;
			}
		}
	}

							
	protected void changeWalkDir() {
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