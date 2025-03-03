package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import core.GamePanel;

/**
 * KeyBoardsHandle - xử lý đầu vào từ bàn phím
 * Theo dõi các phím được nhấn và cập nhật hướng di chuyển
 */
public class KeyBoardsHandle implements KeyListener {
    private GamePanel gamePanel;      // Tham chiếu đến game panel
    private int directionP1;          // Hướng di chuyển của người chơi 1
    private int directionP2;          // Hướng di chuyển của người chơi 2
    
    // Theo dõi trạng thái của các phím được nhấn
    private boolean upP1, downP1, leftP1, rightP1, actionP1, shootP1;
    private boolean upP2, downP2, leftP2, rightP2, shootP2;
    
    // Điều khiển khóa hướng
    private boolean shiftP1 = false;  // Shift trái cho P1
    private boolean shiftP2 = false;  // Ctrl cho P2
    
    /**
     * Khởi tạo trình xử lý phím
     * @param gamePanel Tham chiếu đến game panel
     */
    public KeyBoardsHandle(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        // Khởi tạo không có hướng di chuyển
        directionP1 = -1;
        directionP2 = -1;
    }

    /**
     * Xử lý khi phím được gõ (không sử dụng)
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Không cần triển khai
    }
    
    /**
     * Lấy hướng di chuyển của người chơi 1
     * @return Hướng di chuyển của người chơi 1
     */
    public int getDirectionP1(){
        return directionP1;
    }
    
    /**
     * Lấy hướng di chuyển của người chơi 2
     * @return Hướng di chuyển của người chơi 2
     */
    public int getDirectionP2(){
        return directionP2;
    }
    
    /**
     * Kiểm tra xem người chơi 1 có đang bắn không
     * @return true nếu người chơi 1 đang bắn, ngược lại false
     */
    public boolean isShootingP1() {
        return shootP1;
    }

    /**
     * Kiểm tra xem người chơi 2 có đang bắn không
     * @return true nếu người chơi 2 đang bắn, ngược lại false
     */
    public boolean isShootingP2() {
        return shootP2;
    }
    
    /**
     * Kiểm tra xem hướng di chuyển của người chơi 1 có bị khóa không
     * @return true nếu hướng di chuyển của người chơi 1 bị khóa, ngược lại false
     */
    public boolean isDirectionLockedP1() {
        return shiftP1;
    }
    
    /**
     * Kiểm tra xem hướng di chuyển của người chơi 2 có bị khóa không
     * @return true nếu hướng di chuyển của người chơi 2 bị khóa, ngược lại false
     */
    public boolean isDirectionLockedP2() {
        return shiftP2;
    }
    
    /**
     * Cập nhật hướng di chuyển của người chơi
     */
    public void updateDirections() {
        // Logic hướng di chuyển người chơi 1 (ưu tiên phím được nhấn cuối cùng)
        if (upP1) directionP1 = 0;
        if (downP1) directionP1 = 1;
        if (leftP1) directionP1 = 2;
        if (rightP1) directionP1 = 3;
        if (actionP1) directionP1 = 5;
        if (!upP1 && !downP1 && !leftP1 && !rightP1 && !actionP1) directionP1 = -1;
        
        // Logic hướng di chuyển người chơi 2
        if (upP2) directionP2 = 0;
        if (downP2) directionP2 = 1;
        if (leftP2) directionP2 = 2;
        if (rightP2) directionP2 = 3;
        if (!upP2 && !downP2 && !leftP2 && !rightP2) directionP2 = -1;
    }
    
    /**
     * Xử lý khi phím được nhấn
     * @param e Sự kiện phím được nhấn
     */
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
            case KeyEvent.VK_SPACE:
                shootP1 = true;
                break;
            case KeyEvent.VK_SHIFT:
                shiftP1 = true; // Player 1 direction lock
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
            case KeyEvent.VK_NUMPAD0:
                shootP2 = true;
                break;
            case KeyEvent.VK_CONTROL:
                shiftP2 = true; // Player 2 direction lock
                break;

        }
        
        updateDirections();
    }

    /**
     * Xử lý khi phím được nhả
     * @param e Sự kiện phím được nhả
     */
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
            case KeyEvent.VK_SPACE:
                shootP1 = false;
                break;
            case KeyEvent.VK_SHIFT:
                shiftP1 = false;
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
            case KeyEvent.VK_NUMPAD0:
                shootP2 = false;
                break;
            case KeyEvent.VK_CONTROL:
                shiftP2 = false;
                break;
        }
        
        updateDirections();
    }
}
