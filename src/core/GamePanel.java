package core;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.*;
import inputs.KeyBoardsHandle;
import tile.TileManager;
import effect.Boom;
import graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GamePanel - lớp chính xử lý logic game và render
 * Kế thừa JPanel để vẽ các thành phần đồ họa
 */
public class GamePanel extends JPanel {
    private Player player_1, player_2;     // Hai người chơi trong game
    private KeyBoardsHandle keyBoardsHandle;  // Xử lý đầu vào từ bàn phím
    private Sprite sprite;                 // Quản lý hình ảnh sprite
    private boolean drawMap = true;
    TileManager tileM = new TileManager(this);
    private int aniTick = 25;
    private int aniIndex = 25;
    private int aniSpeed = 25;
    
    /**
     * Khởi tạo panel game và các thành phần cần thiết
     */
    public GamePanel() {
        // Khởi tạo sprite
        sprite = new Sprite();
        
        // Tạo người chơi với các thông số và nhãn
        player_1 = new Player(GameConstants.TILE_SIZE + GameConstants.TILE_SIZE/2, GameConstants.TILE_SIZE + GameConstants.TILE_SIZE/2, 32, Color.BLUE, "P1", sprite);
        player_2 = new Player(GameConstants.GAME_SCREEN_WIDTH-GameConstants.TILE_SIZE*3, GameConstants.GAME_SCREEN_HEIGHT-GameConstants.TILE_SIZE*3-GameConstants.TILE_SIZE/2, 32, Color.RED, "P2", sprite);

        // Thiết lập xử lý đầu vào
        keyBoardsHandle = new KeyBoardsHandle(this);
        addKeyListener(keyBoardsHandle);
        setBackground(Color.DARK_GRAY);
        
        // Đảm bảo panel có thể nhận focus để xử lý phím
        setFocusable(true);
        requestFocus();
        player_2.setMoveDirection(2);
        player_2.updateFacingDirection(false);

    }

    /**
     * Cập nhật trạng thái game mỗi chu kỳ
     * Được gọi từ vòng lặp game chính
     */
    public void updateGame() {
        // Xử lý đầu vào và cập nhật trạng thái
        Graphics2D g;
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
    private void checkCollisions() {
        checkBulletPlayerCollisions();

        checkPlayerGrassCollision(player_1);
        checkPlayerGrassCollision(player_2);

        checkBulletWallCollision(player_1);
        checkBulletWallCollision(player_2);

        checkPlayerWallCollision(player_1);
        checkPlayerWallCollision(player_2);

        checkPlayerWaterCollision(player_1);
        checkPlayerWaterCollision(player_2);

        checkTwoPlayerCollision(player_2, player_1);
    }

    private void checkPlayerGrassCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
    
        for (Grass grass : new ArrayList<>(tileM.getGrasses())) {
            if (playerBounds.intersects(grass.getHitBox())) {
                player.setHidden(true);
                break;
            } else {
                player.setHidden(false);
            }
        }
    }

    private void checkPlayerWaterCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
    
        for (Water water : new ArrayList<>(tileM.getWaters())) {
            if (playerBounds.intersects(water.getHitBox())) {
                player.setSpeed(1);
                break;
            } else {
                player.setSpeed(4);
            }
        }
    }

    private void checkBulletWallCollision(Player player) {
        ArrayList<Bullet> bullets = new ArrayList<>(player.getBullets());

        for(Bullet bullet : bullets) {

            Rectangle bulletBounds = bullet.getBounds();    
            
            for(Wall wall : new ArrayList<>(tileM.getWalls())) {
                if(bulletBounds.intersects(wall.getHitBox())) {
                    bullet.deactivate();
                    break;
                }
            }
        }
    }

    private void checkPlayerWallCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
    
        for (Wall wall : new ArrayList<>(tileM.getWalls())) {
            if (playerBounds.intersects(wall.getHitBox())) {
                player.undoMove();
                break;
            }
        }
    }

    private void checkTwoPlayerCollision(Player p1, Player p2){
        if (p1.getBounds().intersects(p2.getBounds()) || p2.getBounds().intersects(p1.getBounds())){
            p1.undoMove();
            p2.undoMove();
        }
    }

    /**
     * Kiểm tra va chạm giữa đạn và người chơi
     */
    private void updateHealth(Player p1){
        p1.setHealth(p1.getHealth() - Bullet.DAME);
            p1.checkDead();
            if (!p1.getIsDead()){
                    
            }
    }
    private void checkBulletPlayerCollisions() {
        // Kiểm tra đạn của người chơi 1 có trúng người chơi 2 không
        for (Bullet bullet : new ArrayList<>(player_1.getBullets())) {
            Rectangle bulletBound = bullet.getBounds();
            Rectangle player2Bound = new Rectangle(
                player_2.getX(), player_2.getY(), 
                player_2.getSize(), player_2.getSize()
            );
            
            if (bulletBound.intersects(player2Bound)) {
                bullet.deactivate();
                updateHealth(player_2);
            }
        }
        
        // Kiểm tra đạn của người chơi 2 có trúng người chơi 1 không
        for (Bullet bullet : new ArrayList<>(player_2.getBullets())) {
            Rectangle bulletBound = bullet.getBounds();
            Rectangle player1Bound = new Rectangle(
                player_1.getX(), player_1.getY(), 
                player_1.getSize(), player_1.getSize()
            );
            
            if (bulletBound.intersects(player1Bound)) {
                bullet.deactivate();
                // Xử lý logic khi trúng đạn
                updateHealth(player_1);
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
        // if(drawMap == true) {
        //     System.out.println(1);
        tileM.draw(g);
        //     drawMap = false;
        // }

        // Vẽ người chơi
        player_1.draw(g);
        
        player_2.draw(g);
        
        // Vẽ đạn
        player_1.drawBullets(g);
        player_2.drawBullets(g);
        
    }

}
