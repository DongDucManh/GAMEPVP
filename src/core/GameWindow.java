package Core;

import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * GameWindow - quản lý cửa sổ game
 * Sử dụng JFrame để tạo cửa sổ hiển thị
 */
public class GameWindow {
    private JFrame jFrame;  // Cửa sổ chính của game
    
    /**
     * Tạo cửa sổ game với panel được truyền vào
     * @param gamePanel Panel chứa logic game và render
     */
    
    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame("Game");  // Tạo cửa sổ với tiêu đề "Game"
        
        // Thiết lập kích thước cửa sổ
        jFrame.setPreferredSize(new Dimension(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Thoát chương trình khi đóng cửa sổ
        jFrame.setResizable(false);  // Không cho phép thay đổi kích thước
        
        // Thêm game panel vào cửa sổ
        jFrame.add(gamePanel);
        jFrame.pack();  // Đóng gói cửa sổ để có kích thước phù hợp
        
        jFrame.setLocationRelativeTo(null);  // Hiển thị cửa sổ ở giữa màn hình
        jFrame.setVisible(true);  // Hiển thị cửa sổ
    }
}


