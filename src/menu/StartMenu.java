package menu;


import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import core.Game;
import core.GameConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StartMenu extends JPanel{

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;

    public StartMenu()
    {

        JFrame f = new JFrame("Menu");
        f.setSize(GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setBackground(Color.RED);
        f.setLayout(null);


        start = new JButton(new ImageIcon("res/image menu/playbutton.png"));
        start.setFont(new Font("Impact", Font.BOLD, 24));
        start.setBounds(((GameConstants.GAME_SCREEN_WIDTH / 2) - 220) / 2, 325, 220, 130);
        start.setBorderPainted(false);
        start.setFocusPainted(false);
        start.setContentAreaFilled(false);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                f.dispose();
                Game g = new Game();
            } 
        });

        exit = new JButton(new ImageIcon("res/image menu/exitbutton.png"));
        exit.setFont(new Font("Impact", Font.BOLD, 24));
        exit.setBounds(((GameConstants.GAME_SCREEN_WIDTH / 2) - 220) / 2, 460, 220, 130);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setContentAreaFilled(false);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                f.dispose();
            } 
        });

        f.add(start);
        f.add(exit);
        f.setVisible(true);
    }

}


