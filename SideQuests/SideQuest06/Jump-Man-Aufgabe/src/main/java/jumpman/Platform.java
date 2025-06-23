package jumpman;

import java.awt.*;

public class Platform extends GameObject {
    public Platform(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void moveDown() {
        y += 1;
    }
}
