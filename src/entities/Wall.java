package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;;;

public class Wall {
    private float x, y;
    private BufferedImage img;

    private boolean visible;

    private Rectangle hitBox;

    public Wall(float x, float y, BufferedImage img) {
        this.y = y;
        this.x = x;
        this.img = img;
        this.visible = true;
        this.hitBox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(img, (int) x, (int) y, null);
        g.dispose();
    }

    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
