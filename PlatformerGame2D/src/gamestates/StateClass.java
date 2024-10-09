package gamestates;

import main.GameClass;

public class StateClass {

	protected GameClass game;
	
	public StateClass(GameClass game) {
		this.game = game;
		
		
		
	}
	
	public GameClass getGame() {
		
		return game;
	}
	
	
}
