package core;

import javax.swing.JPanel;

import entities.Player;
import entities.Attack;
import inputs.KeyBoardsHandle;

import java.awt.*;
import java.util.ArrayList;

/**
 * GamePanel - lớp chính xử lý logic game và render
 * Kế thừa JPanel để vẽ các thành phần đồ họa
 */
public class GamePanel extends JPanel {
    private Player player_1, player_2;     // Hai người chơi trong game
    private KeyBoardsHandle keyBoardsHandle;  // Xử lý đầu vào từ bàn phím
    
    /**
     * Khởi tạo panel game và các thành phần cần thiết
     */
    public GamePanel() {
        // Tạo người chơi với các thông số và nhãn
        player_1 = new Player(100, 100, 1, 30, Color.BLUE, "P1");
        player_2 = new Player(600, 400, 1, 30, Color.RED, "P2");
        
        // Thiết lập xử lý đầu vào
        keyBoardsHandle = new KeyBoardsHandle(this);
        addKeyListener(keyBoardsHandle);
        setBackground(Color.DARK_GRAY);
        
        // Đảm bảo panel có thể nhận focus để xử lý phím
        setFocusable(true);
        requestFocus();
    }

    /**
     * Cập nhật trạng thái game mỗi chu kỳ
     * Được gọi từ vòng lặp game chính
     */
    public void updateGame() {
        // Xử lý đầu vào và cập nhật trạng thái
        
        // Cập nhật di chuyển người chơi
        setMoving(1);
        setMoving(2);
        
        // Xử lý bắn đạn - hoạt động độc lập với di chuyển
        handleShooting();
        
        // Cập nhật vị trí đạn
        player_1.updateBullets();
        player_2.updateBullets();
        
        // Kiểm tra va chạm
        checkCollisions();
    }
    
    /**
     * Xử lý logic bắn đạn cho cả hai người chơi
     */
    private void handleShooting() {
        // Người chơi 1 bắn
        if (keyBoardsHandle.isShootingP1()) {
            player_1.shoot();
        }
        
        // Người chơi 2 bắn
        if (keyBoardsHandle.isShootingP2()) {
            player_2.shoot();
        }
    }
    
    /**
     * Kiểm tra va chạm giữa đạn và người chơi
     */
    private void checkCollisions() {
        // Kiểm tra đạn của người chơi 1 có trúng người chơi 2 không
        for (Attack bullet : new ArrayList<>(player_1.getBullets())) {
            Rectangle bulletBound = bullet.getBounds();
            Rectangle player2Bound = new Rectangle(
                player_2.getX(), player_2.getY(), 
                player_2.getSize(), player_2.getSize()
            );
            
            if (bulletBound.intersects(player2Bound)) {
                bullet.deactivate();
                // Xử lý logic khi trúng đạn (ví dụ: giảm máu, tính điểm)
                System.out.println("P1 hit P2!");
            }
        }
        
        // Kiểm tra đạn của người chơi 2 có trúng người chơi 1 không
        for (Attack bullet : new ArrayList<>(player_2.getBullets())) {
            Rectangle bulletBound = bullet.getBounds();
            Rectangle player1Bound = new Rectangle(
                player_1.getX(), player_1.getY(), 
                player_1.getSize(), player_1.getSize()
            );
            
            if (bulletBound.intersects(player1Bound)) {
                bullet.deactivate();
                // Xử lý logic khi trúng đạn
                System.out.println("P2 hit P1!");
            }
        }
    }
    
    /**
     * Cập nhật di chuyển cho người chơi
     * @param p Số người chơi (1 hoặc 2)
     */
    public void setMoving(int p) {
        if (p == 1) {
            // Cập nhật hướng di chuyển
            player_1.setMoveDirection(keyBoardsHandle.getDirectionP1());
            // Chỉ cập nhật hướng nhìn nếu không bị khóa
            player_1.updateFacingDirection(keyBoardsHandle.isDirectionLockedP1());
            // Di chuyển người chơi
            player_1.move();
        }
        if (p == 2) {
            // Cập nhật hướng di chuyển
            player_2.setMoveDirection(keyBoardsHandle.getDirectionP2());
            // Chỉ cập nhật hướng nhìn nếu không bị khóa
            player_2.updateFacingDirection(keyBoardsHandle.isDirectionLockedP2());
            // Di chuyển người chơi
            player_2.move();
        }
    }
    
    /**
     * Vẽ tất cả các thành phần game
     * Được gọi tự động bởi Swing khi repaint()
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Vẽ người chơi
        player_1.draw(g);
        player_2.draw(g);
        
        // Vẽ đạn
        player_1.drawBullets(g);
        player_2.drawBullets(g);
        
        // Vẽ hướng dẫn
        drawInstructions(g);
    }
    
    /**
     * Vẽ hướng dẫn điều khiển lên màn hình
     */
    private void drawInstructions(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("P1: WASD để di chuyển, SPACE để bắn (có thể đồng thời), SHIFT để khóa hướng", 20, 20);
        g.drawString("P2: Phím mũi tên để di chuyển, Numpad 0 để bắn (có thể đồng thời), CTRL để khóa hướng", 20, 40);
    }
}
