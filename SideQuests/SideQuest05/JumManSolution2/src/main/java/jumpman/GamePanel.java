package jumpman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private BufferedImage bgImage;
    private int bgXOffset = 0;
    private List<Platform> platforms = new ArrayList<>();
    private boolean gameOver = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);

        try {
            bgImage = ImageIO.read(getClass().getResource("/res/background.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Hintergrundbild nicht gefunden oder konnte nicht geladen werden.");
        }

        // Initiale Plattform direkt unter dem Spieler
        Platform startPlatform = new Platform(100, 520, 100, 10);
        platforms.add(startPlatform);

        // Zusätzliche Plattformen
        for (int i = 1; i < 10; i++) {
            int x = (int)(Math.random() * 600 + 50); // mehr Abstand zu Rändern
            int y = 600 - i * 80; // größere vertikale Abstände
            platforms.add(new Platform(x, y, 60, 10)); // breitere Plattform
        }

        player = new Player(120, 500); // X leicht verschoben für Mitte Plattform

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            for (int i = 0; i < getWidth() / bgImage.getWidth() + 2; i++) {
                g.drawImage(bgImage, i * bgImage.getWidth() - bgXOffset, 0, null);
            }
        }

        for (Platform p : platforms) {
            p.draw(g);
        }

        player.draw(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over", 350, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Plattformen nach unten bewegen und entfernen
        Iterator<Platform> iter = platforms.iterator();
        while (iter.hasNext()) {
            Platform p = iter.next();
            p.moveDown();
            if (p.y > 600) {
                iter.remove();
            }
        }

        // Neue Plattform hinzufügen, wenn weniger als 10
        if (platforms.size() < 10) {
            int x = (int)(Math.random() * 600 + 50);
            int y = -10; // Start oben außerhalb des Sichtfelds
            platforms.add(new Platform(x, y, 120, 10));
        }

        player.update(platforms);
        bgXOffset = (player.getX() / 4) % (bgImage != null ? bgImage.getWidth() : 800);

        if (player.getY() > 600) {
            gameOver = true;
            timer.stop();
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}