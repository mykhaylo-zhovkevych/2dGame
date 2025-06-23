package levels;


import entities.CrabbyClass;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GameClass;
import objects.CannonClass;
import objects.GameContainerClass;
import objects.PotionClass;
import objects.SpikeClass;
import utilts.HelpMethodsClass;
import static utilts.HelpMethodsClass.GetCrabs;
import static utilts.HelpMethodsClass.GetLevelData;
import static utilts.HelpMethodsClass.GetPlayerSpawn;

public class LevelClass {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<CrabbyClass> crabs;
	private ArrayList<PotionClass> potions;
	private ArrayList<SpikeClass> spikes;
	private ArrayList<GameContainerClass> containers;
	private ArrayList<CannonClass> cannons;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	// constructor
	public LevelClass(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createPotions();
		createContainers();
		createSpikes();
		createCannons();
		calcLvlOffset();
		calcPlayerSpawn();

	}

	private void createCannons() {
		cannons = HelpMethodsClass.GetCannonClasses(img);
	}

	private void createSpikes() {
		spikes = HelpMethodsClass.GetSpikes(img);
	}

	private void createContainers() {
		containers = HelpMethodsClass.GetContainers(img);
	}

	private void createPotions() {
		potions = HelpMethodsClass.GetPotions(img);
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

	public ArrayList<PotionClass> getPotions(){
		return potions;
	}

	public ArrayList<GameContainerClass> getContainers(){
		return containers;
	}

	public ArrayList<SpikeClass> getSpikes() {
		return spikes;
	}

	public ArrayList<CannonClass> getCannons() {
		return cannons;
	}

}