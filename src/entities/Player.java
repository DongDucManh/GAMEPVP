package entities;

import java.awt.Color;
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
    private int speed = 4;                     // Tốc độ di chuyển
    private int size;                      // Kích thước hình vuông người chơi
    private int moveDirection = -1;        // Hướng di chuyển (-1: không di chuyển, 0: tiến, 1: lùi)
    private int rotationDirection = -1;    // Hướng xoay (-1: không xoay, 0: xoay trái, 1: xoay phải)
    private double angle = 0;              // Góc xoay (độ)
    private double rotationSpeed = 3;      // Tốc độ xoay (độ mỗi khung hình)
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
    private int health ;
    private int maxHealth = 100;
    private boolean isDead = false;
    private boolean isPlayerControlled;
    private boolean isHidden;
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
    public Player(int x, int y, int size, Color color, String label, Sprite sprite) {
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
        this.health = maxHealth;
        this.hitBox = new Rectangle(x, y, size, size);
    }

    /**
     * Đặt hướng di chuyển hiện tại
     * @param direction Hướng di chuyển (0:tiến, 1:lùi, -1:không di chuyển, 10:hồi máu)
     */
    public void setMoveDirection(int direction) {
        this.moveDirection = direction;
    }
    
    /**
     * Đặt hướng xoay
     * @param direction Hướng xoay (0:xoay trái, 1:xoay phải, -1:không xoay)
     */
    public void setRotationDirection(int direction) {
        this.rotationDirection = direction;
    }
    
    public void setHealth(int health){
        this.health = health;
    }
    public int getHealth(){
        return this.health;
    }
    public void checkDead(){
        if (health<=0){
            isDead = true;
        }
    }
    public boolean getIsDead(){
        return !isDead;
    }
    /**
     * Cập nhật hướng nhìn dựa trên góc xoay
     */
    public void updateFacingDirection(boolean lockDirection) {
        // Chuyển đổi góc thành hướng nhìn (0-3)
        if (!lockDirection) {
            // Từ góc tính hướng nhìn (0: lên, 1: xuống, 2: trái, 3: phải)
            double normalizedAngle = angle % 360;
            if (normalizedAngle < 0) normalizedAngle += 360;
            
            if ((normalizedAngle >= 315 && normalizedAngle <= 360) || (normalizedAngle >= 0 && normalizedAngle < 45)) {
                facingDirection = 3; // Phải
            } else if (normalizedAngle >= 45 && normalizedAngle < 135) {
                facingDirection = 1; // Xuống
            } else if (normalizedAngle >= 135 && normalizedAngle < 225) {
                facingDirection = 2; // Trái
            } else {
                facingDirection = 0; // Lên
            }
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
        // Xử lý xoay xe tăng
        if (rotationDirection == 0) { // Xoay trái
            angle -= rotationSpeed;
        } else if (rotationDirection == 1) { // Xoay phải
            angle += rotationSpeed;
        }
        
        // Giữ góc trong khoảng [0, 360)
        angle = angle % 360;
        if (angle < 0) angle += 360;
        
        // Cập nhật hướng nhìn dựa trên góc
        updateFacingDirection(false);
        
        // Lưu vị trí trước khi di chuyển
        previousX = x;
        previousY = y;
        
        // Xử lý di chuyển đặc biệt (hồi máu)
        if (moveDirection == 10) {
            health = maxHealth; 
            return;
        }
        
        // Chỉ di chuyển khi có hướng di chuyển
        if (moveDirection == -1) {
            isPlayerControlled = false;
            return;
        }
        
        isPlayerControlled = true;
        
        // Tính toán hướng di chuyển dựa trên góc và hướng tiến/lùi
        double radians = Math.toRadians(angle);
        int actualSpeed = speed;
        
        // Tính toán di chuyển dựa trên góc và hướng di chuyển
        if (moveDirection == 0) { // Tiến
            x += (int)(Math.cos(radians) * actualSpeed);
            y += (int)(Math.sin(radians) * actualSpeed);
        } else if (moveDirection == 1) { // Lùi
            x -= (int)(Math.cos(radians) * actualSpeed);
            y -= (int)(Math.sin(radians) * actualSpeed);
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
        
        // Cập nhật vị trí hitbox
        hitBox.setLocation(x, y);
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
        
        // Tính toán vị trí bắt đầu của đạn
        double radians = Math.toRadians(angle);
        int bulletX = x + size/2 - 4;
        int bulletY = y + size/2 - 4;
        
        // Đặt đạn ở đầu nòng súng
        bulletX += (int)(Math.cos(radians) * size/2);
        bulletY += (int)(Math.sin(radians) * size/2);
        
        // Tạo đạn với góc hiện tại của xe tăng
        bullets.add(new Bullet(bulletX, bulletY, angle, color, sprite));
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
        if(!isHidden) {
            if (sprite != null) {
                // Lấy hình ảnh xe tăng
                BufferedImage tankImg = sprite.getTank(isBlue);
                
                // Xoay hình ảnh theo góc
                BufferedImage rotatedTank = sprite.rotateImageByAngle(tankImg, angle);
                
                // Vẽ xe tăng
                g.drawImage(rotatedTank, x, y, size, size, null);
            }
        }
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
    public int getSize() {
        return size;
    }
    public boolean setHidden(boolean isHidden){
        return this.isHidden = isHidden;
    }
    public Rectangle getBounds() {
        return new Rectangle(x,y,size-5,size-5);
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
}
