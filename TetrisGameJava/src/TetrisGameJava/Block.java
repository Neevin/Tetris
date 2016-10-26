package TetrisGameJava;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
class Block { // building element for Figure

    private int x, y;


    public Block(int x, int y) {
        setX(x);
        setY(y);
    }


    void setX(int x) { this.x = x; }
    void setY(int y) { this.y = y; }


    int getX() { return x; }
    int getY() { return y; }


    void paint(Graphics g, int color) {
        g.setColor(new Color(color));
        g.drawRoundRect(x*Settings.BLOCK_SIZE+1, y*Settings.BLOCK_SIZE+1, Settings.BLOCK_SIZE-2, Settings.BLOCK_SIZE-2, Settings.ARC_RADIUS, Settings.ARC_RADIUS);
    }
}
