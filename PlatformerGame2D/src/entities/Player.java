package entities;

import static utilts.constantsClass.Directions.DOWN;
import static utilts.constantsClass.Directions.LEFT;
import static utilts.constantsClass.Directions.RIGHT;
import static utilts.constantsClass.Directions.UP;
//import static utilts.constantsClass.PlyerConstants.IDLE;
//import static utilts.constantsClass.PlyerConstants.RUNNING;
import static utilts.constantsClass.PlyerConstants.*;
import static utilts.constantsClass.PlyerConstants.getSpriteAmount;
import static utilts.HelpMethodsClass.CanMoveHere;

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
	private boolean left, up, right, down;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	// the calculated offset from the hitbox and * Gamm.Scale because if the gave will be scaled it must also be scaled
	private float xDrawOffset = 21 * GameClass.SCALE;
	private float yDrawOffset = 4 * GameClass.SCALE;
	

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations(); // Load animations during initialization
        initHitbox(x,y,20*GameClass.SCALE,28*GameClass.SCALE);
      
    }

    public void update() {
    	updatePos();
    	updateAnomationTick();
		setAnimation();
		
	}
    

    public void render( Graphics g) {
    	g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);
    	drawHitbox(g);
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
		
		if(!left && !right && !up && !down)
			return;
		
		float xSpeed = 0, ySpeed = 0;
		
		if(left && !right) 
		xSpeed = -playerSpeed;
			else if (right && !left) 
			xSpeed = playerSpeed;
				if(up && !down) 
					ySpeed = -playerSpeed;	
						else if( down && !up) 
							ySpeed = playerSpeed; 
		
//		if(CanMoveHere(x+xSpeed, y+ySpeed, width, height, lvlData)) {
//			this.x += xSpeed;
//			this.y += ySpeed;
//			moving = true;
//			
//		}
				

		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
					
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
	
	
    
}
