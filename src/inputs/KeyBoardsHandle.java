package inputs;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import core.GamePanel;

public class KeyBoardsHandle implements KeyListener {
    private GamePanel gamePanel;
    private int directionP1;
    private int directionP2;
    
    // Keep track of which keys are currently pressed
    private boolean upP1, downP1, leftP1, rightP1, actionP1;
    private boolean upP2, downP2, leftP2, rightP2;
    
    public KeyBoardsHandle(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        directionP1 = -1;
        directionP2 = -1;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No implementation needed
    }
    
    public int getDirectionP1(){
        return directionP1;
    }
    
    public int getDirectionP2(){
        return directionP2;
    }
    
    // Update directions based on currently pressed keys
    public void updateDirections() {
        // Player 1 direction logic with priority (last pressed key takes precedence)
        if (upP1) directionP1 = 0;
        if (downP1) directionP1 = 1;
        if (leftP1) directionP1 = 2;
        if (rightP1) directionP1 = 3;
        if (actionP1) directionP1 = 5;
        if (!upP1 && !downP1 && !leftP1 && !rightP1 && !actionP1) directionP1 = -1;
        
        // Player 2 direction logic
        if (upP2) directionP2 = 0;
        if (downP2) directionP2 = 1;
        if (leftP2) directionP2 = 2;
        if (rightP2) directionP2 = 3;
        if (!upP2 && !downP2 && !leftP2 && !rightP2) directionP2 = -1;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Player 1 controls (WASD)
            case KeyEvent.VK_W:
                upP1 = true;
                break;
            case KeyEvent.VK_S:
                downP1 = true;
                break;
            case KeyEvent.VK_A:
                leftP1 = true;
                break;
            case KeyEvent.VK_D:
                rightP1 = true;
                break;
            case KeyEvent.VK_K:
                actionP1 = true;
                break;
                
            // Player 2 controls (Arrow keys)
            case KeyEvent.VK_UP:
                upP2 = true;
                break;
            case KeyEvent.VK_DOWN:
                downP2 = true;
                break;
            case KeyEvent.VK_LEFT:
                leftP2 = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightP2 = true;
                break;
        }
        
        updateDirections();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Player 1
            case KeyEvent.VK_W:
                upP1 = false;
                break;
            case KeyEvent.VK_S:
                downP1 = false;
                break;
            case KeyEvent.VK_A:
                leftP1 = false;
                break;
            case KeyEvent.VK_D:
                rightP1 = false;
                break;
            case KeyEvent.VK_K:
                actionP1 = false;
                break;
                
            // Player 2
            case KeyEvent.VK_UP:
                upP2 = false;
                break;
            case KeyEvent.VK_DOWN:
                downP2 = false;
                break;
            case KeyEvent.VK_LEFT:
                leftP2 = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightP2 = false;
                break;
        }
        
        updateDirections();
    }
}
