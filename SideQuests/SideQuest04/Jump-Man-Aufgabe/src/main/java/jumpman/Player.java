package jumpman;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int width = 20, height = 20;
    private int xSpeed = 0, ySpeed = 0;

    // TODO: Definiere hier die Steuerungs-Flags (left, right, jumping)

    private final int moveSpeed = 5;
    private final int jumpPower = -15;
    private final int gravity = 1;
    private boolean inAir = false;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        // TODO: Berechne xSpeed basierend auf Tasteneingaben (left, right)

        // TODO: Wenn jumping und nicht inAir, setze ySpeed auf jumpPower

        // TODO: Füge Gravity zur y-Bewegung hinzu

        // TODO: Aktualisiere x und y basierend auf xSpeed und ySpeed

        // TODO: Implementiere einfache Boden-Kollision bei y > 500
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        // TODO: Setze bei A/D/W den entsprechenden Bewegungs-Flag auf true
    }

    public void keyReleased(KeyEvent e) {
        // TODO: Setze bei A/D/W den entsprechenden Bewegungs-Flag auf false
    }

    public int getX() {
        return x;
    }
}