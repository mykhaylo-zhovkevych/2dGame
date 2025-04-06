package main;

import java.awt.Graphics;
import gamestates.GamestateEnum;
import gamestates.MenuClass;
import gamestates.PlayingClass;
import utilts.LoadSaveClass;

public class GameClass implements Runnable{
// this class is for gluing together anther classes
	private GameWindowClass gameWindowClass;
	private GamePanelClass gamePanelClass;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private PlayingClass playing;
	private MenuClass menu;

	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2.0f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	
// constructor in java can be taken as head method
	public GameClass() {

		initClasses();
		gamePanelClass = new GamePanelClass(this);
		gameWindowClass = new GameWindowClass(gamePanelClass); // referential object of GameWindowClass
		gamePanelClass.setFocusable(true);
		gamePanelClass.requestFocus();
		
		startGameLoop();
	}
	
	// method for initialising all entities events controller; 
	private void initClasses() {
		
		menu = new MenuClass(this);
		playing = new PlayingClass(this);

	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}


// GAME LOOP 
	public void update() {
		
		switch(GamestateEnum.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
		case QUIT:
		default:
			System.exit(0);
			break;
		}
	}
	
	public void render(Graphics g) {

		switch(GamestateEnum.state) {
		case MENU:
			menu.draw(g);			
			break;
		case PLAYING:
			playing.draw(g);	
			break;
		default:
			break;
		}
	}
	@Override
	public void run() {
	/*  
		Time per frame = 1,000,000,000 nanoseconds / 144 frames
        Time per frame ≈ 6,944,444.44 nanoseconds per frame
        This means that to achieve a frame rate of 144 FPS, each frame should ideally be displayed for about 6,944,444 nanoseconds. 
        The game loop uses this interval to pace the rendering: 
        it checks if this amount of time has elapsed since the last frame was rendered, and if so, it renders the next frame.
    */
 		
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
//		long lastFrame = System.nanoTime();
//		long now = System.nanoTime();
		
		long previousTime = System.nanoTime();
		
		
		int frames = 0;
		int updates = 0;
	
		long lastCheck = 0;
		double deltaU = 0;
		double deltaF = 0;
		
			while (true) {
//				now = System.nanoTime();
				
				long correntTime = System.nanoTime();
				deltaU += (correntTime - previousTime) / timePerUpdate;
				deltaF += (correntTime - previousTime) / timePerFrame;
				previousTime = correntTime;
				
				if(deltaU >= 1) {
					update();
					updates++;
					deltaU--;
				// the wated time will be stored into the deltaU and will be used for the next update to come a little bit sooner so no time will be wasted 
				}
				
				
				if(deltaF >= 1) {
					// this one is tricky it go to the gamePanelClass do code and returns back to the GameClass to use the render in here
					gamePanelClass.repaint();
					frames ++;
					deltaF --;
				}
				
//				// here it takes the currect time and check if the tame for new frame has came up or not
//				if(now - lastFrame >= timePerFrame) {
//				// here it checks if the time is not only equal but also if it is more when it is more then the frame will be lost 	
//					gamePanelClass.repaint();
//					lastFrame = now;
//					frames++;
//				}
				
				if(System.currentTimeMillis() - lastCheck >= 1000 ) {
					lastCheck = System.currentTimeMillis();
					System.out.println("FPS  "+ frames + " | UPS: " + updates);
					frames = 0;
					updates = 0;
				}	
			}
		}
	// never care if it is in the menu 
	public void windowsFocusLost() {
		if (GamestateEnum.state == GamestateEnum.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	
	}
	
	public MenuClass getMenu() {
		return menu;
	}
	
	public PlayingClass getPlaying() {
		return playing;
	}
}
