package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.PlayingClass;
import utilts.LoadSaveClass;
import static utilts.ConstantsClass.EnemyConstants.*;

// this class will take care of the functionality of the enemies like patrol attacks etc
public class EnemyManagerClass {

	private PlayingClass playing;
	private BufferedImage[][] crabbyArr;
	private ArrayList<CrabbyClass> crabbies = new ArrayList<>();
	
	public EnemyManagerClass(PlayingClass playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
		
	}
	
	private void addEnemies() {
		crabbies = LoadSaveClass.GetCrabs();
		System.out.print("size of crabs: " + crabbies.size());
		
	}

	public void update() {
		for(CrabbyClass c : crabbies)
			c.update();
	}
	
	
	public void draw(Graphics g, int xLvlOffset) {
			drawCrabs(g, xLvlOffset);
	}
	
	private void drawCrabs(Graphics g, int xLvlOffset) {
		for(CrabbyClass c : crabbies)
			g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()], (int)c.getHitbox().x - xLvlOffset, (int) c.getHitbox().y, CRABBY_WIDTH, CRABBY_HEIGHT, null);
		
	}

	private void loadEnemyImgs() {
		crabbyArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.CRABBY_SPRITE);
		for(int j = 0; j < crabbyArr.length; j++) {
			for(int i = 0; i < crabbyArr[j].length; i++)
				crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
		}
		
	}
	
	
}
