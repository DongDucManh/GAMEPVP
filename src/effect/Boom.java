package effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Sprite;

public class Boom {
    private int x;
    private int y;
    private ArrayList<BufferedImage> images;
    private Sprite sprite;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = new Sprite();
        this.images = sprite.getBooms();
    }

    public BufferedImage getImage(int index) {
        return images.get(index);
    }

    // public void draw(Graphics g) {
    //     Graphics2D g2d = (Graphics2D) g;
    //     for(int i = 0; i < images.size(); i++) {
    //         int j = 0;
    //         while(j<=15) {
    //             j++;
    //         }
    //         g2d.drawImage(images.get(i), x, y, null);
    //         System.out.println(i);
    //     }
    // }
}
