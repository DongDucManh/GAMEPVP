package tile;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GameConstants;
import core.GamePanel;

public class TileManager {
    
    GamePanel gp;
    Tile[][] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[GameConstants.GAME_SCREEN_WIDTH][GameConstants.GAME_SCREEN_HEIGHT];

        getTileImage();
    }

    public void getTileImage() {
        try {
            for(int i=0; i<GameConstants.GAME_SCREEN_WIDTH; i+=96) {
                for(int j=0; j<GameConstants.GAME_SCREEN_HEIGHT; j+=96) {
                    tile[i][j] = new Tile();
                    tile[i][j].image = ImageIO.read(new File("images/tiles/land96x96.png")); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for(int i=0; i<GameConstants.GAME_SCREEN_WIDTH; i+=96) {
            for(int j=0; j<GameConstants.GAME_SCREEN_HEIGHT; j+=96) {
                g2.drawImage(tile[i][j].image, i, j, 1440, 800, null);
            }
        }
        
    }
}
