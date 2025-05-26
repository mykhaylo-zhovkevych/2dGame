package objects;

import gamestates.PlayingClass;
import utilts.LoadSaveClass;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilts.ConstantsClass.ObjectConstants.*;
public class ObjectManagerClass {

    private PlayingClass playingClass;
    private BufferedImage[][] potionImgs, containerImgs;
    private ArrayList<PotionClass> potions;
    private ArrayList<GameContainerClass> containers;

    public ObjectManagerClass(PlayingClass playingClass){
        this.playingClass = playingClass;
        loadImgs();

        potions = new ArrayList<>();
        potions.add(new PotionClass(300, 300, RED_POTION));
        potions.add(new PotionClass(300, 400, BLUE_POTION));

        containers = new ArrayList<>();
        containers.add(new GameContainerClass(500, 300, BARREL));
        containers.add(new GameContainerClass(600, 300, BOX));
    }

    private void loadImgs(){
        BufferedImage potionSprite = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for(int j = 0; j < potionImgs.length; j++)
            for(int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(12*i, 16*j, 12, 12);
            
        

        BufferedImage containerSprite = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for(int j = 0; j < containerImgs.length; j++)
            for(int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40*i, 30*j, 40, 30);
    }

    public void update(){
        for(PotionClass p : potions){
            if(p.isActive()){
                p.update();
            }
        }
        for(GameContainerClass gc : containers){
            if(gc.isActive()){
                gc.update();
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        drawPotions(g, xLvlOffset);
        drawContainer(g, xLvlOffset);
    }

    private void drawContainer(Graphics g, int xLvlOffset) {
        for(GameContainerClass gc : containers)
            if(gc.isActive()){
                int type = 0;
                if(gc.getObjType() == BARREL){
                    type = 1;
            
                    g.drawImage(containerImgs[type][gc.getAniIndex()], 
                    (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), 
                    (int) (gc.getHitbox().y - gc.getyDrawOffset()),
                    CONTAINER_WIDTH,
                    CONTAINER_HEIGHT, 
                    null);
                }
            }        
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for(PotionClass p : potions){
            if(p.isActive()){
                int type = 0;
                if(p.getObjType() == RED_POTION)
                type = 1;
                g.drawImage(potionImgs[type][p.getAniIndex()], 
                    (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), 
                    (int) (p.getHitbox().y - p.getyDrawOffset()),
                    POTION_WIDTH,
                    POTION_HEIGHT, 
                    null);
            }
        }
    }
}
