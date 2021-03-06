package com.aar.app.tictactoe;

import com.aar.app.tictactoe.game.Board;
import com.aar.app.tictactoe.game.Move;
import com.aar.app.tictactoe.game.GameAi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinimaxTest {

    private static final int PLAYER_MAX = 1;
    private static final int PLAYER_MIN = 2;

    @Test
    public void test1() {
        GameAi m = new GameAi(PLAYER_MAX, PLAYER_MIN);

        int b[] = {
                1, 0, 2,
                2, 0, 0,
                2, 1, 1
        };
        Board board = new Board(b);
        Move move = m.findBestMove(board, PLAYER_MAX);

        assertEquals(4, move.getIndex());
    }

    @Test
    public void test2() {
        GameAi m = new GameAi(PLAYER_MAX, PLAYER_MIN);

        int b[] = {
                0, 0, 0,
                0, 0, 2,
                0, 1, 2
        };
        Board board = new Board(b);
        Move move = m.findBestMove(board, PLAYER_MAX);

        assertEquals(0, move.getIndex());
    }

    @Test
    public void test3() {
        GameAi m = new GameAi(PLAYER_MAX, PLAYER_MIN);

        int b[] = {
                0, 0, 0,
                0, 1, 2,
                0, 1, 2
        };
        Board board = new Board(b);
        Move move = m.findBestMove(board, PLAYER_MAX);

        assertEquals(1, move.getIndex());
    }

    @Test
    public void test4() {
        GameAi m = new GameAi(PLAYER_MAX, PLAYER_MIN);

        int b[] = {
                0, 0, 0,
                2, 1, 0,
                2, 0, 1
        };
        Board board = new Board(b);
        Move move = m.findBestMove(board, PLAYER_MAX);

        assertEquals(0, move.getIndex());
    }

    @Test
    public void test5() {
        GameAi m = new GameAi(PLAYER_MAX, PLAYER_MIN);

        int b[] = {
                2, 1, 0,
                2, 0, 0,
                1, 0, 2
        };
        Board board = new Board(b);
        Move move = m.findBestMove(board, PLAYER_MAX);

        assertEquals(4, move.getIndex());
    }

    @Test
    public void test6() {
        GameAi m = new GameAi(PLAYER_MAX, PLAYER_MIN);

        int b[] = {
                2, 1, 1,
                1, 2, 2,
                2, 1, 1
        };
        Move move = m.findBestMove(new Board(b), PLAYER_MAX);

        assertEquals(-1, move.getIndex());
    }

    @Test
    public void test7() {
        GameAi m = new GameAi(PLAYER_MAX, PLAYER_MIN);

        int b[] = {
                0, 0, 0,
                0, 0, 0,
                2, 0, 0
        };
        Board board = new Board(b);
        Move move = m.findBestMove(board, PLAYER_MAX);

        assertEquals(4, move.getIndex());
    }
}
