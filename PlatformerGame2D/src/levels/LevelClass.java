package levels;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.CrabbyClass;
import main.GameClass;
import static utilts.HelpMethodsClass.GetLevelData;
import static utilts.HelpMethodsClass.GetPlayerSpawn;
import static utilts.HelpMethodsClass.GetCrabs;

public class LevelClass {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<CrabbyClass> crabs;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	// constructor
	public LevelClass(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffset();
		calcPlayerSpawn();

	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffset() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - GameClass.TILES_IN_WIDTH;
		maxLvlOffsetX = GameClass.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		crabs = GetCrabs(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int GetSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<CrabbyClass> getCrabs() {
		return crabs;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
