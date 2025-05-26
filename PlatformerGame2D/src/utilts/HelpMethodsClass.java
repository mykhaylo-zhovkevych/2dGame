package utilts;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.CrabbyClass;
import main.GameClass;
import objects.GameContainerClass;
import objects.PotionClass;
import static utilts.ConstantsClass.ObjectConstants.*;
public class HelpMethodsClass {

	// War nicht im video
	private static final int CRABBY = 0;

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		
		if(!IsSolid(x,y,lvlData)) 
			if(!IsSolid(x+width,y+height,lvlData))
				if(!IsSolid(x+width,y,lvlData))
					if(!IsSolid(x,y+height,lvlData))
						return true;
		return false;
	}
	// this will if this is the Tile an if the position id inside of the GameWindow
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * GameClass.TILES_SIZE;
		if(x < 0 || x >= maxWidth)
			return true;
		if(y < 0 || y >= GameClass.GAME_HEIGHT)
			return true;
		
		float xIndex = x / GameClass.TILES_SIZE;
		float yIndex = y / GameClass.TILES_SIZE;
		
		return IsTileSolid((int)xIndex,(int)yIndex, lvlData);
		
	}

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData){
		int value = lvlData[yTile][xTile];
		
		// only 48 sprites there is more mean no tiles at all
		if(value >= 48 || value < 0 || value != 11) // one is transparent that is why that should be passed through 
			return true;
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / GameClass.TILES_SIZE);
		if (xSpeed > 0) {
			// Right 
			int tileXPos = currentTile * GameClass.TILES_SIZE;
			int xOffset = (int)(GameClass.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
			}
		else {
			// Left
			return currentTile * GameClass.TILES_SIZE;
		}
	}
	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / GameClass.TILES_SIZE);
		if (airSpeed > 0) {
			int tileYPos = currentTile * GameClass.TILES_SIZE;
			int yOffset = (int)(GameClass.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
			// Falling - touching floor
		} else {
			// Jumping 
			return currentTile * GameClass.TILES_SIZE;
		}
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		
		return true;
	}

	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if(xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		else
			return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}

	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData){
		for(int i = 0; i < xEnd - xStart; i++){
			if(IsTileSolid(xStart + i, y, lvlData))
				return false;
			if(!IsTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}
			return true;
	}

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox,
	 Rectangle2D.Float secondHitbox, int yTile){
		int firstXTile = (int)(firstHitbox.x / GameClass.TILES_SIZE);
		int secondXTile = (int)(secondHitbox.x / GameClass.TILES_SIZE);

		if(firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else 
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
	}

	public static int[][] GetLevelData(BufferedImage img) {
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for(int j = 0; j < img.getHeight(); j++)
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				// in case is there index bigger than in GetSpriteAtlas reset it to the 0 for preventing Array out of the boundaries
				if(value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}

		public static ArrayList<CrabbyClass> GetCrabs(BufferedImage img) {
		ArrayList<CrabbyClass> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CRABBY)
					list.add(new CrabbyClass(i * GameClass.TILES_SIZE, j * GameClass.TILES_SIZE));
			}
		return list;
		}

	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100)
					return new Point(i * GameClass.TILES_SIZE, j * GameClass.TILES_SIZE);
			}
		return new Point(1 * GameClass.TILES_SIZE, 1 * GameClass.TILES_SIZE);
	}

		public static ArrayList<PotionClass> GetPotions(BufferedImage img) {
		ArrayList<PotionClass> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == RED_POTION || value == BLUE_POTION)
					list.add(new PotionClass(i* GameClass.TILES_SIZE, j* GameClass.TILES_SIZE, value));

			}
		return list;
		}

		

		public static ArrayList<GameContainerClass> GetContainers(BufferedImage img) {
		ArrayList<GameContainerClass> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == BOX || value == BARREL)
					list.add(new GameContainerClass(i* GameClass.TILES_SIZE, j* GameClass.TILES_SIZE, value));

			}
		return list;
		}

}
