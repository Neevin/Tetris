package TetrisGameJava;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Canvas extends JPanel { // my canvas for painting
    private int[][] mine;
    public Figure figure;
    public Canvas(int[][] mine, Figure figure){
        this.mine = mine;
        this.figure=figure;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < Settings.FIELD_WIDTH; x++)
            for (int y = 0; y < Settings.FIELD_HEIGHT; y++) {
                if (x < Settings.FIELD_WIDTH - 1 && y < Settings.FIELD_HEIGHT - 1) {
                    g.setColor(Color.lightGray);
                    g.drawLine((x+1)*Settings.BLOCK_SIZE-2, (y+1)*Settings.BLOCK_SIZE, (x+1)*Settings.BLOCK_SIZE+2, (y+1)*Settings.BLOCK_SIZE);
                    g.drawLine((x+1)*Settings.BLOCK_SIZE, (y+1)*Settings.BLOCK_SIZE-2, (x+1)*Settings.BLOCK_SIZE, (y+1)*Settings.BLOCK_SIZE+2);
                }
                if (mine[y][x] > 0) {
                    g.setColor(new Color(mine[y][x]));
                    g.fill3DRect(x*Settings.BLOCK_SIZE+1, y*Settings.BLOCK_SIZE+1, Settings.BLOCK_SIZE-1, Settings.BLOCK_SIZE-1, true);
                }
            }
        if (Settings.gameOver) {
            g.setColor(Color.white);
            for (int y = 0; y < Settings.GAME_OVER_MSG.length; y++)
                for (int x = 0; x < Settings.GAME_OVER_MSG[y].length; x++)
                    if (Settings.GAME_OVER_MSG[y][x] == 1) g.fill3DRect(x*11+18, y*11+160, 10, 10, true);
        } else{
            figure.paint(g);
        }

    }
}
