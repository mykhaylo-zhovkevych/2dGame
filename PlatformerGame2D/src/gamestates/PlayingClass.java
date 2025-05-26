package gamestates;

import entities.EnemyManagerClass;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import levels.LevelManagerClass;
import main.GameClass;
import objects.ObjectManagerClass;
import ui.GameOverOverlayClass;
import ui.LevelCompletedOverlayClass;
import ui.PauseOverlayClass;
import static utilts.ConstantsClass.Environment.*;
import utilts.LoadSaveClass;

public class PlayingClass extends StateClass implements Statemethods{

	private Player player;
	private LevelManagerClass levelmanager;	
	// The Naming is not correct, but idk how much I used that variable in the code so I will leave it just how it is 
	private EnemyManagerClass ememyManager;
	// varible responsible for showing the stop-screenn
	private GameOverOverlayClass gameOverOverlay;
	private LevelCompletedOverlayClass levelCompletedOverlayClass;
	private boolean paused = true;
	private PauseOverlayClass pausOverlay;
	//added by Rudy Potions feature
	private ObjectManagerClass objectManagerClass;
	private int xLvlOffset;
	private int leftBorder = (int)(0.2 * GameClass.GAME_WIDTH);
	private int rightBorder = (int)(0.8 * GameClass.GAME_WIDTH);
	private int maxLvlOffsetX;
	
	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudsPos;
	private Random rnd = new Random();

	private boolean gameOver = false;
	private boolean lvlCompleted;
	
	public PlayingClass(GameClass game) {
		super(game);
		initClasses();
		
		backgroundImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.PLAYING_BG_IMG);
		bigCloud = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.BIG_CLOUDS);
		smallCloud = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.SMALL_CLOUDS);
		smallCloudsPos = new int[8]; 
		for(int i = 0; i < smallCloudsPos.length; i++)
			smallCloudsPos[i] = (int)(70 * GameClass.SCALE) + rnd.nextInt((int)(100 * GameClass.SCALE));

			calcLvlOffset();
			loadStartLevel();
	}

	public void loadNextLevel() {
		resetAll();
		levelmanager.loadNextLevel();
		player.setSpawn(levelmanager.getCurrentLevel().getPlayerSpawn());
	}

	private void loadStartLevel() {
		ememyManager.loadEnemies(levelmanager.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelmanager.getCurrentLevel().getLvlOffset();
	}

	private void initClasses() {
		levelmanager = new LevelManagerClass(game);
		ememyManager = new EnemyManagerClass(this);
		//init objectmanager
		objectManagerClass = new ObjectManagerClass(this);
		player = new Player(200, 200, (int) (64 * GameClass.SCALE), (int) (40 * GameClass.SCALE), this);
		player.loadLvlData(levelmanager.getCurrentLevel().getLevelData());
		player.setSpawn(levelmanager.getCurrentLevel().getPlayerSpawn());

		pausOverlay = new PauseOverlayClass(this);
		gameOverOverlay = new GameOverOverlayClass(this);
		levelCompletedOverlayClass = new LevelCompletedOverlayClass(this);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, GameClass.GAME_WIDTH,GameClass.GAME_HEIGHT, null);
		
		drawClouds(g);
		
		levelmanager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		ememyManager.draw(g, xLvlOffset);
		objectManagerClass.draw(g, xLvlOffset);

		
		if(paused) {
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);
			pausOverlay.draw(g);
		} else if (gameOver) 
			gameOverOverlay.draw(g);
		else if(lvlCompleted)
			levelCompletedOverlayClass.draw(g);
		
	}	

	@Override
	public void update() {
		if(paused) {
			pausOverlay.update();
		} else if(lvlCompleted) {
			levelCompletedOverlayClass.update();
		}else if(!gameOver){
			levelmanager.update();
			objectManagerClass.update();
			player.update();
			ememyManager.update(levelmanager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}
	}
	
	private void drawClouds(Graphics g) {
		
		for(int i = 0; i < 3; i++)
			g.drawImage(bigCloud, 0 + i  * BIG_CLOUD_WIDTH - (int)(xLvlOffset * 0.3),(int)(204 * GameClass.SCALE) ,BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		
		for(int i = 0; i < smallCloudsPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);	
	}

	public void resetAll() {
		//TODO: reset playing, enemy, lvl etc
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		ememyManager.resetAllEnemies();
		
		
	}

	public void setGAmeOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		ememyManager.checkEnemyHit(attackBox);
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
		if (!gameOver) 
			if(paused)
				pausOverlay.mouseDragged(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver) 
			if(e.getButton() == MouseEvent.BUTTON1) {
			player.setAttacking(true);
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if(paused)
				pausOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletedOverlayClass.mousePressed(e);
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if(paused)
				pausOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletedOverlayClass.mouseReleased(e);
		}
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if(paused)
				pausOverlay.mouseMoved(e);
		else if(lvlCompleted)
				levelCompletedOverlayClass.mouseMoved(e);
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
		if (gameOver)  
			gameOverOverlay.keyPressed(e);
		else
		switch(e.getKeyCode()) {
	    
	    case KeyEvent.VK_A:
	    	player.setLeft(true);
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
		if (!gameOver) 
		switch(e.getKeyCode()) {
	    
	    case KeyEvent.VK_A:
	    	player.setLeft(false);
	        break;
	    
	    case KeyEvent.VK_D:
	    	player.setRight(false);
	        break;
	    case KeyEvent.VK_SPACE:
	    	player.setJump(false);
	    	break;
	    }  
		
	}

	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
	}

	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public Player getPlayer() {
		return player;
	}

	public EnemyManagerClass getEnemyManager() {
		return ememyManager;
	}
	
	public void windowsFocusLost() {
		player.resetDirBooleans();
	}
	//getter from objectmanager (potions)
	public ObjectManagerClass getObjectManagerClass(){
		return objectManagerClass;
	}
		
}