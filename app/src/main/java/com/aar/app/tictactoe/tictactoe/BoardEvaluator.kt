package com.aar.app.tictactoe.tictactoe

class BoardEvaluator {
    companion object {
        const val VAL_MAX = 100
        const val VAL_MIN = -100
        const val VAL_DRAW = 0

        @JvmStatic
        fun evaluate(board: Board, maxPlayer: Int, minPlayer: Int): Int {
            // horizontal
            for (i in 0..6 step 3) {
                if (board[i] == board[1 + i] && board[1 + i] == board[2 + i]) {
                    if (board[i] == maxPlayer) return VAL_MAX
                    else if (board[i] == minPlayer) return VAL_MIN
                }
            }

            // vertical
            for (i in 0..2) {
                if (board[i] == board[3 + i] && board[3 + i] == board[6 + i]) {
                    if (board[i] == maxPlayer) return VAL_MAX
                    else if (board[i] == minPlayer) return VAL_MIN
                }
            }

            // diagonal
            if (board[0] == board[4] && board[4] == board[8]) {
                if (board[0] == maxPlayer) return VAL_MAX
                else if (board[0] == minPlayer) return VAL_MIN
            }

            if (board[2] == board[4] && board[4] == board[6]) {
                if (board[2] == maxPlayer) return VAL_MAX
                else if (board[2] == minPlayer) return VAL_MIN
            }

            return VAL_DRAW
        }
    }
}