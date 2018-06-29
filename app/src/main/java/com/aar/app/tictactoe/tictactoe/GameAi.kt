package com.aar.app.tictactoe.tictactoe

class GameAi(val maxPlayer: Int, val minPlayer: Int) {

    companion object {
        private const val VAL_WORST_MAX = BoardEvaluator.VAL_MIN * 10
        private const val VAL_WORST_MIN = BoardEvaluator.VAL_MAX * 10
    }

    fun findBestMove(board: Board, player: Int) = minimax(board, player, BoardEvaluator.VAL_MIN, BoardEvaluator.VAL_MAX)

    private fun minimax(board: Board, player: Int, alpha: Int, beta: Int): Move {
        val score = BoardEvaluator.evaluate(board, maxPlayer, minPlayer)
        if (board.isFull || score == BoardEvaluator.VAL_MAX || score == BoardEvaluator.VAL_MIN) {
            return Move(score = score)
        }

        var currAlpha = alpha
        var currBeta = beta
        var bestMove = Move(score = if (player == maxPlayer) VAL_WORST_MAX else VAL_WORST_MIN)
        for (i in 0 until board.data.size) {
            if (board[i] != EMPTY_TOKEN) continue

            board[i] = player
            val move = minimax(board, if (player == maxPlayer) minPlayer else maxPlayer, currAlpha, currBeta)
            board[i] = EMPTY_TOKEN

            if (player == maxPlayer && move.score > bestMove.score) {
                bestMove = Move(i, move.score)
                currAlpha = Math.max(currAlpha, bestMove.score)
            } else if (player == minPlayer && move.score < bestMove.score) {
                bestMove = Move(i, move.score)
                currBeta = Math.min(currBeta, bestMove.score)
            }

            if (currBeta <= currAlpha)
                break
        }

        return bestMove
    }
}