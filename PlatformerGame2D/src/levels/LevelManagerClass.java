package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameClass;
import utilts.LoadSaveClass;

public class LevelManagerClass {
	
	private GameClass game;
	private BufferedImage[] levelSprite;
	private LevelClass levelOne;
	
	public LevelManagerClass(GameClass game) {
		this.game = game;

		importOutsideSprites();
		levelOne = new LevelClass(LoadSaveClass.GetLevelData());
		
	}
	
	
	private void importOutsideSprites() {
		BufferedImage img = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for(int j = 0; j < 4; j++) 
			for(int i = 0; i < 12; i++) {
				int index = j*12 + i;
				levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
			}
		
	}


	public void draw(Graphics g) {
		for(int j = 0; j < GameClass.TILES_IN_HEIGHT; j++)
			for(int i = 0; i < GameClass.TILES_IN_WIDTH; i++) {
				int index = levelOne.GetSpriteIndex(i, j);
				// size of the tiles TILES_SIZE
				g.drawImage(levelSprite[index], GameClass.TILES_SIZE * i,GameClass.TILES_SIZE * j ,GameClass.TILES_SIZE,GameClass.TILES_SIZE, null);
			}
	}
	public void update() {
		
	}
	public LevelClass getCurrentLevel() {
		
		return levelOne;
	}
}
