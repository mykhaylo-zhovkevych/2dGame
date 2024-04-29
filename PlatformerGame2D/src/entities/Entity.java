package entities;

// here will never be stored antity but only the values that will be stored, like position, heals, level etc
public abstract class Entity {

	protected float x, y; // to allow the classes that extends this class for use 
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		
	}
	
}
