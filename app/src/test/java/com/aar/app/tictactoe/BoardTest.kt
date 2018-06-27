package com.aar.app.tictactoe

import com.aar.app.tictactoe.tictactoe.Board
import com.aar.app.tictactoe.tictactoe.EMPTY_TOKEN
import org.junit.Assert.assertEquals
import org.junit.Test

class BoardTest {
    @Test
    fun fullBoardTeat() {
        val arr = arrayOf(
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
        ).toIntArray()
        val b = Board(arr)

        assertEquals(true, b.isFull)

        b.data[0] = EMPTY_TOKEN
        assertEquals(false, b.isFull)
    }
}