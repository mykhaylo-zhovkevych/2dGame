package objects;

import entities.Player;
import gamestates.PlayingClass;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.LevelClass;
import static utilts.ConstantsClass.ObjectConstants.*;
import utilts.LoadSaveClass;

public class ObjectManagerClass {

    private PlayingClass playingClass;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage spikeImg;
    private ArrayList<PotionClass> potions;
    private ArrayList<GameContainerClass> containers;
    private ArrayList<SpikeClass> spikes;

    public ObjectManagerClass(PlayingClass playingClass) {
        this.playingClass = playingClass;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (SpikeClass s : spikes)
        // Die Methode intersects prüft, ob die Hitbox des Spielers mit der Hitbox des Spikes kollidiert
            if (s.getHitbox().intersects(p.getHitbox())) {
                p.kill();
        }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (PotionClass p : potions)
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
    }

    public void applyEffectToPlayer(PotionClass p) {
        if (p.getObjType() == RED_POTION)
            playingClass.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playingClass.getPlayer().changePower(BLUE_POTION_VALUE);
    }

    public void checkObjectHit(Rectangle2D.Float attackbox) {
        for (GameContainerClass gc : containers)
            if (gc.isActive() && !gc.doAnimation) {
                if (gc.getHitbox().intersects(attackbox)) {
                    gc.setAnimation(true);
                    int type = 0;
                    if (gc.getObjType() == BARREL)
                        type = 1;
                    // Spawn potion at center-top of the container
                    potions.add(new PotionClass(
                        (int) (gc.getHitbox().x + gc.getHitbox().width / 2),
                        (int) (gc.getHitbox().y - POTION_HEIGHT / 2),
                        type
                    ));
                    return;
                }
            }
    }

    public void loadObjects(LevelClass newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for (int j = 0; j < potionImgs.length; j++)
            for (int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

        BufferedImage containerSprite = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
        
        spikeImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.TRAP_ATLAS);
    
    }

    public void update() {
        for (PotionClass p : potions)
            if (p.isActive())
                p.update();

        for (GameContainerClass gc : containers)
            if (gc.isActive())
                gc.update();
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawSpikes(g, xLvlOffset);
    }
    private void drawSpikes(Graphics g, int xLvlOffset) {
        for (SpikeClass s : spikes) 
            g.drawImage(
                spikeImg,
                (int) (s.getHitbox().x - xLvlOffset),
                (int) (s.getHitbox().y - s.getyDrawOffset()),
                SPIKE_WIDTH,
                SPIKE_HEIGHT,
                null
            );
    }
    

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainerClass gc : containers)
            if (gc.isActive()) {
                int type = 0;
                if (gc.getObjType() == BARREL)
                    type = 1;
                g.drawImage(
                    containerImgs[type][gc.getAniIndex()],
                    (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                    (int) (gc.getHitbox().y - gc.getyDrawOffset()),
                    CONTAINER_WIDTH,
                    CONTAINER_HEIGHT,
                    null
                );
            }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (PotionClass p : potions)
            if (p.isActive()) {
                int type = 0;
                if (p.getObjType() == RED_POTION)
                    type = 1;
                g.drawImage(
                    potionImgs[type][p.getAniIndex()],
                    (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                    (int) (p.getHitbox().y - p.getyDrawOffset()),
                    POTION_WIDTH,
                    POTION_HEIGHT,
                    null
                );
            }
    }

    public void resetAllObjects() {

        // this is to prevent ArrayIndexOutOfBoundsException, because when a game is reseted or new level is loaded old objects are not removed
        // System.out.println("Size of arrays: " + potions.size() + " - " + containers.size());
        loadObjects(playingClass.getLevelManager().getCurrentLevel());
        // System.out.println("Size of arrays after: " + potions.size() + " - " + containers.size());

        for (PotionClass p : potions)
            p.reset();

        for (GameContainerClass gc : containers)
            gc.reset();
    }
}