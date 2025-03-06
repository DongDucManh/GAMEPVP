package Tile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.GameConstants;
import core.GamePanel;
import entities.Wall;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    ArrayList<Wall> walls = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        mapTileNum = new int[GameConstants.MAX_SCREEN_COL][GameConstants.MAX_SCREEN_ROW];
        tile = new Tile[10];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("res/tiles/land.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("res/tiles/brick32x32.png"));
            // tile[2].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("res/tiles/cactus.png"));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void loadMap() {
        try {
            InputStream is = new FileInputStream("res/maps/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < GameConstants.MAX_SCREEN_COL && row < GameConstants.MAX_SCREEN_ROW) {

                String line = br.readLine();

                while(col < GameConstants.MAX_SCREEN_COL) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == GameConstants.MAX_SCREEN_COL) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public void updateWall(Graphics2D g) {
    //     for(Wall wall : walls) {
    //         if(wall.isVisible()) {
    //             wall.drawImage(g);
    //         }
    //     }
    // }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int col = 0, row = 0;
        int x = 0, y = 0;

        while(col < GameConstants.MAX_SCREEN_COL && row < GameConstants.MAX_SCREEN_ROW) {
            
            int tileNum = mapTileNum[col][row];

            if(tileNum == 1) {
                g2.drawImage(tile[tileNum].image, x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
                Wall wall = new Wall(x, y, tile[tileNum].image);
                walls.add(wall);
            }
            g2.drawImage(tile[tileNum].image, x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
            
            col++;
            x += GameConstants.TILE_SIZE;

            if(col == GameConstants.MAX_SCREEN_COL) {
                col = 0;
                x = 0;
                row++;
                y += GameConstants.TILE_SIZE;
            }
        }
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }
}
