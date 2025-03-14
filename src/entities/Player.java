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
    // Vị trí và chuyển động
    private double xPos, yPos; // Vị trí thực (số thực)
    private int x, y; // Vị trí hiển thị (số nguyên)
    private int speed = 4; // Tốc độ di chuyển
    private int size; // Kích thước hình vuông người chơi
    private int moveDirection = -1; // Hướng di chuyển (-1: không di chuyển, 0: tiến, 1: lùi)
    private int rotationDirection = -1; // Hướng xoay (-1: không xoay, 0: xoay trái, 1: xoay phải)
    private double angle = 0; // Góc xoay (độ)
    private double rotationSpeed = 1; // Tốc độ xoay (độ mỗi khung hình)
    private int facingDirection = 3; // Hướng nhìn (mặc định: phải)

    // Thuộc tính
    private Color color; // Màu sắc người chơi
    private List<Bullet> bullets; // Danh sách đạn đã bắn
    private long lastShootTime; // Thời gian bắn đạn cuối cùng
    private static final long SHOOT_COOLDOWN = 500; // Thời gian hồi chiêu (ms)
    private boolean isBlue; // Xác định màu xe tăng (true: xanh, false: đỏ)
    private Sprite sprite; // Tham chiếu đến đối tượng Sprite
    private int previousX; // Vị trí X trước khi di chuyển
    private int previousY; // Vị trí Y trước khi di chuyển
    private Rectangle hitBox; // Hộp va chạm
    private int health; // Mức máu hiện tại
    private int maxHealth = 100; // Mức máu tối đa
    private boolean isDead = false; // Trạng thái sống/chết
    private boolean isHidden; // Có đang ẩn không (ví dụ: trong cỏ)

    // Các hằng số kiểm soát chuyển động
    private static final double MIN_MOVEMENT_THRESHOLD = 0.01; // Ngưỡng tối thiểu để phát hiện chuyển động
    private static final int POSITION_DECIMALS = 2; // Số chữ số thập phân cần giữ lại

    /**
     * Khởi tạo người chơi
     */
    public Player(int x, int y, int size, Color color, String label, Sprite sprite) {
        this.xPos = x;
        this.yPos = y;
        this.x = x;
        this.y = y;

        this.size = size;
        this.color = color;
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
     * 
     * @param direction Hướng di chuyển (0:tiến, 1:lùi, -1:không di chuyển, 10:hồi
     *                  máu)
     */
    public void setMoveDirection(int direction) {
        this.moveDirection = direction;
    }

    /**
     * Đặt hướng xoay
     * 
     * @param direction Hướng xoay (0:xoay trái, 1:xoay phải, -1:không xoay)
     */
    public void setRotationDirection(int direction) {
        this.rotationDirection = direction;
    }

    /**
     * Đặt mức máu
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Lấy mức máu hiện tại
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Kiểm tra trạng thái sống chết
     */
    public void checkDead() {
        if (health <= 0) {
            isDead = true;
        }
    }

    /**
     * Kiểm tra nếu người chơi vẫn còn sống
     */
    public boolean getIsDead() {
        return !isDead;
    }

    /**
     * Cập nhật hướng nhìn dựa trên góc xoay
     */
    public void updateFacingDirection(boolean lockDirection) {
        // Chỉ cập nhật khi không bị khóa
        if (!lockDirection) {
            // Chuẩn hóa góc về đoạn [0, 360)
            double normalizedAngle = angle % 360;
            if (normalizedAngle < 0)
                normalizedAngle += 360;

            // Xác định hướng nhìn dựa trên góc
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
     * 
     * @return Hướng nhìn (0:lên, 1:xuống, 2:trái, 3:phải)
     */
    public int getFacingDirection() {
        return facingDirection;
    }

    /**
     * Làm tròn một giá trị double với độ chính xác xác định
     * 
     * @param value  Giá trị cần làm tròn
     * @param places Số chữ số thập phân cần giữ
     * @return Giá trị đã làm tròn
     */
    private double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * Di chuyển người chơi dựa trên hướng và tốc độ hiện tại
     */
    public void move() {
        // Xử lý xoay xe tăng với độ chính xác cao hơn
        if (rotationDirection != -1) {
            // Lưu góc trước khi xoay để phát hiện thay đổi nhỏ
            double previousAngle = angle;

            if (rotationDirection == 0) { // Xoay trái
                angle -= rotationSpeed;
            } else if (rotationDirection == 1) { // Xoay phải
                angle += rotationSpeed;
            }

            // Giới hạn góc trong khoảng [0, 360)
            angle = angle % 360;
            if (angle < 0)
                angle += 360;

            // Chỉ cập nhật khi góc thay đổi đủ lớn
            if (Math.abs(angle - previousAngle) > MIN_MOVEMENT_THRESHOLD) {
                updateFacingDirection(false);
            }
        }

        // Lưu vị trí trước khi di chuyển
        previousX = x;
        previousY = y;

        // Xử lý di chuyển đặc biệt (hồi máu)
        if (moveDirection == 10) {
            health = maxHealth;
            return;
        }

        // Tính toán di chuyển dựa trên góc và hướng tiến/lùi
        double radians = Math.toRadians(angle);
        if (moveDirection != -1) {
            double deltaX = Math.cos(radians), deltaY = Math.sin(radians);

            // Tính toán delta chuyển động
            if (moveDirection == 0) { // Tiến
                deltaX = Math.cos(radians) * speed;
                deltaY = Math.sin(radians) * speed;
            } else if (moveDirection == 1) { // Lùi
                deltaX = -Math.cos(radians) * speed;
                deltaY = -Math.sin(radians) * speed;
            }

            // Làm tròn để tránh lỗi tích lũy số thực
            deltaX = round(deltaX, POSITION_DECIMALS);
            deltaY = round(deltaY, POSITION_DECIMALS);

            // Chỉ áp dụng thay đổi nếu đủ lớn để tránh lỗi rung
            if (Math.abs(deltaX) > MIN_MOVEMENT_THRESHOLD) {
                xPos += deltaX;
            }

            if (Math.abs(deltaY) > MIN_MOVEMENT_THRESHOLD) {
                yPos += deltaY;
            }

            // Giới hạn trong màn hình
            if (xPos < 0)
                xPos = 0;
            if (yPos < 0)
                yPos = 0;
            if (xPos > GameConstants.GAME_SCREEN_WIDTH - size - size / 2 + 4)
                xPos = GameConstants.GAME_SCREEN_WIDTH - size - size / 2 + 4;
            if (yPos > GameConstants.GAME_SCREEN_HEIGHT - size - size - 1)
                yPos = GameConstants.GAME_SCREEN_HEIGHT - size - size - 1;

            // Cập nhật vị trí hiển thị (làm tròn từ vị trí thực)
            x = (int) Math.round(xPos);
            y = (int) Math.round(yPos);

            // Cập nhật hitbox
            hitBox.setLocation(x, y);
        }
    }

    /**
     * Hoàn tác di chuyển khi va chạm
     */
    public void undoMove() {
        this.xPos = this.previousX;
        this.yPos = this.previousY;
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
        int bulletX = x + size / 2 - 4;
        int bulletY = y + size / 2 - 4;

        // Đặt đạn ở đầu nòng súng
        bulletX += (int) (Math.cos(radians) * size / 2);
        bulletY += (int) (Math.sin(radians) * size / 2);

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
     * 
     * @param g Đối tượng đồ họa để vẽ
     */
    public void drawBullets(Graphics g) {
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    /**
     * Lấy danh sách đạn
     * 
     * @return Danh sách đạn đang hoạt động
     */
    public List<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Vẽ người chơi và chỉ báo hướng nhìn
     * 
     * @param g Đối tượng đồ họa để vẽ
     */
    public void draw(Graphics g) {
        if (!isHidden) {
            if (sprite != null) {
                // Lấy hình ảnh xe tăng
                BufferedImage tankImg = sprite.getTank(isBlue);

                // Xoay hình ảnh theo góc
                BufferedImage rotatedTank = sprite.rotateImageByAngle(tankImg, angle);
                // xoay hitbox theo góc
                hitBox = new Rectangle(x, y, size, size);

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

    public boolean setHidden(boolean isHidden) {
        return this.isHidden = isHidden;
    }

    public Rectangle getBounds() {
        return hitBox;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
