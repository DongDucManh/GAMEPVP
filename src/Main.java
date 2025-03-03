import core.Game;
import java.io.File;

/**
 * Lớp Main - điểm khởi đầu của chương trình
 * Chịu trách nhiệm khởi tạo game và bắt đầu vòng lặp game
 */
public class Main {
    /**
     * Phương thức main - điểm khởi đầu của chương trình
     * @param args Tham số dòng lệnh (không sử dụng)
     */
    public static void main(String[] args) {
        System.out.println("Khởi động game...");
        // Khởi tạo đối tượng Game - điều này sẽ bắt đầu vòng lặp game
        Game game = new Game();
    }
}