package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Player {
    private int x, y;
    private int speed;
    private int size;
    private int direction = -1; // No movement initially
    private Color color;
    private String label; // Player label (P1 or P2)

    public Player(int x, int y, int speed, int size, Color color, String label) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = color;
        this.label = label;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void move() {
        // Only move if a direction is set
        if (direction == -1)
            return;

        // Increase movement speed for smoother motion
        int actualSpeed = speed * 3; // Multiplier for smoother movement

        switch (direction) {
            case 0: // Up
                y -= actualSpeed;
                break;
            case 1: // Down
                if (y + actualSpeed > 600) {
                    y +=( y + actualSpeed) - 600;
                } else {
                    y += actualSpeed;
                }

                break;
            case 2: // Left
                x -= actualSpeed;
                break;
            case 3: // Right
                if (x + actualSpeed > 800) {
                    x += (x + actualSpeed) - 800;
                } else {
                    x += actualSpeed;
                }

                break;
            // Add other cases as needed
        }

        // Basic boundary checking
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x > 800 - size)
            x = 800 - size - size / 2;
        if (y > 600 - size )
            y = 600 - size - size ;
    }

    public void draw(Graphics g) {
        // Draw player rectangle
        g.setColor(color);
        g.fillRect(x, y, size, size);

        // Draw player label above the player
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(label, x + (size / 2) - 10, y - 10);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
