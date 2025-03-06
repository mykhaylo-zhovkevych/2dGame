package ui;

import gamestates.GamestateEnum;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static utilts.ConstantsClass.UI.Buttons.*;
import utilts.LoadSaveClass;

public class ButtonsMenuClass {

	private int xPos, yPos, rowIndex, index;
	private int xOffsetCenter = B_WIDTH / 2;
	
	private GamestateEnum state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	
	
	// needs x and y position to know where to draw the button 
	public ButtonsMenuClass(int xPos, int yPos, int rowIndex, GamestateEnum state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.state = state;
		this.rowIndex = rowIndex;
		loadImgs();
		initBounds();
	}

	private void initBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
		
	}

	private void loadImgs() {
		// should contain 3 images
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.MENU_BUTTONS);
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
			
		}	
	}
	
	public void draw (Graphics g) {
		// index is depended on the mouse-event of the user
		g.drawImage(imgs[index],xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
	}
	
	public void update() {
		index = 0;
		if(mouseOver) {
			index = 1;
			if(mousePressed) {
				index = 2;
			}
		}
	}


	public boolean isMouseOver() {
		return mouseOver;
	}


	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}


	public boolean isMousePressed() {
		return mousePressed;
	}


	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void applyGamestateEnum() {
		GamestateEnum.state = state;
	}
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
}
