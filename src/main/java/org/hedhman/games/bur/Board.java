package org.hedhman.games.bur;

import javax.swing.*;
import java.awt.*;

import static org.hedhman.games.bur.Main.BLOCKSIZE;

public class Board extends JPanel {

    private final int columns;
    private final int rows;
    boolean[][] playarea;
    private String message;
    private int collisionRow;
    private int collisionCol;

    public Board(int cols, int rows) {
        Dimension dim = new Dimension(cols * BLOCKSIZE, rows * BLOCKSIZE);
        setPreferredSize(dim);
        this.columns = cols;
        this.rows = rows;
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                Color color;
                if (playarea[c][r])
                    color = Color.BLACK;
                else
                    color = Color.WHITE;
                g.setColor(color);
                g.fillRect(c * BLOCKSIZE, r * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
            }
        }
        if (collisionCol != 0 || collisionRow != 0) {
            g.setColor(Color.RED);
            int x = collisionCol * BLOCKSIZE;
            int y = collisionRow * BLOCKSIZE;
            g.drawRect(x, y, BLOCKSIZE, BLOCKSIZE);
            g.drawLine(x, y, x+BLOCKSIZE, y+BLOCKSIZE);
            g.drawLine(x+BLOCKSIZE, y, x, y+BLOCKSIZE);
            collisionRow = 0;
            collisionCol = 0;
        }
        if (message != null) {
            drawTopBorder();
            Font font = Font.decode(Font.MONOSPACED);
            font = font.deriveFont(Font.PLAIN, 20);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(message, 100, BLOCKSIZE - 4);
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void clearPlayArea() {
        playarea = new boolean[columns][];
        for (int i = 0; i < columns; i++) {
            playarea[i] = new boolean[rows];
        }
        drawBorder();
    }

    void drawBorder() {
        drawTopBorder();
        drawBottomBorder();
        drawLeftBorder();
        drawRightBorder();
    }

    void drawTopBorder() {
        for (int i = 0; i < columns; i++)
            playarea[i][0] = true;
    }

    void drawBottomBorder() {
        for (int i = 0; i < columns; i++)
            playarea[i][rows - 1] = true;
    }

    void drawLeftBorder() {
        for (int i = 0; i < rows; i++)
            playarea[0][i] = true;
    }

    void drawRightBorder() {
        for (int i = 0; i < rows; i++)
            playarea[columns - 1][i] = true;
    }

    public void setMessage(String text) {
        message = text;
    }

    public void drawCollision(int posCol, int posRow) {
        collisionCol = posCol;
        collisionRow = posRow;
        repaint();
        sleep(1000);
    }
}
