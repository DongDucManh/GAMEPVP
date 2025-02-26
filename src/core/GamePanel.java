package core;

import javax.swing.JPanel;

import inputs.KeyBoardsHandle;

public class GamePanel extends JPanel {
 
    public GamePanel() {
        addKeyListener(new KeyBoardsHandle(this));
    }
}
