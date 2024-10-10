package gamestates;

import java.awt.event.MouseEvent;

import main.GameClass;
import ui.ButtonsMenuClass;

public class StateClass {

	protected GameClass game;
	
	public StateClass(GameClass game) {
		this.game = game;
		
	}
	
	// make more sense to declare here the methods because the buttons can have different states.
	public boolean isIn(MouseEvent e, ButtonsMenuClass mb) {
		// return the boolean that give me a true if the mouse inside of the Rectangle, otherwise is returns false.
		return mb.getBounds().contains(e.getX(),e.getY());
		
	}
	
	public GameClass getGame() {
		
		return game;
	}
	
	
}
