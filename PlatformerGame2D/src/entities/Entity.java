package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.GameClass;

// here will never be stored antity but only the values that will be stored, like position, heals, level etc
public abstract class Entity {

	// Subclasses of the Entity can acces these variables
	protected float x, y; // to allow the classes that extends this class for use 
	protected int width, height; 
	protected Rectangle2D.Float hitbox;
	protected int aniTick, aniIndex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;

	protected int maxHealth;
	protected int currentHealth;
	protected Rectangle2D.Float attackBox;
	protected float walkSpeed;


	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		// Hitbox for Player and Enemies
		// method that initialise the hitbox
		
	}
	protected void drawHitbox(Graphics g, int xLvlOffset) {
		// For debugging the hitbox
		g.setColor(Color.RED);
		g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}
	
	// moved this method in here (part19)
	protected void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	// the initHitbox is not in the same place as the starring position of Entity
	// the x and y will be taken from the position of the entity/constructor
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)(width * GameClass.SCALE), (int)(height * GameClass.SCALE));
		
	}
	// this method will be will take new x and y and put it to the hitbox
	//	public void updateHitbox() {
	//		hitbox.x = (int) x;
	//		hitbox.y = (int) y;
	//	}
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public int getState() {
		return state;
	}
	
	public int getAniIndex() {
		return aniIndex;
	}

} 