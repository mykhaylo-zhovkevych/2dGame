package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.GamestateEnum;
import main.GamePanelClass;
import static utilts.ConstantsClass.Directions.*;

//extending class is not the same as implementing the interface and only one class can be extended but interfaces can be extended more than one 
public class KeyboardInputsClass implements KeyListener {
// this one take care of clicks
// interface KeyListener doesn't really have any code in it but has a lot of methods 
	private GamePanelClass gamePanelClass; // global variable 
	public KeyboardInputsClass(GamePanelClass gamePanelClass) {
		this.gamePanelClass = gamePanelClass; 
// this. is like to reaswure 		
	}
	/*
	 
	Dependency Injection
    Why Use It: 
    The KeyboardInputsClass is designed to handle keyboard events and to perform actions based on those events within 
    the context of a GamePanelClass object. However, KeyboardInputsClass does not inherently know about the GamePanelClass it will interact 
    with.
    How It Works: 
    By passing a GamePanelClass object to the KeyboardInputsClass constructor, you're giving KeyboardInputsClass a 
    reference to the specific instance of GamePanelClass it should work with. This allows KeyboardInputsClass 
    to call methods on the GamePanelClass instance, such as changeXDelta and changeYDelta, effectively linking the keyboard input to the game's logic.
    
    */
// now KeyboardInputsClass has access to the gamePanelClass an when the something happens here that can take place in thegamePanelClass
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {

		switch (GamestateEnum.state) {
		case MENU:
			gamePanelClass.getGameClass().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanelClass.getGameClass().getPlaying().keyPressed(e);
			break;
		default:
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		switch (GamestateEnum.state) {
		case MENU:
			gamePanelClass.getGameClass().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanelClass.getGameClass().getPlaying().keyReleased(e);
			break;
		default:
			break;

		}
   
	}
}
