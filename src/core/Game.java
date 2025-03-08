package core;

/**
 * Lớp Game - quản lý vòng lặp game chính và các thành phần cốt lõi
 * Thực hiện Runnable để tạo một luồng riêng cho vòng lặp game
 */
public class Game implements Runnable {
    private GameWindow gameWindow;   
    private GamePanel gamePanel;    
    private Thread gameThread;       
    private final int FPS_SET = 120; 
    private final int UPS_SET = 40; 
    
    public Game() {
        gamePanel = new GamePanel();             
        gameWindow = new GameWindow(gamePanel);   
        gamePanel.requestFocus();                 // Đặt focus vào panel để nhận input
        startGameLoop();                      
    }

    private void startGameLoop() {
        gameThread = new Thread(this);  // Tạo luồng mới với Game là Runnable
        gameThread.start();             // Bắt đầu luồng (gọi phương thức run())
    }


    /**
     * Vòng lặp game chính - chạy trong một luồng riêng
     * Sử dụng timestepping để đảm bảo tốc độ nhất quán trên các hệ thống khác nhau
     */
    @Override
    public void run() {
        // Tính toán thời gian cho mỗi khung hình và mỗi lần cập nhật logic
        double timePerFrame = 1000000000.0 / FPS_SET;    // Nano giây cho mỗi khung hình
        double timePerUpdate = 1000000000.0 / UPS_SET;   // Nano giây cho mỗi lần cập nhật
        
        long previousTime = System.nanoTime();  // Thời điểm của vòng lặp trước đó
        
        int frames = 0;    // Đếm số khung hình đã vẽ
        int updates = 0;   // Đếm số lần cập nhật logic
        long lastCheck = System.currentTimeMillis();  // Thời điểm kiểm tra FPS cuối cùng
        
        double deltaU = 0;  // Thời gian tích lũy cho cập nhật
        double deltaF = 0;  // Thời gian tích lũy cho vẽ khung hình
        
        // Vòng lặp game vô hạn
        while (true) {
            long currentTime = System.nanoTime();  // Thời điểm hiện tại
            
            // Tính toán thời gian đã trôi qua kể từ vòng lặp trước
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            
            // Nếu đủ thời gian cho việc cập nhật logic
            if (deltaU >= 1) {
                // Cập nhật trạng thái game
                gamePanel.updateGame();
                updates++;
                deltaU--;
            }
            
            // Nếu đủ thời gian cho việc vẽ khung hình
            if (deltaF >= 1) {
                // Vẽ lại màn hình game
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            
            // Hiển thị FPS mỗi giây
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }
        }
    }
}
