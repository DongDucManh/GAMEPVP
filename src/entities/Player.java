package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

/**
 * Player - đại diện cho một người chơi trong game
 * Quản lý vị trí, di chuyển, bắn đạn và vẽ người chơi
 */
public class Player {
    private int x, y;                      // Vị trí của người chơi
    private int speed;                     // Tốc độ di chuyển
    private int size;                      // Kích thước hình vuông người chơi
    private int moveDirection = -1;        // Hướng di chuyển (-1: không di chuyển)
    private int facingDirection = 3;       // Hướng nhìn (mặc định: phải)
    private Color color;                   // Màu sắc người chơi
    private String label;                  // Nhãn hiển thị (P1/P2)
    private List<Attack> bullets;          // Danh sách đạn đã bắn
    private long lastShootTime;            // Thời gian bắn đạn cuối cùng
    private static final long SHOOT_COOLDOWN = 500; // Thời gian hồi chiêu (ms)

    /**
     * Khởi tạo người chơi
     * @param x Tọa độ X ban đầu
     * @param y Tọa độ Y ban đầu
     * @param speed Tốc độ di chuyển
     * @param size Kích thước người chơi
     * @param color Màu sắc
     * @param label Nhãn hiển thị (P1/P2)
     */
    public Player(int x, int y, int speed, int size, Color color, String label) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = color;
        this.label = label;
        this.bullets = new ArrayList<>();
        this.lastShootTime = 0;
    }

    /**
     * Đặt hướng di chuyển hiện tại
     * @param direction Hướng di chuyển (0:lên, 1:xuống, 2:trái, 3:phải, -1:không di chuyển)
     */
    public void setMoveDirection(int direction) {
        this.moveDirection = direction;
    }
    
    /**
     * Cập nhật hướng nhìn dựa trên hướng di chuyển
     * @param lockDirection True nếu hướng nhìn bị khóa (không thay đổi)
     */
    public void updateFacingDirection(boolean lockDirection) {
        // Chỉ cập nhật hướng nhìn nếu không bị khóa và đang di chuyển
        if (!lockDirection && moveDirection != -1) {
            facingDirection = moveDirection;
        }
    }
    
    /**
     * Lấy hướng nhìn hiện tại
     * @return Hướng nhìn (0:lên, 1:xuống, 2:trái, 3:phải)
     */
    public int getFacingDirection() {
        return facingDirection;
    }

    /**
     * Di chuyển người chơi dựa trên hướng và tốc độ hiện tại
     */
    public void move() {
        // Chỉ di chuyển nếu có hướng di chuyển
        if (moveDirection == -1)
            return;

        // Tăng tốc độ di chuyển để mượt hơn
        int actualSpeed = speed * 3;

        // Di chuyển theo hướng
        switch (moveDirection) {
            case 0: // Lên
                y -= actualSpeed;
                break;
            case 1: // Xuống
                if (y + actualSpeed > 600) {
                    y += (y + actualSpeed) - 600;
                } else {
                    y += actualSpeed;
                }
                break;
            case 2: // Trái
                x -= actualSpeed;
                break;
            case 3: // Phải
                if (x + actualSpeed > 800) {
                    x += (x + actualSpeed) - 800;
                } else {
                    x += actualSpeed;
                }
                break;
        }

        // Kiểm tra và giới hạn trong màn hình
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x > 800 - size)
            x = 800 - size - size / 2;
        if (y > 600 - size)
            y = 600 - size - size;
    }

    /**
     * Bắn đạn theo hướng nhìn hiện tại
     */
    public void shoot() {
        long currentTime = System.currentTimeMillis();
        
        // Kiểm tra thời gian hồi chiêu
        if (currentTime - lastShootTime < SHOOT_COOLDOWN) {
            return;
        }
        
        // Tạo đạn mới dựa trên hướng nhìn (không phải hướng di chuyển)
        int bulletX = x;
        int bulletY = y;
        
        // Điều chỉnh vị trí bắt đầu của đạn dựa trên hướng nhìn của người chơi
        switch (facingDirection) {
            case 0: // Lên
                bulletX += size / 2 - 2;
                break;
            case 1: // Xuống
                bulletX += size / 2 - 2;
                bulletY += size;
                break;
            case 2: // Trái
                bulletY += size / 2 - 2;
                break;
            case 3: // Phải
                bulletX += size;
                bulletY += size / 2 - 2;
                break;
            default:
                // Nếu không có hướng, mặc định bắn sang phải
                bulletX += size;
                bulletY += size / 2 - 2;
                facingDirection = 3;
                break;
        }
        
        // Tạo đạn và đặt thời gian hồi chiêu
        bullets.add(new Attack(bulletX, bulletY, facingDirection, color));
        lastShootTime = currentTime;
    }
    
    /**
     * Cập nhật trạng thái tất cả đạn
     */
    public void updateBullets() {
        // Cập nhật tất cả đạn đang hoạt động
        for (int i = 0; i < bullets.size(); i++) {
            Attack bullet = bullets.get(i);
            bullet.update();
            
            // Xóa đạn không còn hoạt động
            if (!bullet.isActive()) {
                bullets.remove(i);
                i--;
            }
        }
    }
    
    /**
     * Vẽ tất cả đạn
     * @param g Đối tượng đồ họa để vẽ
     */
    public void drawBullets(Graphics g) {
        for (Attack bullet : bullets) {
            bullet.draw(g);
        }
    }
    
    /**
     * Lấy danh sách đạn
     * @return Danh sách đạn đang hoạt động
     */
    public List<Attack> getBullets() {
        return bullets;
    }

    /**
     * Vẽ người chơi và chỉ báo hướng nhìn
     * @param g Đối tượng đồ họa để vẽ
     */
    public void draw(Graphics g) {
        // Vẽ hình vuông người chơi
        g.setColor(color);
        g.fillRect(x, y, size, size);
        
        // Vẽ chỉ báo hướng nhìn
        g.setColor(Color.YELLOW);
        
        // Vẽ chỉ báo tam giác thể hiện hướng nhìn của người chơi
        Polygon indicator = new Polygon();
        switch (facingDirection) {
            case 0: // Lên
                indicator.addPoint(x + size/2, y - 5);
                indicator.addPoint(x + size/2 - 5, y);
                indicator.addPoint(x + size/2 + 5, y);
                break;
            case 1: // Xuống
                indicator.addPoint(x + size/2, y + size + 5);
                indicator.addPoint(x + size/2 - 5, y + size);
                indicator.addPoint(x + size/2 + 5, y + size);
                break;
            case 2: // Trái
                indicator.addPoint(x - 5, y + size/2);
                indicator.addPoint(x, y + size/2 - 5);
                indicator.addPoint(x, y + size/2 + 5);
                break;
            case 3: // Phải
                indicator.addPoint(x + size + 5, y + size/2);
                indicator.addPoint(x + size, y + size/2 - 5);
                indicator.addPoint(x + size, y + size/2 + 5);
                break;
        }
        g.fillPolygon(indicator);

        // Vẽ nhãn người chơi phía trên
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(label, x + (size / 2) - 10, y - 10);
    }
    
    /**
     * Lấy tọa độ X
     */
    public int getX() {
        return x;
    }
    
    /**
     * Lấy tọa độ Y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Lấy kích thước
     */
    public int getSize() {
        return size;
    }
}
