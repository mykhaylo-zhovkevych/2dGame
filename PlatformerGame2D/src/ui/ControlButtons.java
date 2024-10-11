package ui;

import java.awt.Rectangle;

// superclass for the overlay-window
public class ControlButtons {

	protected int x, y, width, height;
	protected Rectangle bounds;
	
	public ControlButtons(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		createBounds();
	}

	private void createBounds() {
		// TODO Auto-generated method stub
		
	}
	
	
}
