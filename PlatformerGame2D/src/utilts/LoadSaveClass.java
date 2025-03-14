package utilts;

import entities.CrabbyClass;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GameClass;
import static utilts.ConstantsClass.EnemyConstants.*;

// in this class will be only the static methods for not creating the objects 
public class LoadSaveClass {
	// the idea is that i will have one method that can take as much parameter as needed
		
	public static final String PLAYER_ATLAS = "player_sprites.png";	
	public static final String LEVEL_ATLAS = "outside_sprites.png";	
	//	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
	public static final String MENU_BUTTONS = "button_atlas.png";	
	public static final String MENU_BACKGROUND = "menu_background.png";
	
	public static final String PAUS_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";	
	public static final String URM_BUTTONS = "urm_buttons.png";	
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String PLAYING_BG_IMG = "playing_bg_img.png";
	public static final String BIG_CLOUDS = "big_clouds.png";
	public static final String SMALL_CLOUDS = "small_clouds.png";
	
	public static final String CRABBY_SPRITE = "crabby_sprite.png";
	public static final String STATUS_BAR = "health_power_bar.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSaveClass.class.getResourceAsStream("/" + fileName);
        try {
        img = ImageIO.read(is);
         
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
	}
	
	public static ArrayList<CrabbyClass> GetCrabs() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
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
	
	public static int[][] GetLevelData() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
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
}
