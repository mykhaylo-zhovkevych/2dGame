package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.GamestateEnum;
import main.GamePanelClass;

public class MouseInputsClass implements MouseListener, MouseMotionListener{

	private GamePanelClass gamePanelClass;
	public MouseInputsClass(GamePanelClass gamePanelClass) {
		this.gamePanelClass = gamePanelClass;
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		switch(GamestateEnum.state) {
		case PLAYING:
			gamePanelClass.getGameClass().getPlaying().mouseDragged(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch(GamestateEnum.state) {
		case MENU:
			gamePanelClass.getGameClass().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			gamePanelClass.getGameClass().getPlaying().mouseMoved(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(GamestateEnum.state) {
		case PLAYING:
			gamePanelClass.getGameClass().getPlaying().mouseClicked(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(GamestateEnum.state) {
		case MENU:
			gamePanelClass.getGameClass().getMenu().mousePressed(e);
			break;
		case PLAYING:
			gamePanelClass.getGameClass().getPlaying().mousePressed(e);
			break;
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(GamestateEnum.state) {
		case MENU:
			gamePanelClass.getGameClass().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gamePanelClass.getGameClass().getPlaying().mouseReleased(e);
			break;
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
