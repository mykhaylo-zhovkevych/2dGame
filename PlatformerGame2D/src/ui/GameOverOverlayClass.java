package ui;

import gamestates.GamestateEnum;
import gamestates.PlayingClass;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import main.GameClass;


public class GameOverOverlayClass {

    private PlayingClass playing;
    public GameOverOverlayClass(PlayingClass playing) {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0,0,0, 200));
        g.fillRect(0, 0, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", GameClass.GAME_WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu!", GameClass.GAME_WIDTH / 2, 300);
    }

    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GamestateEnum.state = GamestateEnum.MENU;
        }

    }

}
