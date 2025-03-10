package entities;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import core.GameConstants;
import graphics.Sprite;

/**
 * Attack - đại diện cho một viên đạn trong game
 * Quản lý vị trí, di chuyển và vẽ đạn
 */
public class Bullet {
    private int speed = 20;           // Tốc độ di chuyển đạn
    private int x, y;                 // Vị trí 
    public static final int DAME = 20;
    private int width = 8, height = 8; // Kích thước đạn
    private boolean active = true;    // Trạng thái hoạt động
    private double angle;             // Góc di chuyển (độ)
    private Color color;              // Màu sắc (theo người chơi)
    private Sprite sprite;            // Tham chiếu đến đối tượng Sprite
    private BufferedImage bulletImage; // Hình ảnh đạn đã xoay theo hướng
    
    /**
     * Khởi tạo đạn với góc cụ thể
     * @param x Tọa độ X ban đầu
     * @param y Tọa độ Y ban đầu
     * @param angle Góc di chuyển (độ)
     * @param color Màu sắc đạn
     * @param sprite Đối tượng Sprite để lấy hình ảnh
     */
    public Bullet(int x, int y, double angle, Color color, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.color = color;
        this.sprite = sprite;
        
        // Xoay hình ảnh đạn theo góc
        this.bulletImage = sprite.rotateImageByAngle(sprite.getBullet(), angle);
    }
    
    /**
     * Cập nhật vị trí đạn
     */
    public void update() {
        if (!active) return;  // Không cập nhật nếu đạn không còn hoạt động
        
        // Tính toán di chuyển dựa trên góc
        double radians = Math.toRadians(angle);
        x += (int)(Math.cos(radians) * speed);
        y += (int)(Math.sin(radians) * speed);
        
        // Kiểm tra nếu đạn đi ra ngoài màn hình
        if (x < 0 || x > GameConstants.GAME_SCREEN_WIDTH || y < 0 || y > GameConstants.GAME_SCREEN_HEIGHT) {
            active = false;  // Đánh dấu không hoạt động
        }
    }
    
    /**
     * Vẽ đạn
     * @param g Đối tượng đồ họa để vẽ
     */
    public void draw(Graphics g) {
        if (!active) return;  // Không vẽ nếu đạn không còn hoạt động
        
        // Vẽ đạn bằng hình ảnh sprite
        if (bulletImage != null) {
            g.drawImage(bulletImage, x, y, width, height, null);
        } else {
            // Vẽ đạn đơn giản nếu không có hình ảnh
            g.setColor(color);
            if (angle == 270 || angle == 90) { // Đạn dọc (lên/xuống)
                g.fillRect(x, y, 5, 10);
            } else { // Đạn ngang (trái/phải)
                g.fillRect(x, y, 10, 5);
            }
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
