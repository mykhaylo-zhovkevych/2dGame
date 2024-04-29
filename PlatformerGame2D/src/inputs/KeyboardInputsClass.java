package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanelClass;
import static utilts.constantsClass.Directions.*;

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
	    switch(e.getKeyCode()) {
	    case KeyEvent.VK_W:
	        
	        gamePanelClass.getGameClass().getPlayer().setDirection(UP);
	        break;
	    case KeyEvent.VK_A:
	        gamePanelClass.getGameClass().getPlayer().setDirection(LEFT);
	        break;
	    case KeyEvent.VK_S:
	    	gamePanelClass.getGameClass().getPlayer().setDirection(DOWN);
	        break;
	    case KeyEvent.VK_D:
	    	gamePanelClass.getGameClass().getPlayer().setDirection(RIGHT);
	        break;
	    }
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()) {
	    case KeyEvent.VK_W:
	    case KeyEvent.VK_A:
	    case KeyEvent.VK_S:
	    case KeyEvent.VK_D:
	    	 gamePanelClass.getGameClass().getPlayer().setMoving(false);
	        break;
	    }
	}
}
