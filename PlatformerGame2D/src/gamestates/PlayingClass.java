package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManagerClass;
import main.GameClass;
import ui.PauseOverlayClass;

public class PlayingClass extends StateClass implements Statemethods{

	private Player player;
	private LevelManagerClass levelmanager;	
	// varible responsible for showing the stop-screenn
	private boolean paused = true;
	private PauseOverlayClass pausOverlay;
	
	public PlayingClass(GameClass game) {
		super(game);
		initClasses();
	
	}


	private void initClasses() {
		levelmanager = new LevelManagerClass(game);
		player = new Player(200, 200, (int) (64 * GameClass.SCALE), (int) (40 * GameClass.SCALE));
		player.loadLvlData(levelmanager.getCurrentLevel().getLevelData());
		pausOverlay = new PauseOverlayClass(this);
	}


	@Override
	public void update() {
		if(!paused) {
			levelmanager.update();
			player.update();
		}
		else {
			pausOverlay.update();
		}
	}


	@Override
	public void draw(Graphics g) {
		levelmanager.draw(g);
		player.render(g);
		
		if(paused)
		pausOverlay.draw(g);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
		player.setAttacking(true);
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if(paused)
			pausOverlay.mousePressed(e);
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if(paused)
			pausOverlay.mouseReleased(e);
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		if(paused)
			pausOverlay.mouseMoved(e);
		
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
	    case KeyEvent.VK_ESCAPE:
	    	paused = !paused;
	    	break;
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
	
	public void unpauseGame() {
		paused = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void windowsFocusLost() {
		player.resetDirBooleans();
	}
	
	
}
