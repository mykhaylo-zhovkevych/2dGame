package utilts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.GameClass;

// in this class will be only the static methods for not creating the objects 
public class LoadSaveClass {
	// the idea is that i will have one method that can take as much parameter as needed
		
	public static final String PLAYER_ATLAS = "player_sprites012.png";	
	public static final String LEVEL_ATLAS = "outside_sprites.png";	
	public static final String LEVEL_ONE_DATA = "level_one_data.png";	
		
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
	public static int[][] GetLevelData() {
		int[][] lvlData = new int[GameClass.TILES_IN_HEIGHT][GameClass.TILES_IN_WIDTH]; 
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		
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
