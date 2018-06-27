package com.aar.app.tictactoe.tictactoe

class TicTacToeAi(val maxPlayer: Int, val minPlayer: Int) {

    fun findBestMove(board: Board, player: Int): Move = minimax(board, player)

    private fun minimax(board: Board, player: Int): Move {
        val score = BoardEvaluator.evaluate(board, player, player == maxPlayer)
        if (board.isFull || score == BoardEvaluator.VAL_MAX || score == BoardEvaluator.VAL_MIN) {
            return Move(score = score)
        }

        val moves = ArrayList<Move>()
        for (i in 0 until board.data.size) {
            if (board[i] != 0) continue
            board[i] = player
            val move = if (player == maxPlayer)
                minimax(board, minPlayer) else
                minimax(board, maxPlayer)
            board[i] = EMPTY_TOKEN
            moves.add(Move(i, move.score))
        }

        val bestMove = if (player == maxPlayer) {
            moves.maxBy { it.score }
        } else {
            moves.minBy { it.score }
        }
        return bestMove ?: Move()
    }
}