package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestates.PlayingClass;
import levels.LevelClass;
import main.GameClass;
import static utilts.ConstantsClass.ObjectConstants.BARREL;
import static utilts.ConstantsClass.ObjectConstants.BLUE_POTION_VALUE;
import static utilts.ConstantsClass.ObjectConstants.CANNON_HEIGHT;
import static utilts.ConstantsClass.ObjectConstants.CANNON_LEFT;
import static utilts.ConstantsClass.ObjectConstants.CANNON_RIGHT;
import static utilts.ConstantsClass.ObjectConstants.CANNON_WIDTH;
import static utilts.ConstantsClass.ObjectConstants.CONTAINER_HEIGHT;
import static utilts.ConstantsClass.ObjectConstants.CONTAINER_WIDTH;
import static utilts.ConstantsClass.ObjectConstants.POTION_HEIGHT;
import static utilts.ConstantsClass.ObjectConstants.POTION_WIDTH;
import static utilts.ConstantsClass.ObjectConstants.RED_POTION;
import static utilts.ConstantsClass.ObjectConstants.RED_POTION_VALUE;
import static utilts.ConstantsClass.ObjectConstants.SPIKE_HEIGHT;
import static utilts.ConstantsClass.ObjectConstants.SPIKE_WIDTH;
import static utilts.ConstantsClass.Projecttiles.CANNON_BALL_HEIGHT;
import static utilts.ConstantsClass.Projecttiles.CANNON_BALL_WIDTH;
import static utilts.HelpMethodsClass.CanCannonSeePlayer;
import static utilts.HelpMethodsClass.IsProjectileHittingLevel;
import utilts.LoadSaveClass;


public class ObjectManagerClass {

    private PlayingClass playingClass;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage[] cannonImgs;
    private BufferedImage spikeImg, cannonBallImg;
    private ArrayList<PotionClass> potions;
    private ArrayList<GameContainerClass> containers;
    private ArrayList<SpikeClass> spikes;
    private ArrayList<CannonClass> cannons;
    private ArrayList<ProjecttileClass> projecttiles = new ArrayList<>();

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
        cannons = newLevel.getCannons();

        // Method clears the projecttiles list, so it doesn't contain old projecttiles and this is used when a new level is loaded
        projecttiles.clear();
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
        cannonImgs = new BufferedImage[7];
        BufferedImage temp = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.CANNON_ATLAS);

        for (int i = 0; i < cannonImgs.length; i++) {
            cannonImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);
        }

        cannonBallImg = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.CANNON_BALL);

    
    }

    public void update(int[][] lvlData, Player player) {
        for (PotionClass p : potions)
            if (p.isActive())
                p.update();

        for (GameContainerClass gc : containers)
            if (gc.isActive())
                gc.update();

        updateCannons(lvlData, player);
        updateProjecttiles(lvlData, player);

    }

    private void updateProjecttiles(int[][] lvlData, Player player) {
        for (ProjecttileClass p : projecttiles) {
            if (p.isActive()) {
                p.updatePos();

                    if (p.getHitbox().intersects(player.getHitbox())) {
                        player.changeHealth(-25);
                        p.setActive(false);

                    } else if(IsProjectileHittingLevel(p, lvlData)) {
                            p.setActive(false);
                    
                }
            }
        }
    }

    private boolean isPlayerInRange(CannonClass c, Player player) {
      int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
        return absValue <= GameClass.TILES_SIZE * 5; // 5 tiles range
    }

    private boolean isPlayerInFrontOfCannon(CannonClass c, Player palyer) {
        if (c.getObjType() == CANNON_LEFT) {
            if (c.getHitbox().x > palyer.getHitbox().x)
                return true;
        } else if (c.getHitbox().x < palyer.getHitbox().x)
            return true;
        return false;
    }

    // if the cannon is not animated, skip it
            // TileY is the same
            // If Player is in rage
            // If Player infront of cammon
            // line of sight is clear
            // shoot the cannon

    // All cannons are updated in one method to avoid multiple loops
    private void updateCannons(int[][] lvlData, Player player) {
        for (CannonClass c : cannons) {
            if (!c.doAnimation) 
                if (c.getTileY() == player.getTileY()) 
                    if (isPlayerInRange(c, player))
                        if (isPlayerInFrontOfCannon(c, player))
                            if (CanCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY())) 
                                c.setAnimation(true);
                c.update();
                if (c.getAniIndex() == 4 && c.getAniTick() == 0){
                    shootCannon(c);
                }

             
        }
    }

    private void shootCannon (CannonClass c) {
      

        int dir = 1;
        if (c.getObjType() == CANNON_LEFT)
            dir = -1;

        projecttiles.add(new ProjecttileClass((int)c.getHitbox().x, (int)c.getHitbox().y, dir));

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawSpikes(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
		for (ProjecttileClass p : projecttiles)
			if (p.isActive())
				g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);

	}

    private void drawCannons(Graphics g, int xLvlOffset) {
        for(CannonClass c : cannons) {
            int x = (int)(c.getHitbox().x - xLvlOffset);
            int width = CANNON_WIDTH;

            if(c.getObjType() == CANNON_RIGHT) {
                x += width;
                // This causes the image to be drawn mirrored (flipped horizontally), so the cannon faces right.
                width *= -1;

            }
            g.drawImage(cannonImgs[c.getAniIndex()], x, (int)(c.getHitbox().y), width, CANNON_HEIGHT, null);
        }

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

        for (CannonClass c : cannons)
            c.reset();
    }
}