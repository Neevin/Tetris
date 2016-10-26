package TetrisGameJava;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


class GameTetris extends JFrame {


    int[][] mine = new int[Settings.FIELD_HEIGHT + 1][Settings.FIELD_WIDTH]; // mine/glass
    JFrame frame;
    Figure figure = new Figure(mine);
    Canvas canvas = new Canvas(mine, figure);


    public static void main(String[] args) {
        new GameTetris().go();
    }


    GameTetris() {
        setTitle(Settings.TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(Settings.START_LOCATION, Settings.START_LOCATION, Settings.FIELD_WIDTH * Settings.BLOCK_SIZE + Settings.FIELD_DX, Settings.FIELD_HEIGHT * Settings.BLOCK_SIZE + Settings.FIELD_DY);
        setResizable(false);
        canvas.setBackground(Color.black); // define the background color
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!Settings.gameOver) {
                    if (e.getKeyCode() == Settings.DOWN) {
                        figure.drop();
                    }
                    if (e.getKeyCode() == Settings.UP) {
                        figure.rotate();
                    }
                    if (e.getKeyCode() == Settings.LEFT || e.getKeyCode() == Settings.RIGHT) {
                        figure.move(e.getKeyCode());
                    }
                }
                canvas.repaint();
            }
        });
        add(BorderLayout.CENTER, canvas);
        setVisible(true);
        Arrays.fill(mine[Settings.FIELD_HEIGHT], 1);
    }


    void go() {
        while (!Settings.gameOver) {
            try {
                Thread.sleep(Settings.SHOW_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            canvas.repaint();
            checkFilling();
            if (figure.isTouchGround()) {
                figure.leaveOnTheGround();
                figure = new Figure(mine);
                canvas.figure = figure;
                Settings.gameOver = figure.isCrossGround(); // Is there space for a new figure?
            } else {
                figure.stepDown();
            }
        }
    }


    void checkFilling() { // check filling rows
        int row = Settings.FIELD_HEIGHT - 1;
        int countFillRows = 0;
        while (row > 0) {
            int filled = 1;
            for (int col = 0; col < Settings.FIELD_WIDTH; col++){
                filled *= Integer.signum(mine[row][col]);
            }
            if (filled > 0) {
                countFillRows++;
                for (int i = row; i > 0; i--){
                    System.arraycopy(mine[i - 1], 0, mine[i], 0, Settings.FIELD_WIDTH);
                }
            } else{
                row--;
            }
        }
        if (countFillRows > 0) {
            Settings.gameScore += Settings.SCORES[countFillRows - 1];
            setTitle(Settings.TITLE_OF_PROGRAM + " : " + Settings.gameScore);
        }
    }
}
 