package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.GamePanel;

/**
 * KeyBoardsHandle - xử lý đầu vào từ bàn phím
 * Theo dõi các phím được nhấn và cập nhật hướng di chuyển
 */
public class KeyBoardsHandle implements KeyListener {
    private GamePanel gamePanel; // Tham chiếu đến game panel
    private int directionP1; // Hướng di chuyển của người chơi 1
    private int directionP2; // Hướng di chuyển của người chơi 2
    private int rotationP1; // Hướng xoay của người chơi 1
    private int rotationP2; // Hướng xoay của người chơi 2

    // Theo dõi trạng thái của các phím được nhấn
    private boolean upP1, downP1, leftP1, rightP1, actionP1, shootP1;
    private boolean upP2, downP2, leftP2, rightP2, shootP2;

    // Điều khiển khóa hướng
    private boolean shiftP1 = false; // Shift trái cho P1
    private boolean shiftP2 = false; // Ctrl cho P2
    private boolean revice = false;

    /**
     * Khởi tạo trình xử lý phím
     * 
     * @param gamePanel Tham chiếu đến game panel
     */
    public KeyBoardsHandle(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        // Khởi tạo không có hướng di chuyển
        directionP1 = -1;
        directionP2 = -1;
        rotationP1 = -1;
        rotationP2 = -1;
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
     * 
     * @return Hướng di chuyển của người chơi 1
     */
    public int getDirectionP1() {
        return directionP1;
    }

    /**
     * Lấy hướng di chuyển của người chơi 2
     * 
     * @return Hướng di chuyển của người chơi 2
     */
    public int getDirectionP2() {
        return directionP2;
    }

    /**
     * Lấy hướng xoay của người chơi 1
     * 
     * @return Hướng xoay của người chơi 1
     */
    public int getRotationP1() {
        return rotationP1;
    }

    /**
     * Lấy hướng xoay của người chơi 2
     * 
     * @return Hướng xoay của người chơi 2
     */
    public int getRotationP2() {
        return rotationP2;
    }

    /**
     * Kiểm tra xem người chơi 1 có đang bắn không
     * 
     * @return true nếu người chơi 1 đang bắn, ngược lại false
     */
    public boolean isShootingP1() {
        return shootP1;
    }

    /**
     * Kiểm tra xem người chơi 2 có đang bắn không
     * 
     * @return true nếu người chơi 2 đang bắn, ngược lại false
     */
    public boolean isShootingP2() {
        return shootP2;
    }

    /**
     * Kiểm tra xem hướng di chuyển của người chơi 1 có bị khóa không
     * 
     * @return true nếu hướng di chuyển của người chơi 1 bị khóa, ngược lại false
     */
    public boolean isDirectionLockedP1() {
        return shiftP1;
    }

    /**
     * Kiểm tra xem hướng di chuyển của người chơi 2 có bị khóa không
     * 
     * @return true nếu hướng di chuyển của người chơi 2 bị khóa, ngược lại false
     */
    public boolean isDirectionLockedP2() {
        return shiftP2;
    }

    /**
     * Cập nhật hướng di chuyển và xoay của người chơi
     */
    public void updateDirections() {
        // Người chơi 1: W/S để tiến/lùi, A/D để xoay trái/phải
        if (upP1)
            directionP1 = 0;  // Tiến
        else if (downP1)
            directionP1 = 1;  // Lùi
        else
            directionP1 = -1; // Không di chuyển

        if (leftP1)
            rotationP1 = 0;   // Xoay trái
        else if (rightP1)
            rotationP1 = 1;   // Xoay phải
        else
            rotationP1 = -1;  // Không xoay
            
        if (actionP1)
            directionP1 = 5;  // Hành động đặc biệt
            
        // Người chơi 2: Up/Down để tiến/lùi, Left/Right để xoay trái/phải
        if (upP2)
            directionP2 = 0;  // Tiến
        else if (downP2)
            directionP2 = 1;  // Lùi
        else
            directionP2 = -1; // Không di chuyển

        if (leftP2)
            rotationP2 = 0;   // Xoay trái
        else if (rightP2)
            rotationP2 = 1;   // Xoay phải
        else
            rotationP2 = -1;  // Không xoay
            
        if (revice)
            directionP2 = 10; // Hồi máu
    }

    /**
     * Xử lý khi phím được nhấn
     * 
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
            case KeyEvent.VK_NUMPAD1:
                revice = true;
                break;

        }

        updateDirections();
    }

    /**
     * Xử lý khi phím được nhả
     * 
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
            case KeyEvent.VK_NUMPAD1:
                revice = false;
                break;
        }

        updateDirections();
    }
}
