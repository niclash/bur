package org.hedhman.games.bur;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.LinkedBlockingDeque;

public class Main extends JFrame {

    static final int BLOCKSIZE = 32;
    LinkedBlockingDeque<Character> inputs = new LinkedBlockingDeque<>(10);
    private int rightPoints;
    private int leftPoints;

    public static void main(String[] args) {
        new Main().run();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void run() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int cols = 40;
        int rows = 25;
        Board board = new Board(cols, rows);
        add(board);
        pack();
        setVisible(true);
        addKeyListener(new GameKeyListener());
        int playing = 0;
        while( playing == 0) {
            for (int i = 0; i < 11; i++) {
                Round round = new Round(board, cols, rows, inputs);
                boolean[] result = round.play();
                if (result[0]) {
                    leftPoints++;
                }
                if (result[1]) {
                    rightPoints++;
                }
            }
            playing = JOptionPane.showOptionDialog(this, " Left : " + leftPoints + "\nRight : " + rightPoints + " \n\nPlay again?", "Result", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, null, null);
        }
    }


    private class GameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            inputs.add(e.getKeyChar());
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
