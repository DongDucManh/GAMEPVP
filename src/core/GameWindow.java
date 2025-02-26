package core;

import javax.swing.JFrame;

public class GameWindow {
    private GamePanel gamePanel;
    private JFrame jFrame;
    public GameWindow() {
        jFrame = new JFrame("Game");
        gamePanel = new GamePanel();
        jFrame.add(gamePanel);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
}
