package levels;

import gamestates.GamestateEnum;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GameClass;
import utilts.LoadSaveClass;

public class LevelManagerClass {
	
	private GameClass game;
	private BufferedImage[] levelSprite;
	private ArrayList<LevelClass> levels;
	private int lvlIndex = 0;
	
	public LevelManagerClass(GameClass game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
		
	}

	public void loadNextLevel() {
		lvlIndex++;
		if (lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("No more Levels! Game Completed!");
			GamestateEnum.state = GamestateEnum.MENU;
		}

		LevelClass newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManagerClass().loadObjects(newLevel);
	}

	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSaveClass.GetAllLevels();
		for(BufferedImage img : allLevels)
			levels.add(new LevelClass(img));
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


	public void draw(Graphics g, int lvlOffset) {
		for(int j = 0; j < GameClass.TILES_IN_HEIGHT; j++)
			for(int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).GetSpriteIndex(i, j);
				// size of the tiles TILES_SIZE
				g.drawImage(levelSprite[index], GameClass.TILES_SIZE * i - lvlOffset,GameClass.TILES_SIZE * j ,GameClass.TILES_SIZE,GameClass.TILES_SIZE, null);
			}
	}
	public void update() {
		
	}
	public LevelClass getCurrentLevel() {
		return levels.get(lvlIndex);
	}

	public int getAmountOfLevels() {
		return levels.size();
	}
}
