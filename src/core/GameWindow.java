package core;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GameWindow {
    private JFrame jFrame;
    
    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame("Game");
        
        // Set the size of the JFrame according to the preferred size
        jFrame.setPreferredSize(new Dimension(800, 600));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        
        // Add the game panel
        jFrame.add(gamePanel);
        jFrame.pack(); // Pack the frame to get proper sizing
        
        jFrame.setLocationRelativeTo(null); // Center on screen
        jFrame.setVisible(true);
    }
}
