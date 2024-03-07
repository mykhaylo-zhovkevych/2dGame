package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanelClass;

//extending class is not the same as implementing the interface and only one class can be extended but interfaces can be extended more than one 
public class KeyboardInputsClass implements KeyListener {
// this one take care of clicks
// interface KeyListener doesn't really have any code in it but has a lot of methods 
	private GamePanelClass gamePanelClass; // global variable 
	public KeyboardInputsClass(GamePanelClass gamePanelClass) {
		this.gamePanelClass = gamePanelClass;
	}
// now KeyboardInputsClass has access to the gamePanelClass an when the something happens here that can take place in thegamePanelClass
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
	    switch(e.getKeyCode()) {
	    case KeyEvent.VK_W:
	        System.out.println("W");
	        gamePanelClass.changeYDelta(-5); // Move up
	        break;
	    case KeyEvent.VK_A:
	        System.out.println("A");
	        gamePanelClass.changeXDelta(-5); // Move left
	        break;
	    case KeyEvent.VK_S:
	        System.out.println("S");
	        gamePanelClass.changeYDelta(5); // Move down
	        break;
	    case KeyEvent.VK_D:
	        System.out.println("D");
	        gamePanelClass.changeXDelta(5); // Move right
	        break;
	    }
	}
	@Override
	public void keyReleased(KeyEvent e) {		
	}
}
