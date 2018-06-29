package com.aar.app.tictactoe.tictactoe

class GameAi(val maxPlayer: Int, val minPlayer: Int) {

    companion object {
        private const val VAL_WORST_MAX = BoardEvaluator.VAL_MIN * 10
        private const val VAL_WORST_MIN = BoardEvaluator.VAL_MAX * 10
    }

    fun findBestMove(board: Board, player: Int): Move = minimax(board, player)

    private fun minimax(board: Board, player: Int): Move {
        val score = BoardEvaluator.evaluate(board, maxPlayer, minPlayer)
        if (board.isFull || score == BoardEvaluator.VAL_MAX || score == BoardEvaluator.VAL_MIN) {
            return Move(score = score)
        }

        var alpha = BoardEvaluator.VAL_MIN
        var beta = BoardEvaluator.VAL_MAX
        var bestMove = Move(score = if (player == maxPlayer) VAL_WORST_MAX else VAL_WORST_MIN)
        for (i in 0 until board.data.size) {
            if (board[i] != 0) continue

            board[i] = player
            val move = minimax(board, if (player == maxPlayer) minPlayer else maxPlayer)
            board[i] = EMPTY_TOKEN

            if (player == maxPlayer && move.score > bestMove.score) {
                bestMove = Move(i, move.score)
                alpha = Math.max(alpha, bestMove.score)
            } else if (player == minPlayer && move.score < bestMove.score) {
                bestMove = Move(i, move.score)
                beta = Math.min(beta, bestMove.score)
            }

            if (beta <= alpha)
                break
        }

        return bestMove
    }
}