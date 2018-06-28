package com.aar.app.tictactoe;

import com.aar.app.tictactoe.tictactoe.Board;
import com.aar.app.tictactoe.tictactoe.BoardEvaluator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BoardEvaluatorTest {

    private static final int PLAYER_MAX = 1;
    private static final int PLAYER_MIN = 2;

    @Test
    public void evaluateEmpty() {
        int b[] = {
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
        };

        Board gameBoard = new Board(b);
        assertEquals(BoardEvaluator.VAL_DRAW, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));
    }

    @Test
    public void evaluateFullWithDraw() {
        int x = PLAYER_MAX;
        int y = PLAYER_MIN;
        int b[] = {
                x, y, y,
                y, x, x,
                y, x, y
        };

        Board gameBoard = new Board(b);
        assertEquals(BoardEvaluator.VAL_DRAW, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));
    }

    @Test
    public void evaluateHorizontalWin() {
        Board gameBoard = createBoardHorizontalWin(0, PLAYER_MAX);
        assertEquals(BoardEvaluator.VAL_MAX, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));

        gameBoard = createBoardHorizontalWin(2, PLAYER_MIN);
        assertEquals(BoardEvaluator.VAL_MIN, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));
    }

    @Test
    public void evaluateVerticalWin() {
        Board gameBoard = createBoardVerticalWin(0, PLAYER_MIN);
        assertEquals(BoardEvaluator.VAL_MIN, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));

        gameBoard = createBoardVerticalWin(1, PLAYER_MAX);
        assertEquals(BoardEvaluator.VAL_MAX, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));
    }

    @Test
    public void evaluateDiagonalWin() {
        int x = PLAYER_MAX;
        int b[] = {
                x, 0, 0,
                0, x, 0,
                0, 0, x
        };

        Board gameBoard = new Board(b);
        assertEquals(BoardEvaluator.VAL_MAX, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));


        int o = PLAYER_MIN;
        int b1[] = {
                0, 0, o,
                0, o, 0,
                o, 0, 0
        };

        gameBoard = new Board(b1);
        assertEquals(BoardEvaluator.VAL_MIN, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));
    }

    @Test
    public void evaluateNoWin() {
        int x = PLAYER_MAX;
        int b[] = {
                0, 0, 0,
                0, x, 0,
                0, 0, x
        };

        Board gameBoard = new Board(b);
        assertEquals(BoardEvaluator.VAL_DRAW, BoardEvaluator.evaluate(gameBoard, PLAYER_MAX, PLAYER_MIN));
    }

    private Board createBoardHorizontalWin(int row, int player) {
        int offset = 0;
        if (row == 1) offset = 3;
        else if (row == 2) offset = 6;

        Board b = new Board();
        b.set(offset , player);
        b.set(offset + 1, player);
        b.set(offset + 2, player);
        return b;
    }

    private Board createBoardVerticalWin(int col, int player) {
        Board b = new Board();
        b.set(col, player);
        b.set(3 + col, player);
        b.set(6 + col, player);
        return b;
    }
}