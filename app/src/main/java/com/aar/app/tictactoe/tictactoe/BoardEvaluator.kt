package com.aar.app.tictactoe.tictactoe

class BoardEvaluator {

    companion object {
        const val VAL_MAX = 100
        const val VAL_MIN = -100
        const val VAL_DRAW = 0

        @JvmStatic
        fun evaluate(board: Board, player: Int, isMaxPlayer: Boolean): Int {
            return if (
                    // horizontal
                    board[0] == player && board[0] == board[1] && board[1] == board[2] ||
                    board[3] == player && board[3] == board[4] && board[4] == board[5] ||
                    board[6] == player && board[6] == board[7] && board[7] == board[8] ||

                    // vertical
                    board[0] == player && board[0] == board[3] && board[3] == board[6] ||
                    board[1] == player && board[1] == board[4] && board[4] == board[7] ||
                    board[2] == player && board[2] == board[5] && board[5] == board[8] ||

                    // diagonal
                    board[0] == player && board[0] == board[4] && board[4] == board[8] ||
                    board[2] == player && board[2] == board[4] && board[4] == board[6]
            ) {
                if (isMaxPlayer) VAL_MAX else VAL_MIN
            } else {
                VAL_DRAW
            }
        }
    }

}