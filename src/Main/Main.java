package main;
import menu.StartMenu;

/**
 * Lớp Main - điểm khởi đầu của chương trình
 * Chịu trách nhiệm khởi tạo game và bắt đầu vòng lặp game
 */
public class Main {
    /**
     * Phương thức main - điểm khởi đầu của chương trình
     * @param args Tham số dòng lệnh (không sử dụng)
     */
    static {
        System.out.println("Đang tải game...");
        System.out.println("Khởi động game...");
        //System.exit(0);
    }   
    public static void main(String[] args) {

        StartMenu st = new StartMenu();
    }
}