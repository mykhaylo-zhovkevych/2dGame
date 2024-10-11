package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GamestateEnum;
import main.GameClass;
import utilts.LoadSaveClass;

public class PauseOverlayClass {

	private BufferedImage backgroundImg;
	private int bgX, bgY, bgWidth, bgHeight;
	
	public PauseOverlayClass() {
		
		loadBackground();
		
	}
	
	
	private void loadBackground() {
		backgroundImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.PAUS_BACKGROUND);
		bgWidth = (int) (backgroundImg.getWidth() * GameClass.SCALE);
		bgHeight = (int) (backgroundImg.getHeight() * GameClass.SCALE);
		bgX = GameClass.GAME_WIDTH / 2 - bgWidth / 2;
		bgY = (int) (25 * GameClass.SCALE);
		
	}


	public void update() {
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);
	}
	
	public void mouseDragged() {
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
}
