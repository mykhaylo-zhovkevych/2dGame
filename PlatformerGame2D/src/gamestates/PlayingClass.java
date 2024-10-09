package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManagerClass;
import main.GameClass;

public class PlayingClass extends StateClass implements Statemethods{

	private Player player;
	private LevelManagerClass levelmanager;	
	
	public PlayingClass(GameClass game) {
		super(game);
		initClasses();
	
	}


	private void initClasses() {
		levelmanager = new LevelManagerClass(game);
		player = new Player(200, 200, (int) (64 * GameClass.SCALE), (int) (40 * GameClass.SCALE));
		player.loadLvlData(levelmanager.getCurrentLevel().getLevelData());
		
		}


	@Override
	public void update() {
		levelmanager.update();
		player.update();
		
	}


	@Override
	public void draw(Graphics g) {
		levelmanager.draw(g);
		player.render(g);
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
		player.setAttacking(true);
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
	    case KeyEvent.VK_W:
	    	player.setUp(true);
	        break;
	    case KeyEvent.VK_A:
	    	player.setLeft(true);
	        break;
	    case KeyEvent.VK_S:
	    	player.setDown(true);
	        break;
	    case KeyEvent.VK_D:
	    	player.setRight(true);
	        break;
	    case KeyEvent.VK_SPACE:
	    	player.setJump(true);
	    	break;
	    case KeyEvent.VK_BACK_SPACE:
	    	GamestateEnum.state = GamestateEnum.MENU;
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()) {
	    case KeyEvent.VK_W:
	    	player.setUp(false);
	        break;
	    case KeyEvent.VK_A:
	    	player.setLeft(false);
	        break;
	    case KeyEvent.VK_S:
	    	player.setDown(false);
	        break;
	    case KeyEvent.VK_D:
	    	player.setRight(false);
	        break;
	    case KeyEvent.VK_SPACE:
	    	player.setJump(false);
	    	break;
	    }  
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void windowsFocusLost() {
		player.resetDirBooleans();
	}
	
	
}
