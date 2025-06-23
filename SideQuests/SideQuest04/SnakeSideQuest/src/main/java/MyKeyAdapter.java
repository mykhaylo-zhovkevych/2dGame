import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {

    private final GamePanel game;

    public MyKeyAdapter(GamePanel game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.running) {
            // TODO: Bewegung steuern mit W A S D
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_W:
//                    if (game.dy == 0) {
//                        game.dx = 0;
//                        game.dy = -game.UNIT_SIZE;
//                    }
//                    break;
//                case KeyEvent.VK_S:
//                    if (game.dy == 0) {
//                        game.dx = 0;
//                        game.dy = game.UNIT_SIZE;
//                    }
//                    break;
//                case KeyEvent.VK_A:
//                    if (game.dx == 0) {
//                        game.dx = -game.UNIT_SIZE;
//                        game.dy = 0;
//                    }
//                    break;
//                case KeyEvent.VK_D:
//                    if (game.dx == 0) {
//                        game.dx = game.UNIT_SIZE;
//                        game.dy = 0;
//                    }
//                    break;
//            }
//        } else {
//            // Neustart mit R
//            if (e.getKeyCode() == KeyEvent.VK_R) {
//                game.startGame();
//            }
//        }
    }
}
