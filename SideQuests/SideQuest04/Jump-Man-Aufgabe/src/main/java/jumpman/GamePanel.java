package jumpman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private BufferedImage bgImage;
    private int bgXOffset = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);

        player = new Player(100, 500);

        try {
            bgImage = ImageIO.read(getClass().getResource("/res/background.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Hintergrundbild nicht gefunden oder konnte nicht geladen werden.");
        }

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

        player.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        bgXOffset = (player.getX() / 4) % (bgImage != null ? bgImage.getWidth() : 800);
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
