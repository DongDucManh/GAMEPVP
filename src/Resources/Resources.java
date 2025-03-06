package Resources;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resources {
    private static Map<String, BufferedImage> images = new HashMap<>();

    public Resources() {
        try {
            images.put("redTank", ImageIO.read(getClass().getResource("res/player/redTank.png")));
            images.put("blueTank", ImageIO.read(getClass().getResource("res/player/blueTank.png")));
            images.put("bullet", ImageIO.read(getClass().getResource("res/player/bullet.png")));
            images.put("floor", ImageIO.read(getClass().getResource("res/tiles/floor.png")));
            images.put("brick", ImageIO.read(getClass().getResource("res/tiles/brick.png")));
            images.put("startButton", ImageIO.read(getClass().getResource("res/menu/playButton.png")));
            images.put("exitButton", ImageIO.read(getClass().getResource("res/menu/exitButton.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(String key) {
        return Resources.images.get(key);
    }

    public BufferedImage rotateImage(BufferedImage image, int direction) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = newImage.createGraphics();
        
        // Xác định góc xoay theo hướng
        double angle = 0;
        switch (direction) {
            case 0: angle = -Math.PI/2; break; // Lên
            case 1: angle = Math.PI/2; break;  // Xuống
            case 2: angle = Math.PI; break;    // Trái
            case 3: angle = 0; break;          // Phải
        }
        
        // Thiết lập phép biến đổi
        AffineTransform transform = new AffineTransform();
        transform.translate(width/2, height/2);
        transform.rotate(angle);
        transform.translate(-width/2, -height/2);
        
        // Vẽ hình ảnh xoay
        g.setTransform(transform);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        
        return newImage;
    }
}
