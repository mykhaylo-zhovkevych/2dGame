package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GamestateEnum;
import gamestates.PlayingClass;
import main.GameClass;
import utilts.LoadSaveClass;
import static utilts.ConstantsClass.UI.PausButtons.*;
import static utilts.ConstantsClass.UI.URMButtons.*;
import static utilts.ConstantsClass.UI.VolumeButtons.*;

public class PauseOverlayClass {

	private PlayingClass playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgWidth, bgHeight;
	private SoundButtonsClass musicButton, sfxButton;
	
	private UrmButtonsClass menuB, replayB, unpauseB;
	private VolumeButtonClass volumeButton;
	
	public PauseOverlayClass(PlayingClass playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createUrmButtons();
		createVolumeButton();
	}
	
	
	private void createVolumeButton() {
		int vX = (int)(309 * GameClass.SCALE);
		int vY = (int)(278 * GameClass.SCALE);
		volumeButton = new VolumeButtonClass(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}


	private void createUrmButtons() {
		int menuX = (int)(313 * GameClass.SCALE);
		int replayX = (int)(387 * GameClass.SCALE);
		int unpauseX = (int)(462 * GameClass.SCALE);
		int bY = (int)(325 * GameClass.SCALE);
		// the last parameter is fore the row, can check the method itself
		menuB = new UrmButtonsClass(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButtonsClass(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButtonsClass(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
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
		
		menuB.update();
		replayB.update();
		unpauseB.update();
		
		volumeButton.update();
	}
	
	public void draw(Graphics g) {
		// Background buttons
		g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);
		
		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);
		
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		volumeButton.draw(g);
		
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
	}

	public void mousePressed(MouseEvent e) {
		
		if(isIn(e,musicButton)) {
			musicButton.setMousePressed(true);
		}
		else if (isIn(e, sfxButton)) {
			sfxButton.setMousePressed(true);
		}
		else if(isIn(e,menuB))
			menuB.setMousePressed(true);
		else if(isIn(e,replayB))
			replayB.setMousePressed(true);
		else if(isIn(e,unpauseB))
			unpauseB.setMousePressed(true);
		else if(isIn(e,volumeButton))
			volumeButton.setMousePressed(true);
	}


	public void mouseReleased(MouseEvent e) {

		if(isIn(e, musicButton)) {
			if (musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
			}
		}
		else if (isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
			
		}
		else if (isIn(e, menuB)) {
			if(menuB.isMousePressed()) {
				GamestateEnum.state = GamestateEnum.MENU;
				playing.unpauseGame();
			}
		}
		else if (isIn(e, replayB)) {
			if(replayB.isMousePressed())
				System.out.println("replay lvl...");
			
		}
		else if (isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed())
				playing.unpauseGame();
			
		}
		
		musicButton.resetBools();
		sfxButton.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		volumeButton.resetBools();
	}


	public void mouseMoved(MouseEvent e) {
		
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if(isIn(e,musicButton)) {
			musicButton.setMouseOver(true);
		}
		else if (isIn(e, sfxButton)) {
			sfxButton.setMouseOver(true);
		}
		else if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		}
		else if (isIn(e, replayB)) {
			replayB.setMouseOver(true);
		}
		else if (isIn(e, unpauseB)) {
			unpauseB.setMouseOver(true);
		}
		else if (isIn(e, volumeButton)) {
			volumeButton.setMouseOver(true);
		}
	}

	// this class is not the state class that is why i am recreating it again
	private boolean isIn(MouseEvent e, ControlButtonsClass b) {
		return(b.getBounds().contains(e.getX(), e.getY()));
	}
	
}
