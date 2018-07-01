package com.aar.app.tictactoe.game

data class Move(
        val index: Int = -1,
        val score: Int = 0
) {
    val isValid inline get() = index >= 0
}