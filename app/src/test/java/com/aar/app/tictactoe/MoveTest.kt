package com.aar.app.tictactoe

import com.aar.app.tictactoe.game.Move
import org.junit.Assert.assertEquals
import org.junit.Test

class MoveTest {
    @Test
    fun validMoveTest() {
        val m = Move()
        val m2 = Move(0)
        val m3 = Move(5)

        assertEquals(false, m.isValid)
        assertEquals(true, m2.isValid)
        assertEquals(true, m3.isValid)
    }
}