package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GamestateEnum;
import main.GameClass;
import utilts.LoadSaveClass;
import static utilts.ConstantsClass.UI.PausButtons.*;


public class PauseOverlayClass {

	private BufferedImage backgroundImg;
	private int bgX, bgY, bgWidth, bgHeight;
	private SoundButtonsClass musicButton, sfxButton;
	
	public PauseOverlayClass() {
		
		loadBackground();
		createSoundButtons();
		
	}
	
	
	private void createSoundButtons() {
		int soundX = (int)(450 * GameClass.SCALE);
		int musicY = (int)(140 * GameClass.SCALE);
		int sfxY = (int)(186 * GameClass.SCALE);
		musicButton = new SoundButtonsClass(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButtonsClass(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
		
	}


	private void loadBackground() {
		backgroundImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.PAUS_BACKGROUND);
		bgWidth = (int) (backgroundImg.getWidth() * GameClass.SCALE);
		bgHeight = (int) (backgroundImg.getHeight() * GameClass.SCALE);
		bgX = GameClass.GAME_WIDTH / 2 - bgWidth / 2;
		bgY = (int) (25 * GameClass.SCALE);
		
	}


	public void update() {
		musicButton.update();
		sfxButton.update();
	}
	
	public void draw(Graphics g) {
		// Background buttons
		g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);
		
		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);
		
	}
	
	public void mouseDragged() {
		
	}

	public void mousePressed(MouseEvent e) {
		
		if(isIn(e,musicButton)) {
			musicButton.setMousePressed(true);
		}
		else if (isIn(e, sfxButton)) {
			sfxButton.setMousePressed(true);
		}
		
	}


	public void mouseReleased(MouseEvent e) {

		if(isIn(e,musicButton)) {
			if (musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
			}
		}
		else if (isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
		}
		musicButton.resetBools();
		sfxButton.resetBools();
	}


	public void mouseMoved(MouseEvent e) {
		
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		
		if(isIn(e,musicButton)) {
			musicButton.setMouseOver(true);
		}
		else if (isIn(e, sfxButton)) {
			sfxButton.setMouseOver(true);
		}
		
	}

	// this class is not the state class that is why i am recreating it again
	private boolean isIn(MouseEvent e, ControlButtonsClass b) {
		return(b.getBounds().contains(e.getX(), e.getY()));
	}
	
}
