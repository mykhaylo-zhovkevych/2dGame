package entities;

import static utilts.constantsClass.Directions.DOWN;
import static utilts.constantsClass.Directions.LEFT;
import static utilts.constantsClass.Directions.RIGHT;
import static utilts.constantsClass.Directions.UP;
import static utilts.constantsClass.PlyerConstants.IDLE;
import static utilts.constantsClass.PlyerConstants.RUNNING;
import static utilts.constantsClass.PlyerConstants.getSpriteAmount;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity {

    private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	// global varibale 
	private int playerAction = IDLE;
//	private int playerDirection = -1; // here is zero because if player is not moving than it should remain as it is 
	private boolean left, up, right, down;
	private boolean moving = false;
	private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations(); // Load animations during initialization
    }

    public void update() {
    	updatePos();
    	updateAnomationTick();
		setAnimation();
		
	}
    

    public void render( Graphics g) {
    	
    	g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 192, 120, null);
    }
	
	
//    this update the animation loop
	private void updateAnomationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(playerAction))
				aniIndex = 0;
		}
		
	}
	
	private void setAnimation() {
		if(moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
	}
	
	// this method is used to prevent when user hit/hold like w s or a d simultaneously at the same time 
	// When using the booleans problem can occur that when the user exit the window the character will keep running 
	private void updatePos() {
		
		moving = false;
		
		if(left && !right) {
			x-=playerSpeed;
			moving = true;
		} else if (right && !left){ 
			x+=playerSpeed;
			moving = true;
		}
		
		if(up && !down) {
			y-=playerSpeed;
			moving = true;
		} else if( down && !up) {
			y+=playerSpeed;
			moving = true;
		}
	}
	

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/player_sprites012.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[9][6];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
		
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
