package levels;

public class LevelClass {

	private int[][] lvlData;
	
	// constructor
	public LevelClass(int[][] lvlData) {
		this.lvlData = lvlData;
	}
	public int GetSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	public int[][] getLevelData() {
		return lvlData;
	}
}
