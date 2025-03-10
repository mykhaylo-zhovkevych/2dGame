package gamestates;

import entities.EnemyManagerClass;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import levels.LevelManagerClass;
import main.GameClass;
import ui.PauseOverlayClass;
import static utilts.ConstantsClass.Environment.*;
import utilts.LoadSaveClass;

public class PlayingClass extends StateClass implements Statemethods{

	private Player player;
	private LevelManagerClass levelmanager;	
	private EnemyManagerClass ememyManager;
	// varible responsible for showing the stop-screenn
	private boolean paused = true;
	private PauseOverlayClass pausOverlay;
	
	private int xLvlOffset;
	private int leftBorder = (int)(0.2 * GameClass.GAME_WIDTH);
	private int rightBorder = (int)(0.8 * GameClass.GAME_WIDTH);
	// returns the lvl width 
	private int lvlTilesWide = LoadSaveClass.GetLevelData()[0].length;
	// this variable store the actual amount of the tiles that player needs 
	private int maxTilesOffset = lvlTilesWide - GameClass.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * GameClass.TILES_SIZE;
	
	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudsPos;
	private Random rnd = new Random();
	
	public PlayingClass(GameClass game) {
		super(game);
		initClasses();
		
		backgroundImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.PLAYING_BG_IMG);
		bigCloud = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.BIG_CLOUDS);
		smallCloud = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.SMALL_CLOUDS);
		smallCloudsPos = new int[8]; 
		for(int i = 0; i < smallCloudsPos.length; i++)
			smallCloudsPos[i] = (int)(70 * GameClass.SCALE) + rnd.nextInt((int)(100 * GameClass.SCALE));
	
	}

	private void initClasses() {
		levelmanager = new LevelManagerClass(game);
		ememyManager = new EnemyManagerClass(this);
		player = new Player(200, 200, (int) (64 * GameClass.SCALE), (int) (40 * GameClass.SCALE));
		player.loadLvlData(levelmanager.getCurrentLevel().getLevelData());
		pausOverlay = new PauseOverlayClass(this);
	}

	
	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, GameClass.GAME_WIDTH,GameClass.GAME_HEIGHT, null);
		
		drawClouds(g);
		
		levelmanager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		ememyManager.draw(g, xLvlOffset);
		if(paused) {
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);
			pausOverlay.draw(g);
		}
	}	

	@Override
	public void update() {
		if(!paused) {
			levelmanager.update();
			player.update();
			ememyManager.update(levelmanager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}
		else {
			pausOverlay.update();
		}
	}
	
	private void drawClouds(Graphics g) {
		
		for(int i = 0; i < 3; i++)
			g.drawImage(bigCloud, 0 + i  * BIG_CLOUD_WIDTH - (int)(xLvlOffset * 0.3),(int)(204 * GameClass.SCALE) ,BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		
		for(int i = 0; i < smallCloudsPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
		
	}

		// this methode that check if player position beyond any border of the lvl
		private void checkCloseToBorder() {
			int playerX = (int) player.getHitbox().x;
			// if the difference more that the border that i know that player is outside of the rendered lvl
			int diff = playerX - xLvlOffset;
			
			if(diff > rightBorder) {
				xLvlOffset += diff -rightBorder;
			}
			else if(diff < leftBorder) {
				xLvlOffset += diff - leftBorder;
			}
			if(xLvlOffset > maxLvlOffsetX)
				xLvlOffset = maxLvlOffsetX;
			else if(xLvlOffset < 0)
				xLvlOffset = 0;
		}

	
	public void mouseDragged(MouseEvent e) {
		if(paused)
			pausOverlay.mouseDragged(e);
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
