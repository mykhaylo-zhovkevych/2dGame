package jumpman;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Player {
    private int x, y;
    private int width = 20, height = 20;
    private int xSpeed = 0, ySpeed = 0;

    // TODO: Definiere die Steuerungs-Variablen left, right, jumping

    private final int moveSpeed = 6;
    private final int jumpPower = -20;
    private final int gravity = 1;
    private boolean inAir = false;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(List<Platform> platforms) {
        // TODO: Bewegung nach links/rechts umsetzen

        // TODO: Sprung starten, wenn jumping und nicht inAir

        // TODO: Gravitation anwenden

        // TODO: Kollisionsprüfung mit Plattformen (nur wenn fallend)

        // TODO: Spielerposition aktualisieren
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        // TODO: Tastendrücke abfragen (A, D, W) und entsprechende Variablen setzen
    }

    public void keyReleased(KeyEvent e) {
        // TODO: Tasten loslassen behandeln
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}