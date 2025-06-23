package jumpman;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int width = 20, height = 20;
    private int xSpeed = 0, ySpeed = 0;

    private boolean left, right, jumping;
    private final int moveSpeed = 5;
    private final int jumpPower = -15;
    private final int gravity = 1;
    private boolean inAir = false;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
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

        if (y > 500) {
            y = 500;
            ySpeed = 0;
            inAir = false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) left = true;
        if (e.getKeyCode() == KeyEvent.VK_D) right = true;
        if (e.getKeyCode() == KeyEvent.VK_W) jumping = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) right = false;
        if (e.getKeyCode() == KeyEvent.VK_W) jumping = false;
    }

    public int getX() {
        return x;
    }
}