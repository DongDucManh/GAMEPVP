package tile;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GameConstants;
import core.GamePanel;

public class TileManager {
    
    GamePanel gp;
    // Tile[] tile;
    Tile[][] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[GameConstants.GAME_SCREEN_WIDTH][GameConstants.GAME_SCREEN_HEIGHT];
        // tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {
        try {
            for(int i=0; i<GameConstants.GAME_SCREEN_WIDTH; i+=32) {
                for(int j=0; j<GameConstants.GAME_SCREEN_HEIGHT; j+=32) {
                    tile[i][j] = new Tile();
                    tile[i][j].image = ImageIO.read(new File("images/tiles/land.png")); 
                }
            }
            // tile[0].image = ImageIO.read(new File("images/tiles/brick32x32.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for(int i=0; i<GameConstants.GAME_SCREEN_WIDTH; i+=32) {
            for(int j=0; j<GameConstants.GAME_SCREEN_HEIGHT; j+=32) {
                g2.drawImage(tile[i][j].image, i, j, 32, 32, null);
            }
        }
        // g2.drawImage(tile[0].image, 0, 0, 32, 32, null);
    }
}
