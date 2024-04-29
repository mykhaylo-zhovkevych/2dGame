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
	private int playerDirection = -1; // here is zero because if player is not moving than it should remain as it is 
	private boolean moving = false;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations(); // Load animations during initialization
    }

    public void update() {
        
    	updateAnomationTick();

		setAnimation();
		updatePositionDelta();
	}
    

    public void render( Graphics g) {
    	
    	g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 192, 120, null);
    }
	// the method is responsible for changing the picture based on the keyboard conditions 
	public void setDirection( int direction ) {
		this.playerDirection = direction;
		moving = true;
	}
	// private boolean moving = false; all variables declared above 
	public void setMoving(boolean moving) {
		this.moving = moving;
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
	
	private void updatePositionDelta() {
		if(moving) {
			switch(playerDirection) {
			case LEFT: 
				x -=1;
				break;
			case UP: 
				y -=1;
				break;
			case RIGHT: 
				x +=1;
				break;
			case DOWN: 
				y +=1;
				break;
			}
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
}
