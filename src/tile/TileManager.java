package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

        tile = new Tile[GameConstants.GAME_SCREEN_WIDTH/32][GameConstants.GAME_SCREEN_HEIGHT/32];
        // tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {
        String file = "map.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = new String();
            for(int i=0; i<GameConstants.GAME_SCREEN_WIDTH/32; i++) {
                line = br.readLine();
                if (line.equals("end")) {
                    break;
                }
                for(int j=0; j<GameConstants.GAME_SCREEN_HEIGHT/32; j++) {
                    if(line.charAt(j) == '0') {
                        tile[i][j] = new Tile();
                        tile[i][j].image = ImageIO.read(new File("images/tiles/land.png")); 
                    } else {
                        tile[i][j] = new Tile();
                        tile[i][j].image = ImageIO.read(new File("images/tiles/brick32x32.png")); 
                    }
                    System.out.println(line.charAt(j));
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
