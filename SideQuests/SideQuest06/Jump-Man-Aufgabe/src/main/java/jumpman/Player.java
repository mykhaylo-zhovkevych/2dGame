package jumpman;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Player {
    private int x, y;
    private int width = 20, height = 20;
    private int xSpeed = 0, ySpeed = 0;

    private boolean left, right, jumping;
    private final int moveSpeed = 5;
    private final int jumpPower = -20;
    private final int gravity = 1;
    private boolean inAir = false;

    // TODO: Zähle Punkte und Leben (bei neuer Plattform-Kollision)
    private int score = 0;
    private int lives = 3;
    private Set<Platform> platformsTouched = new HashSet<>();

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(List<Platform> platforms) {
        if (left) xSpeed = -moveSpeed;
        else if (right) xSpeed = moveSpeed;
        else xSpeed = 0;

        if (jumping && !inAir) {
            ySpeed = jumpPower;
            inAir = true;
        }

        ySpeed += gravity;
        x += xSpeed;
        y += ySpeed;

        boolean touched = false;
        for (Platform p : platforms) {
            if (getBounds().intersects(p.getBounds()) && ySpeed > 0) {
                y = p.y - height;
                ySpeed = 0;
                inAir = false;
                touched = true;

                // TODO: Punkte nur bei neuer Plattform (nicht doppelt zählen!)
                if (!platformsTouched.contains(p)) {
                    platformsTouched.add(p);
                    score++;
                }
            }
        }

        if (!touched) inAir = true;

        if (y > 600) {
            lives--;
            if (lives > 0) {
                x = 120;
                y = 500;
                ySpeed = 0;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, width, height);

        // TODO: Zeichne Punktestand und Leben oben links
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) left = true;
        if (e.getKeyCode() == KeyEvent.VK_D) right = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) jumping = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) right = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) jumping = false;
    }

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
