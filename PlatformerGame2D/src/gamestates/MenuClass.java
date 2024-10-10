package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.GameClass;
import ui.ButtonsMenuClass;
import utilts.LoadSaveClass;

public class MenuClass extends StateClass implements Statemethods {

	
	private ButtonsMenuClass[] buttons = new ButtonsMenuClass[3];
	private BufferedImage backgroundImg;
	private int menuX,menuY,menuWidth,menuHeight;
	
	public MenuClass(GameClass game) {
		super(game);
		loadButtons();
		loadBackground();
	}


	private void loadBackground() {
		backgroundImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.MENU_BACKGROUND);
		menuWidth = (int)(backgroundImg.getWidth() * GameClass.SCALE);
		menuHeight = (int)(backgroundImg.getHeight() * GameClass.SCALE);
		menuX = GameClass.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int)(45 * GameClass.SCALE);
		
	}


	private void loadButtons() {
		buttons[0] = new ButtonsMenuClass(GameClass.GAME_WIDTH / 2, (int)(150 * GameClass.SCALE), 0, GamestateEnum.PLAYING);
		buttons[1] = new ButtonsMenuClass(GameClass.GAME_WIDTH / 2, (int)(220 * GameClass.SCALE), 1, GamestateEnum.OPTIONS);
		buttons[2] = new ButtonsMenuClass(GameClass.GAME_WIDTH / 2, (int)(290 * GameClass.SCALE), 2, GamestateEnum.QUIT);
		
	}


	@Override
	public void update() {
		for(ButtonsMenuClass mb : buttons)
			mb.update();
		
	}

	
	@Override
	public void draw(Graphics g) {
		
		g.drawImage(backgroundImg, menuX, menuY,menuWidth ,menuHeight, null);
		
		for(ButtonsMenuClass mb : buttons)
			mb.draw(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(ButtonsMenuClass mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(ButtonsMenuClass mb : buttons) {
			// to check if i am inside of the button if yes execute the button itself otherwise do break
			if(isIn(e,mb)) {
				if(mb.isMousePressed()) {
					mb.applyGamestateEnum();
					break;
				}
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(ButtonsMenuClass mb : buttons) 
			mb.resetBools();
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		for(ButtonsMenuClass mb : buttons) 
			mb.setMouseOver(false);
		
		for(ButtonsMenuClass mb : buttons) 
			if(isIn(e,mb)) {
				mb.setMouseOver(true);
				break;
			}
	}	

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			GamestateEnum.state = GamestateEnum.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
