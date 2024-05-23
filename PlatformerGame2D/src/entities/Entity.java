package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// here will never be stored antity but only the values that will be stored, like position, heals, level etc
public abstract class Entity {

	protected float x, y; // to allow the classes that extends this class for use 
	protected int width, height; 
	protected Rectangle hitbox;
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		// Hitbox for Player and Enemies
		// method that initialise the hitbox
		initHitbox();
		
	}
	protected void drawHitbox(Graphics g) {
		// For debugging the hitbox
		g.setColor(Color.RED);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
	
	private void initHitbox() {
		hitbox = new Rectangle((int)x,(int)y, width, height);
		
	}
	// this method will be will take new x and y and put it to the hitbox
	public void updateHitbox() {
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
} 



