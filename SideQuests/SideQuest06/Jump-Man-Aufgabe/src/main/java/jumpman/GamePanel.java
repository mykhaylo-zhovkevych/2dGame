package jumpman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private BufferedImage bgSky, bgSpace, currentBg;

    private List<Platform> platforms = new ArrayList<>();
    private boolean gameOver = false;
    private int bgYOffset = 0;

    // TODO: Deklariere hier Variablen für Scrollgeschwindigkeit, Maximalgeschwindigkeit und Beschleunigung

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        setBackground(Color.BLACK);
        addKeyListener(this);

        try {
            bgSky = ImageIO.read(getClass().getResource("/res/background_sky.png"));
            bgSpace = ImageIO.read(getClass().getResource("/res/background_space.png"));
            currentBg = bgSky;
        } catch (IOException e) {
            System.out.println("Hintergrundbild konnte nicht geladen werden.");
        }

        // Starte mit einer festen Plattform
        platforms.add(new Platform(100, 520, 100, 10));

        // Plattformen über dem Bildschirm generieren
        int currentY = 520;
        while (currentY > -400) {
            currentY -= 80;
            int count = 1 + (int)(Math.random() * 2);
            for (int i = 0; i < count; i++) {
                int x = 50 + (int)(Math.random() * 600);
                platforms.add(new Platform(x, currentY, 100, 10));
            }
        }

        player = new Player(120, 500);
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TODO: Zeichne das Hintergrundbild mehrfach so, dass es nach unten scrollt (loopend)
        // Tipp: Nutze scrollSpeed + % Höhe des Bildes

        for (Platform p : platforms) {
            p.draw(g);
        }

        player.draw(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Game Over", 300, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Erhöhe die Scrollgeschwindigkeit kontinuierlich (z. B. mit acceleration)
        // TODO: Bewege Plattformen mit scrollSpeed nach unten
        // TODO: Wechsle das Hintergrundbild, wenn Punkte >= 10 (bgSky <-> bgSpace)

        // Entferne Plattformen unterhalb des Bildschirms
        platforms.removeIf(p -> p.y > 600);

        // Erzeuge neue Plattformen oberhalb
        int highestY = platforms.stream().mapToInt(p -> p.y).min().orElse(600);
        while (highestY > -240) {
            int count = 1 + (int)(Math.random() * 2);
            for (int i = 0; i < count; i++) {
                int newX = 50 + (int)(Math.random() * 600);
                platforms.add(new Platform(newX, highestY - 80, 100, 10));
            }
            highestY -= 80;
        }

        player.update(platforms);

        if (player.getLives() <= 0) {
            gameOver = true;
            timer.stop();
        }

        repaint();
    }

    @Override public void keyPressed(KeyEvent e) { player.keyPressed(e); }
    @Override public void keyReleased(KeyEvent e) { player.keyReleased(e); }
    @Override public void keyTyped(KeyEvent e) {}
}
