package jumpman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.imageio.ImageIO;

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

        // TODO: Initiale Plattform direkt unter dem Spieler erstellen
        // platforms.add(new Platform(...));

        // TODO: Weitere Plattformen zufällig generieren
        // for (int i = 1; i < 10; i++) { ... }

        // TODO: Spieler initialisieren (positioniert auf erster Plattform)
        // player = new Player(...);

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

    // === actionPerformed-Methode ===
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Plattformen nach unten bewegen und entfernen
        // Nutze eine Schleife über die Plattformen und entferne sie, wenn sie unter dem Bildschirm sind
        // Beispiel: if (p.y > 600) { remove... }

        // TODO: Wenn weniger als 10 Plattformen existieren, füge eine neue hinzu
        // Hinweis: x = zufällige Position, y = -10 (Start oberhalb des Bildschirms)

        // TODO: Rufe player.update(platforms) auf, um Kollisionen zu prüfen

        // TODO: Prüfe, ob der Spieler aus dem Bildschirm gefallen ist (y > 600)
        // Falls ja → gameOver = true und timer.stop()

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