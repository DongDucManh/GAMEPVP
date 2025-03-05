package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import core.GameConstants;
import graphics.Sprite;

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
    // private String label;                  // Nhãn hiển thị (P1/P2)
    private List<Bullet> bullets;          // Danh sách đạn đã bắn
    private long lastShootTime;            // Thời gian bắn đạn cuối cùng
    private static final long SHOOT_COOLDOWN = 500; // Thời gian hồi chiêu (ms)
    private boolean isBlue; // Xác định màu xe tăng (true: xanh, false: đỏ)
    private Sprite sprite;  // Tham chiếu đến đối tượng Sprite
    private int previousX;
    private int previousY;
    private Rectangle hitBox;
    /**
     * Khởi tạo người chơi
     * @param x Tọa độ X ban đầu
     * @param y Tọa độ Y ban đầu
     * @param speed Tốc độ di chuyển
     * @param size Kích thước người chơi
     * @param color Màu sắc
     * @param label Nhãn hiển thị (P1/P2)
     * @param sprite Đối tượng Sprite để lấy hình ảnh
     */
    public Player(int x, int y, int speed, int size, Color color, String label, Sprite sprite) {
        this.x = x;
        this.y = y;

        this.speed = speed;
        this.size = size;
        this.color = color;
        //this.label = label;
        this.bullets = new ArrayList<>();
        this.lastShootTime = 0;
        this.sprite = sprite;
        
        // Xác định xe tăng là màu xanh hay đỏ dựa theo label
        this.isBlue = label.equals("P1");

        this.hitBox = new Rectangle(x, y, size, size);
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
        if (moveDirection == -1) return;

        previousX = x;
        previousY = y;

        // Tăng tốc độ di chuyển để mượt hơn
        int actualSpeed = speed;

        // Di chuyển theo hướng
        switch (moveDirection) {
            case 0: // Lên
                y -= actualSpeed;
                break;
            case 1: // Xuống
                if (y + actualSpeed > GameConstants.GAME_SCREEN_HEIGHT) {
                    y += (y + actualSpeed) - GameConstants.GAME_SCREEN_HEIGHT;
                } else {
                    y += actualSpeed;
                }
                break;
            case 2: // Trái
                x -= actualSpeed;
                break;
            case 3: // Phải
                if (x + actualSpeed > GameConstants.GAME_SCREEN_WIDTH) {
                    x += (x + actualSpeed) - GameConstants.GAME_SCREEN_WIDTH;
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
        if (x > GameConstants.GAME_SCREEN_WIDTH - size - size/2 + 4)
            x = GameConstants.GAME_SCREEN_WIDTH - size - size/2 + 4;
        if (y > GameConstants.GAME_SCREEN_HEIGHT - size - size -1)
            y = GameConstants.GAME_SCREEN_HEIGHT - size - size -1;

    }

    public void undoMove() {
        this.x = this.previousX;
        this.y = this.previousY;
        this.hitBox.setLocation(previousX, previousY);
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
                bulletX += size / 2 - 8;
                bulletY -= 16;
                break;
            case 1: // Xuống
                bulletX += size / 2 - 8;
                bulletY += size;
                break;
            case 2: // Trái
                bulletX -= 16;
                bulletY += size / 2 - 8;
                break;
            case 3: // Phải
                bulletX += size;
                bulletY += size / 2 - 8;
                break;
            default:
                // Nếu không có hướng, mặc định bắn sang phải
                bulletX += size;
                bulletY += size / 2 - 8;
                facingDirection = 3;
                break;
        }
        
        // Tạo đạn và đặt thời gian hồi chiêu
        bullets.add(new Bullet(bulletX, bulletY, facingDirection, color, sprite));
        lastShootTime = currentTime;
    }
    
    /**
     * Cập nhật trạng thái tất cả đạn
     */
    public void updateBullets() {
        // Cập nhật tất cả đạn đang hoạt động
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
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
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }
    
    /**
     * Lấy danh sách đạn
     * @return Danh sách đạn đang hoạt động
     */
    public List<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Vẽ người chơi và chỉ báo hướng nhìn
     * @param g Đối tượng đồ họa để vẽ
     */
    public void draw(Graphics g) {
        if (sprite != null) {
            // Lấy hình ảnh xe tăng và xoay theo hướng nhìn
            BufferedImage tankImg = sprite.getTank(isBlue);
            BufferedImage rotatedTank = sprite.rotateImage(tankImg, facingDirection);
            
            // Vẽ xe tăng
            g.drawImage(rotatedTank, x, y, size, size, null);
        } //else {
            // Vẽ hình vuông người chơi nếu không có sprite
           // g.setColor(color);
            //g.fillRect(x, y, size, size);
            
            // // Vẽ chỉ báo hướng nhìn
            // g.setColor(Color.YELLOW);
            
            // // Vẽ chỉ báo tam giác thể hiện hướng nhìn của người chơi
            // int[] xPoints = new int[3];
            // int[] yPoints = new int[3];
            
            // switch (facingDirection) {
            //     case 0: // Lên
            //         xPoints[0] = x + size/2;     yPoints[0] = y - 5;
            //         xPoints[1] = x + size/2 - 5; yPoints[1] = y;
            //         xPoints[2] = x + size/2 + 5; yPoints[2] = y;
            //         break;
            //     case 1: // Xuống
            //         xPoints[0] = x + size/2;     yPoints[0] = y + size + 5;
            //         xPoints[1] = x + size/2 - 5; yPoints[1] = y + size;
            //         xPoints[2] = x + size/2 + 5; yPoints[2] = y + size;
            //         break;
            //     case 2: // Trái
            //         xPoints[0] = x - 5;          yPoints[0] = y + size/2;
            //         xPoints[1] = x;              yPoints[1] = y + size/2 - 5;
            //         xPoints[2] = x;              yPoints[2] = y + size/2 + 5;
            //         break;
            //     case 3: // Phải
            //         xPoints[0] = x + size + 5;   yPoints[0] = y + size/2;
            //         xPoints[1] = x + size;       yPoints[1] = y + size/2 - 5;
            //         xPoints[2] = x + size;       yPoints[2] = y + size/2 + 5;
            //         break;
          //  }
            
            //g.fillPolygon(xPoints, yPoints, 3);
       // }

        // Vẽ nhãn người chơi phía trên
        //g.setColor(Color.WHITE);
        //g.setFont(new Font("Arial", Font.BOLD, 14));
        //g.drawString(label, x + (size / 2) - 10, y - 10);
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

    public Rectangle getBounds() {
        return hitBox;
    }
}
