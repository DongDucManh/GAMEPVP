package inputs;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import core.GamePanel;

public class KeyBoardsHandle implements KeyListener {
    private GamePanel gamePanel;
    private int PLAYER;

    public KeyBoardsHandle(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setBoard(int PLAYER) {
        this.PLAYER = PLAYER;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (PLAYER == 1) {
            if (e.getKeyCode() == KeyEvent.VK_W) {

            }
            if (e.getKeyCode() == KeyEvent.VK_S) {

            }
        } else if (PLAYER == 2) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
