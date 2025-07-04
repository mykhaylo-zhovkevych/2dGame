package entities;

import gamestates.PlayingClass;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import main.GameClass;
import static utilts.ConstantsClass.*;
import static utilts.ConstantsClass.PlyerConstants.*;
import static utilts.HelpMethodsClass.*;
import utilts.LoadSaveClass;

public class Player extends Entity {

    private BufferedImage[][] animations;

	// private int playerDirection = -1; // here is zero because if player is not moving than it should remain as it is 
	private boolean left, right, jump;
	private boolean moving = false, attacking = false;

	private int[][] lvlData;
	// the calculated offset from the hitbox and * Gamm.Scale because if the gave will be scaled it must also be scaled
	private float xDrawOffset = 21 * GameClass.SCALE;
	private float yDrawOffset = 4 * GameClass.SCALE;
	

	private float jumpSpeed = -2.25f * GameClass.SCALE;
	// in case if the player hits the roof a new value will be assigned 
	private float fallSpeedAfterCollision = 0.5f * GameClass.SCALE;
	
	// Status bar UI

	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * GameClass.SCALE);
	private int statusBarHeight = (int) (58 * GameClass.SCALE);
	private int statusBarX = (int) (10 * GameClass.SCALE);
	private int statusBarY = (int) (10 * GameClass.SCALE);

	private int healthBarWidth = (int) (150 * GameClass.SCALE);
	private int healthBarHeight = (int) (4 * GameClass.SCALE);
	private int healthBarXStart = (int) (34 * GameClass.SCALE);
	private int helathBarYStart = (int) (14 * GameClass.SCALE);

	
	private int healthWidth = healthBarWidth;


	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private PlayingClass playing;

	private int tileY = 0;


    public Player() {
        super(0, 0, 0, 0);
    }

    public Player(float x, float y, int width, int height, PlayingClass playing) {
        super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 100;
		this.currentHealth = maxHealth;
		this.walkSpeed = 1.0f * GameClass.SCALE;
        loadAnimations(); // Load animations during initialization
        initHitbox(20,27);
        initAttackBox();
				
		}
			public void setSpawn(Point spawn) {
				this.x = spawn.x;
				this.y = spawn.y;
				hitbox.x = x;
				hitbox.y = y;
			}
		
			private void initAttackBox() {
				attackBox = new Rectangle2D.Float(x,y,(int)(20 * GameClass.SCALE),(int)(20 * GameClass.SCALE));

			}
		
			public void update() {
				// Because if it is down it will leave some health still there 
				updateHealthBar();

				if (currentHealth <= 0) {
					playing.setGAmeOver(true);
					return;
				}

				
				updateAttackBox();

				updatePos();
				if(moving) {
					checkPotionsTouched();
					checkSpikesTouiuched();
					tileY = (int)(hitbox.y / GameClass.TILES_SIZE);
				}
				if (attacking) 
					checkAttack();
				updateAnomationTick();
				setAnimation();
				
			}

		private void checkSpikesTouiuched() {
			playing.checkSpikesTouched(this);
		}

		private void checkPotionsTouched() {
			playing.checkPotionsTouched(hitbox);
		}

		private void checkAttack() {
			if (attackChecked || aniIndex != 1) 
				return;
			attackChecked = true;
			playing.checkEnemyHit(attackBox);
			playing.checkObjectHit(attackBox);
		}
			
		private void updateAttackBox() {
			// the hitbox will be on the right side if true and on the left if false 
			if(right) {
					attackBox.x = hitbox.x + hitbox.width + (int)(GameClass.SCALE * 10);
				} else if (left){
					attackBox.x = hitbox.x - hitbox.width - (int)(GameClass.SCALE * 10);
				}
				attackBox.y = hitbox.y + (GameClass.SCALE * 10);
			}

		private void updateHealthBar() {
				healthWidth = (int)((currentHealth / (float) maxHealth) * healthBarWidth);
			}

		public void render( Graphics g, int lvlOffset) {
			g.drawImage(animations[state][aniIndex], 
			(int)(hitbox.x - xDrawOffset) - lvlOffset + flipX, 
			(int)(hitbox.y - yDrawOffset), 
			width * flipW, height, null);
			// draws the hitbox
			drawHitbox(g, lvlOffset);
			drawAttackBox(g, lvlOffset);
			drawUI(g);
				
			}
			
			
		private void drawUI(Graphics g) {
				g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
				g.setColor(Color.RED);
				// this value healthBarXStart is the offset in the image
				g.fillRect(healthBarXStart + statusBarX, helathBarYStart + statusBarY , healthWidth, healthBarHeight);
			}
		
	// This update the animation loop
	private void updateAnomationTick() {
		aniTick++;
		if(aniTick >= ANI_SEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;	
				attackChecked = false;
			}		
		}
	}
	
	private void setAnimation() {
		int startAnimation = state;
		
		if(moving)
			state = RUNNING;
		else
			state = IDLE;
		
		if(inAir) {
			if(airSpeed < 0)
				state = JUMP;
			else 
				state = FALLING;
		}
		
		if(attacking) {
			state = ATTACK;
			if (startAnimation != ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}

		}
		
		if(startAnimation != state) {
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
		if(!inAir) {
			if((!left && !right) || (right && left))
				return;
		}
		
		float xSpeed = 0;
		
		if(left) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) 	{
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}
		if(!inAir) {
			if(!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}
		
		
		if(inAir) {
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
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

	public void changeHealth(int value) {
		currentHealth += value;
		// making sure i dont go over zero or max health
		if(currentHealth <= 0) {
			currentHealth = 0;
			// gameOver();
		} else if (currentHealth >= maxHealth) {
			currentHealth = maxHealth;
		}
	}
 
	public void changePower(int value) {
		System.out.println("Power Addet"); //TODO Placeholder
    }

	private void loadAnimations() {
      
    BufferedImage img = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.PLAYER_ATLAS);
            
    animations = new BufferedImage[7][8];
            for (int j = 0; j < animations.length; j++) 
                for (int i = 0; i < animations[j].length; i++) 
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                
			statusBarImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.STATUS_BAR);

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

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}	

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		state = IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox,lvlData))
			inAir = true;
			
	}

    public void kill() {
		currentHealth = 0;
	}

	public int getTileY() {

	return tileY;
}

}
