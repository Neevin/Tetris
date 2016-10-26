import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


class GameTetris extends JFrame {


    final String TITLE_OF_PROGRAM = "Tetris";
    final int BLOCK_SIZE = 25; // size of one block
    final int ARC_RADIUS = 6;
    final int FIELD_WIDTH = 10; // size game field in block
    final int FIELD_HEIGHT = 18;
    final int START_LOCATION = 180;
    final int FIELD_DX = 7;
    final int FIELD_DY = 26;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    final int SHOW_DELAY = 400;
    final int[][][] SHAPES = {
            {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}, {4, 0x00f0f0}}, // I
            {{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}, {4, 0xf0f000}}, // O
            {{1,0,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x0000f0}}, // J
            {{0,0,1,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf0a000}}, // L
            {{0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x00f000}}, // S
            {{1,1,1,0}, {0,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xa000f0}}, // T
            {{1,1,0,0}, {0,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf00000}}  // Z
    };
    final int[] SCORES = {100, 300, 700, 1500};
    int gameScore = 0;
    int[][] mine = new int[FIELD_HEIGHT + 1][FIELD_WIDTH]; // mine/glass
    JFrame frame;
    Canvas canvas = new Canvas();
    Random random = new Random();
    Figure figure = new Figure();
    boolean gameOver = false;
    final int[][] GAME_OVER_MSG = {
            {0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},
            {1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
            {1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
            {1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
            {1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
            {0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}};


    public static void main(String[] args) {
        new GameTetris().go();
    }


    GameTetris() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        canvas.setBackground(Color.black); // define the background color
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    if (e.getKeyCode() == DOWN) figure.drop();
                    if (e.getKeyCode() == UP) figure.rotate();
                    if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT) figure.move(e.getKeyCode());
                }
                canvas.repaint();
            }
        });
        add(BorderLayout.CENTER, canvas);
        setVisible(true);
        Arrays.fill(mine[FIELD_HEIGHT], 1);
    }


    void go() {
        while (!gameOver) {
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (Exception e) { e.printStackTrace(); }
            canvas.repaint();
            checkFilling();
            if (figure.isTouchGround()) {
                figure.leaveOnTheGround();
                figure = new Figure();
                gameOver = figure.isCrossGround(); // Is there space for a new figure?
            } else
                figure.stepDown();
        }
    }


    void checkFilling() { // check filling rows
        int row = FIELD_HEIGHT - 1;
        int countFillRows = 0;
        while (row > 0) {
            int filled = 1;
            for (int col = 0; col < FIELD_WIDTH; col++)
                filled *= Integer.signum(mine[row][col]);
            if (filled > 0) {
                countFillRows++;
                for (int i = row; i > 0; i--) System.arraycopy(mine[i-1], 0, mine[i], 0, FIELD_WIDTH);
            } else
                row--;
        }
        if (countFillRows > 0) {
            gameScore += SCORES[countFillRows - 1];
            setTitle(TITLE_OF_PROGRAM + " : " + gameScore);
        }
    }
 