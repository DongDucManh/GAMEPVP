//Quản lý các hình ảnh, sprite của game
package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Sprite - Lớp quản lý các hình ảnh trong game
 * Chịu trách nhiệm tải và lưu trữ các sprite
 */
public class Sprite {
    // Kích thước mặc định của sprite
    public static final int SIZE = 64;
    
    // Các sprite cho nhân vật và đạn
    private BufferedImage blueTank;
    private BufferedImage redTank;
    private BufferedImage bullet;
    private ArrayList<BufferedImage> booms;
    
    /**
     * Khởi tạo và tải tất cả các hình ảnh
     */
    public Sprite() {
        // Tạo mặc định trước để đảm bảo luôn có hình ảnh
        blueTank = createDefaultTank(0x3333FF);
        redTank = createDefaultTank(0xFF3333);
        bullet = createDefaultBullet();
        booms = new ArrayList<>();
        // Sau đó thử tải hình ảnh từ file
        tryLoadImages();
    }
    
    /**
     * Thử tải hình ảnh từ các vị trí khác nhau
     */
    private void tryLoadImages() {
        try {
            // Kiểm tra và tạo thư mục images nếu chưa tồn tại
            // File imageDir = new File("images");
            // if (!imageDir.exists()) {
            //     imageDir.mkdir();
            //     System.out.println("Đã tạo thư mục images");
            // }
            
            // Thử tải từ nhiều vị trí khác nhau
            blueTank = ImageIO.read(new File("res/player/BlueTank.png"));
            redTank = ImageIO.read(new File("res/player/redTank.png"));
            bullet = ImageIO.read(new File("res/player/bullet.png"));
            for(int i=1; i<=15; i++) {
                booms.add(ImageIO.read(new File("res/effects/boom/" + i + ".png")));
            }
            // BufferedImage loadedBlue = loadImage("BlueTank.png");
            // if (loadedBlue != null) blueTank = loadedBlue;
            
            // BufferedImage loadedRed = loadImage("redTank.png");
            // if (loadedRed != null) redTank = loadedRed;
            
            // BufferedImage loadedBullet = loadImage("bullet.png");
            // if (loadedBullet != null) bullet = loadedBullet;
            
            // System.out.println("Tải hình ảnh thành công: " + 
            //                   (loadedBlue != null ? "blueTank.png " : "") +
            //                   (loadedRed != null ? "redTank.png " : "") +
            //                   (loadedBullet != null ? "bullet.png" : ""));
            
            // if (loadedBlue == null && loadedRed == null && loadedBullet == null) {
            //     System.out.println("Không tìm thấy file hình ảnh, đang sử dụng hình ảnh mặc định");
            //     System.out.println("Vui lòng tạo các file hình ảnh trong thư mục: " + imageDir.getAbsolutePath());
            // }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // /**
    //  * Tải một hình ảnh từ thư mục
    //  * @param filename Tên file hình ảnh
    //  * @return BufferedImage đã tải, hoặc null nếu không tìm thấy
    //  */
    // private BufferedImage loadImage(String filename) {
    //     BufferedImage img = null;
        
    //     try {
    //         // // Thử từ thư mục images trong thư mục gốc
    //         // File file = new File("images/" + filename);
    //         // if (file.exists()) {
    //         //     System.out.println("Tìm thấy file: " + file.getAbsolutePath());
    //         //     return ImageIO.read(file);
    //         // }
            
    //         // // Thử từ D:/GamePVP/images/
    //         // file = new File("D:/GamePVP/images/" + filename);
    //         // if (file.exists()) {
    //         //     System.out.println("Tìm thấy file: " + file.getAbsolutePath());
    //         //     return ImageIO.read(file);
    //         // }
            
    //         // // Thử từ thư mục hiện tại
    //         // file = new File(filename);
    //         // if (file.exists()) {
    //         //     System.out.println("Tìm thấy file: " + file.getAbsolutePath());
    //         //     return ImageIO.read(file);
    //         // }
            
    //         // Thử tải từ resources
    //         img = ImageIO.read(new File("/res/player/" + filename));
    //         if (img != null) {
    //             System.out.println("Tìm thấy file trong resources: " + filename);
    //             return img;
    //         }
            
    //         System.out.println("Không tìm thấy file hình ảnh: " + filename);
            
    //     } catch (IOException e) {
    //         System.out.println("Lỗi đọc file " + filename + ": " + e.getMessage());
    //     }
        
    //     return null;
    // }
    
    /**
     * Tạo hình ảnh xe tăng mặc định với màu chỉ định
     * @param color Mã màu RGB
     * @return BufferedImage của xe tăng
     */
    private BufferedImage createDefaultTank(int color) {
        BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        // Vẽ thân xe
        g.setColor(new java.awt.Color(color));
        g.fillRect(4, 4, SIZE - 8, SIZE - 8);
        
        // Vẽ nòng súng
        g.setColor(new java.awt.Color(0x666666));
        g.fillRect(SIZE - 8, SIZE/2 - 2, 8, 4);
        
        g.dispose();
        return img;
    }
    
    /**
     * Tạo hình ảnh đạn mặc định
     * @return BufferedImage của đạn
     */
    private BufferedImage createDefaultBullet() {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        // Tạo hình tròn cho đạn với nhiều lớp màu
        g.setColor(new java.awt.Color(0x000000)); // Đen
        g.fillOval(0, 0, 5, 5);
        
        // g.setColor(new java.awt.Color(0xFF6600)); // Cam
        // g.fillOval(2, 2, 6, 6);
        
        // g.setColor(new java.awt.Color(0xFFFF00)); // Vàng
        // g.fillOval(4, 4, 2, 2);
        
        g.dispose();
        return img;
    }
    
    /**
     * Lấy hình ảnh xe tăng theo màu
     * @param isBlue true nếu là xe tăng xanh, false nếu là xe tăng đỏ
     * @return BufferedImage của xe tăng
     */
    public BufferedImage getTank(boolean isBlue) {
        return isBlue ? blueTank : redTank;
    }
    
    /**
     * Lấy hình ảnh đạn
     * @return BufferedImage của đạn
     */
    public BufferedImage getBullet() {
        return bullet;
    }
    
    /**
     * Xoay một hình ảnh theo hướng chỉ định
     * @param image Hình ảnh cần xoay
     * @param direction Hướng (0: lên, 1: xuống, 2: trái, 3: phải)
     * @return BufferedImage đã xoay
     */
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

    public ArrayList<BufferedImage> getBooms() {
        return booms;
    }
    
}
