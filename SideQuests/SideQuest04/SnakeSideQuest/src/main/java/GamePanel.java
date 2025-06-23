
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;

    int delay = 150;

    public int x = SCREEN_WIDTH / 2;
    public int y = SCREEN_HEIGHT / 2;
    public int dx = 0;
    public int dy = -UNIT_SIZE;

    public boolean running = true;

    ArrayList<Point> body = new ArrayList<>();
    Point food;
    int score = 0;

    Timer timer;
    Random random = new Random();

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter(this));

        startGame();
    }

    public void startGame() {
        x = SCREEN_WIDTH / 2;
        y = SCREEN_HEIGHT / 2;
        dx = 0;
        dy = -UNIT_SIZE;
        delay = 150;
        score = 0;
        body.clear();
        body.add(new Point(x, y));
        spawnFood();
        running = true;

        if (timer != null) timer.stop();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void spawnFood() {
        int foodX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int foodY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        food = new Point(foodX, foodY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }

    public void move() {
        // TODO: Schlange bewegen
//        x += dx;
//        y += dy;
//        body.add(0, new Point(x, y));
//
//        while (body.size() > score + 1) {
//            body.remove(body.size() - 1);
//        }
    }

    public void checkFood() {
        // TODO: Wenn Kopf == Essen → score erhöhen, neues Essen erzeugen, Geschwindigkeit erhöhen
//        if (x == food.x && y == food.y) {
//            score++;
//            spawnFood();
//
//            if (delay > 30) {
//                delay -= 5;
//                timer.setDelay(delay);
//            }
//        }
    }

    public void checkCollision() {
        // TODO: Rand- und Selbstkollision
//        if (x < 0 || x >= SCREEN_WIDTH || y < 0 || y >= SCREEN_HEIGHT) {
//            running = false;
//        }
//
//        for (int i = 1; i < body.size(); i++) {
//            if (x == body.get(i).x && y == body.get(i).y) {
//                running = false;
//                break;
//            }
//        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // TODO: Essen zeichnen
            g.setColor(Color.red);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            // TODO: Schlange zeichnen
            g.setColor(Color.green);
            for (Point p : body) {
                g.fillRect(p.x, p.y, UNIT_SIZE, UNIT_SIZE);
            }

            // Punktestand
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Punkte: " + score, 10, 20);

        } else {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", SCREEN_WIDTH / 2 - 120, SCREEN_HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Punkte: " + score, SCREEN_WIDTH / 2 - 40, SCREEN_HEIGHT / 2 + 40);
            g.setFont(new Font("Arial", Font.ITALIC, 16));
            g.drawString("Drücke 'R' zum Neustart", SCREEN_WIDTH / 2 - 80, SCREEN_HEIGHT / 2 + 70);
        }
    }
}
