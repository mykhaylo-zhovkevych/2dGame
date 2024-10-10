package entities;

import static utilts.ConstantsClass.Directions.DOWN;
import static utilts.ConstantsClass.Directions.LEFT;
import static utilts.ConstantsClass.Directions.RIGHT;
import static utilts.ConstantsClass.Directions.UP;
//import static utilts.ConstantsClass.PlyerConstants.IDLE;
//import static utilts.ConstantsClass.PlyerConstants.RUNNING;
import static utilts.ConstantsClass.PlyerConstants.*;
import static utilts.ConstantsClass.PlyerConstants.getSpriteAmount;
import static utilts.HelpMethodsClass.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.GameClass;
import utilts.LoadSaveClass;

public class Player extends Entity {

    private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	// global varibale 
	private int playerAction = IDLE;
	//	private int playerDirection = -1; // here is zero because if player is not moving than it should remain as it is 
	private boolean left, up, right, down, jump;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	// the calculated offset from the hitbox and * Gamm.Scale because if the gave will be scaled it must also be scaled
	private float xDrawOffset = 21 * GameClass.SCALE;
	private float yDrawOffset = 4 * GameClass.SCALE;
	
	// the variables is storing the speed of the auto-running
	private float airSpeed = 0f;
	private float gravity = 0.04f * GameClass.SCALE;
	private float jumpSpeed = -2.25f * GameClass.SCALE;
	// in case if the player hits the roof a new value will be assigned 
	private float fallSpeedAfterCollision = 0.5f * GameClass.SCALE;
	private boolean inAir = false;
	

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations(); // Load animations during initialization
        initHitbox(x,y,(int)(20*GameClass.SCALE),(int)(27*GameClass.SCALE));
        
        
    }

    public void update() {
    	updatePos();
    	updateAnomationTick();
		setAnimation();
		
	}
    

    public void render( Graphics g) {
    	g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);
//    	drawHitbox(g);
    }
	
	
//    this update the animation loop
	private void updateAnomationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				
			}
				
		}
		
	}
	
	private void setAnimation() {
		int startAnimation = playerAction;
		
		if(moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		
		if(inAir) {
			if(airSpeed < 0)
				playerAction = JUMP;
			else 
				playerAction = FALLING;
		}
		
		if(attacking)
			playerAction = ATTACK_1;
		
		if(startAnimation != playerAction) {
			resetAnimationTick();
		}
	}
	
	private void resetAnimationTick() {
		aniTick = 0;
		aniIndex = 0;
		
	}

	// this method is used to prevent when user hit/hold like w s or a d simultaneously at the same time 
	// When using the booleans problem can occur that when the user exit the window the character will keep running 
	private void updatePos() {
		moving = false;
		if(jump)
			jump();
		// if the player is still 
		if(!left && !right && !inAir)
			return;
		
		float xSpeed = 0;
		
		if(left) 
			xSpeed -= playerSpeed;
		
		if (right) 	
			xSpeed += playerSpeed;
		
		if(!inAir) {
			if(!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}
		
		
		if(inAir) {
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				// This method takes care of the left and right 
				updateXPos(xSpeed);
				} else {
					hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
					// if hit the floor
					if(airSpeed > 0) 
						resetInAir();
						// if hit the roof
						else 
							airSpeed = fallSpeedAfterCollision;
					updateXPos(xSpeed);
					
				}
				
		} else 
			updateXPos(xSpeed);
		
		moving = true;
			
	}

    private void jump() {
		if(inAir) 
			return;
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
    	
		inAir = false;
		airSpeed = 0;
		
	}

	private void updateXPos(float xSpeed) {
	if	(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
		
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
		
	}

	private void loadAnimations() {
      
    BufferedImage img = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.PLAYER_ATLAS);
            
    animations = new BufferedImage[9][6];
            for (int j = 0; j < animations.length; j++) 
                for (int i = 0; i < animations[j].length; i++) 
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                
            }
 // this class is in here just temporarily
 	public void loadLvlData(int[][] lvlData) {
 		this.lvlData = lvlData;
 		if(!IsEntityOnFloor(hitbox,lvlData))
 			inAir = true;
 		
 	}
    
    public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
    
    public void setAttacking(boolean attacking) {
    	this.attacking = attacking;
    	
    }
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
    
}
