package main;

public class GameClass implements Runnable{
// this class is for gluing together anther classes
	private GameWindowClass gameWindowClass;
	private GamePanelClass gamePanelClass;
	private Thread gameThread;
	private final int FPS_SET = 144;
// constructor in java can be taken as head method
	public GameClass() {
		gamePanelClass = new GamePanelClass();
		gameWindowClass = new GameWindowClass(gamePanelClass); // referential object of GameWindowClass
		gamePanelClass.requestFocus();
		startGameLoop();
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}



	@Override
	public void run() {
	
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		
		int frames = 0;
		long lastCheck = 0;
		
			while (true) {
				now = System.nanoTime();
				if(now - lastFrame >= timePerFrame) {
					
					gamePanelClass.repaint();
					lastFrame = now;
					frames++;
				}
				
				if(System.currentTimeMillis() - lastCheck >= 1000 ) {
					lastCheck = System.currentTimeMillis();
					System.out.println("FPS "+ frames);
					frames = 0;
				}	
			}
		}
	}
