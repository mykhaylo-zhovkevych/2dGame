package jumpman;

import javax.swing.*;

public class GameWindow {
    public GameWindow() {
        JFrame frame = new JFrame("JumpMan Prototype");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}