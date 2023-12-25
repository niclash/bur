package org.hedhman.games.bur;

import java.util.concurrent.BlockingDeque;

public class Round {

    private long nextFrame;
    private boolean running;

    private int rightDirRow;
    private int rightDirCol;
    private int rightPosRow;
    private int rightPosCol;
    private boolean rightCollided;

    private int leftDirCol;
    private int leftDirRow;
    private int leftPosRow;
    private int leftPosCol;
    private boolean leftCollided;
    private Board board;
    private final int cols;
    private final int rows;
    private BlockingDeque<Character> inputs;

    Round(Board board, int cols, int rows, BlockingDeque<Character> inputs) {
        this.board = board;
        this.cols = cols;
        this.rows = rows;
        this.inputs = inputs;
    }

    boolean[] play() {
        board.clearPlayArea();
        rightDirRow = 0;
        rightDirCol = -1;
        leftDirRow = 0;
        leftDirCol = 1;
        rightPosRow = rows / 2;
        leftPosRow = rows / 2;
        leftPosCol = 3;
        rightPosCol = cols - 4;

        running = true;
        while (running) {
            while (System.currentTimeMillis() < nextFrame) ;
            nextFrame = System.currentTimeMillis() + 100;
            while (inputs.size() > 0) {
                Character key = inputs.pollFirst();
                switch (key) {
                    case 'a' -> {
                        leftDirRow = 0;
                        leftDirCol = -1;
                    }
                    case 'x' -> {
                        leftDirRow = 1;
                        leftDirCol = 0;
                    }
                    case 'w' -> {
                        leftDirRow = -1;
                        leftDirCol = 0;
                    }
                    case 'd' -> {
                        leftDirRow = 0;
                        leftDirCol = 1;
                    }
                    case '4' -> {
                        rightDirRow = 0;
                        rightDirCol = -1;
                    }
                    case '2' -> {
                        rightDirRow = 1;
                        rightDirCol = 0;
                    }
                    case '8' -> {
                        rightDirRow = -1;
                        rightDirCol = 0;
                    }
                    case '6' -> {
                        rightDirRow = 0;
                        rightDirCol = 1;
                    }
                    default -> System.out.println(key);
                }
            }
            updateMovement();
            detectCollision();
            if (rightCollided || leftCollided)
                break;
            placeNewBlock();
            board.repaint();
        }
        String text;
        boolean[] result = new boolean[2];
        if (rightCollided && leftCollided)
            text = "DRAW";
        else if (rightCollided) {
            text = "Left Won";
            result[0] = true;
        }
        else if (leftCollided) {
            text = "Right Won";
            result[1] = true;
        }
        else
            text = "Internal Error?";
        board.setMessage(text);
        board.repaint();
        return result;
    }

    private void detectCollision() {
        if (board.playarea[leftPosCol][leftPosRow]) {
            leftCollided = true;
            board.drawCollision(leftPosCol,leftPosRow);
        }
        if (board.playarea[rightPosCol][rightPosRow]) {
            rightCollided = true;
            board.drawCollision(rightPosCol, rightPosRow);
        }
    }

    private void placeNewBlock() {
        board.playarea[leftPosCol][leftPosRow] = true;
        board.playarea[rightPosCol][rightPosRow] = true;
    }

    private void updateMovement() {
        leftPosRow = leftPosRow + leftDirRow;
        leftPosCol = leftPosCol + leftDirCol;
        rightPosRow = rightPosRow + rightDirRow;
        rightPosCol = rightPosCol + rightDirCol;
    }

}
