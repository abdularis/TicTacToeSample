package com.aar.app.tictactoe.tictactoe

data class Move(
        val index: Int = -1,
        val score: Int = 0
) {
    val isValid inline get() = index >= 0
}