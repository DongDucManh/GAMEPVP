package entities;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * Attack - đại diện cho một viên đạn trong game
 * Quản lý vị trí, di chuyển và vẽ đạn
 */
public class Attack {
    private int speed = 10;           // Tốc độ di chuyển đạn
    private int x, y;                 // Vị trí đạn
    private int width = 10, height = 5; // Kích thước đạn
    private boolean active = true;    // Trạng thái hoạt động
    private int direction;            // Hướng di chuyển
    private Color color;              // Màu sắc (theo người chơi)
    
    /**
     * Khởi tạo đạn
     * @param x Tọa độ X ban đầu
     * @param y Tọa độ Y ban đầu
     * @param direction Hướng di chuyển (0:lên, 1:xuống, 2:trái, 3:phải)
     * @param color Màu sắc đạn
     */
    public Attack(int x, int y, int direction, Color color) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.color = color;
    }
    
    /**
     * Cập nhật vị trí đạn
     */
    public void update() {
        if (!active) return;  // Không cập nhật nếu đạn không còn hoạt động
        
        // Di chuyển đạn theo hướng
        switch (direction) {
            case 0: // Lên
                y -= speed;
                break;
            case 1: // Xuống
                y += speed;
                break;
            case 2: // Trái
                x -= speed;
                break;
            case 3: // Phải
                x += speed;
                break;
        }
        
        // Kiểm tra nếu đạn đi ra ngoài màn hình
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            active = false;  // Đánh dấu không hoạt động
        }
    }
    
    /**
     * Vẽ đạn
     * @param g Đối tượng đồ họa để vẽ
     */
    public void draw(Graphics g) {
        if (!active) return;  // Không vẽ nếu đạn không còn hoạt động
        
        g.setColor(color);
        
        // Vẽ hình dạng đạn khác nhau tùy theo hướng
        if (direction == 0 || direction == 1) { // Đạn dọc (lên/xuống)
            g.fillRect(x, y, 5, 10);
        } else { // Đạn ngang (trái/phải)
            g.fillRect(x, y, 10, 5);
        }
    }
    
    /**
     * Kiểm tra trạng thái hoạt động của đạn
     * @return true nếu đạn đang hoạt động
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * Đánh dấu đạn không còn hoạt động
     */
    public void deactivate() {
        active = false;
    }
    
    /**
     * Lấy khung va chạm của đạn
     * @return Hình chữ nhật đại diện cho vùng va chạm
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
