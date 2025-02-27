package core;

import javax.swing.JPanel;

import entities.Player;
import inputs.KeyBoardsHandle;

import java.awt.*;

public class GamePanel extends JPanel {
    private Player player_1, player_2;
    private KeyBoardsHandle keyBoardsHandle;
    
    public GamePanel() {
        // Create players with labels
        player_1 = new Player(100, 100, 1, 30, Color.BLUE, "P1");
        player_2 = new Player(300, 100, 1, 30, Color.RED, "P2");
        
        keyBoardsHandle = new KeyBoardsHandle(this);
        addKeyListener(keyBoardsHandle);
        setBackground(Color.DARK_GRAY);
        
        // Make sure panel is focusable
        setFocusable(true);
        requestFocus();
    }

    public void updateGame() {
        // Update both players
        setMoving(1);
        setMoving(2);
    }
    
    public void setMoving(int p){
         if (p==1){
            player_1.setDirection(keyBoardsHandle.getDirectionP1());
            player_1.move();
         }
         if (p==2){
            player_2.setDirection(keyBoardsHandle.getDirectionP2());
            player_2.move();
         }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player_1.draw(g);
        player_2.draw(g);
        System.out.println(player_1.getX() + " " + player_1.getY());
    }
}
