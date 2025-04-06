package ui;
import static utilts.ConstantsClass.UI.URMButtons.URM_SIZE;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GamestateEnum;
import gamestates.PlayingClass;
import main.GameClass;
import utilts.LoadSaveClass;
import static utilts.ConstantsClass.UI.URMButtons.*;

public class LevelCompletedOverlayClass {

    private  PlayingClass playing;
    private UrmButtonsClass menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;

    public LevelCompletedOverlayClass(PlayingClass playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330* GameClass.SCALE);
        int nextX = (int) (445* GameClass.SCALE);
        int y = (int) (195 * GameClass.SCALE);
        next = new UrmButtonsClass(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtonsClass(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSaveClass.GetSpriteAtlas(LoadSaveClass.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * GameClass.SCALE);
        bgH = (int) (img.getHeight() * GameClass.SCALE);
        bgX = GameClass.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * GameClass.SCALE);
    }
    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    public void update() {
        next.update();
        menu.update();
    }

    private boolean isIn(UrmButtonsClass b, MouseEvent e) {
        return b.getBounds().contains(e.getX(),e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu, e))
            menu.setMouseOver(true);
        else if(isIn(next, e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(menu, e)) {
            if(menu.isMousePressed())
                {
                playing.resetAll();
                GamestateEnum.state = GamestateEnum.MENU;
                }
        } else if(isIn(next, e))
            if(next.isMousePressed())
                playing.loadNextLevel();
                
        menu.resetBools();
        next.resetBools();
    }   
    
    public void mousePressed(MouseEvent e) {
        if(isIn(menu, e))
            menu.setMousePressed(true);
        else if(isIn(next, e))
            next.setMousePressed(true);
    }
}
