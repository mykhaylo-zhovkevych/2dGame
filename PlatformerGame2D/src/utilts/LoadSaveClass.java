package utilts;

import entities.CrabbyClass;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GameClass;
import static utilts.ConstantsClass.EnemyConstants.*;

// in this class will be only the static methods for not creating the objects 
public class LoadSaveClass {
	// the idea is that i will have one method that can take as much parameter as needed
		
	public static final String PLAYER_ATLAS = "player_sprites.png";	
	public static final String LEVEL_ATLAS = "outside_sprites.png";	
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
	public static final String COMPLETED_IMG = "completed_sprite.png";


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


	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSaveClass.class.getResource("/lvls");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++){
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];
			}


		BufferedImage[] imgs = new BufferedImage[filesSorted.length];

		for(int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return imgs;

	}
	

	
}
