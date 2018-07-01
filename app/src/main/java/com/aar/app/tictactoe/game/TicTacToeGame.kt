package com.aar.app.tictactoe.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aar.app.tictactoe.SingleLiveEvent

class TicTacToeGame: ViewModel() {

    companion object {
        const val PLAYER_X = 1
        const val PLAYER_O = 2
    }

    enum class Mode {
        VS_HUMAN,
        VS_COMPUTER
    }

    enum class GameState {
        IDLE,
        PLAYING,
        PLAYER_X_WIN,
        PLAYER_O_WIN,
        DRAW,
        COMPUTER_THINKING
    }

    data class BoardCell(val row: Int, val col: Int, val player: Int)

    val board = MutableLiveData<Board>()
    val boardCellChanged = SingleLiveEvent<BoardCell>()
    val currentPlayer = MutableLiveData<Int>()
    val gameState = MutableLiveData<GameState>()

    init {
        gameState.value = GameState.IDLE
    }

    private val _board = Board()
    private var _currentPlayer = PLAYER_X
    private val ai = GameAi(maxPlayer = PLAYER_X, minPlayer = PLAYER_O)
    private var mode = Mode.VS_HUMAN

    fun playWithHuman() {
        mode = Mode.VS_HUMAN
        _board.clear()
        _currentPlayer = PLAYER_X

        board.value = _board
        currentPlayer.value = _currentPlayer
        gameState.value = GameState.PLAYING
    }

    fun playWithComputer() {
        mode = Mode.VS_COMPUTER
        _board.clear()
        _currentPlayer = PLAYER_O

        board.value = _board
        currentPlayer.value = _currentPlayer
        gameState.value = GameState.PLAYING
    }

    fun makeMove(row: Int, col: Int) {
        if (gameState.value != GameState.PLAYING) return
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) return
        if (!_board.emptyAt(row, col)) return

        if (mode == Mode.VS_HUMAN) {
            doPlayWithHuman(row, col)
        } else {
            doPlayWithComputer(row, col)
        }
    }

    private fun doPlayWithComputer(row: Int, col: Int) {
        doPlayWithHuman(row, col)

        val move = ai.findBestMove(_board, PLAYER_X)
        if (move.isValid) {
            _board[move.index] = PLAYER_X
            boardCellChanged.value = BoardCell(move.index / 3, move.index % 3, _currentPlayer)
            if (!evaluateBoardState()) {
                _currentPlayer = getNextPlayer()
                currentPlayer.value = _currentPlayer
            }
        }
    }

    private fun doPlayWithHuman(row: Int, col: Int) {
        _board[row, col] = _currentPlayer
        boardCellChanged.value = BoardCell(row, col, _currentPlayer)

        if (!evaluateBoardState()) {
            _currentPlayer = getNextPlayer()
            currentPlayer.value = _currentPlayer
        }
    }

    private fun evaluateBoardState(): Boolean {
        val score = BoardEvaluator.evaluate(_board, PLAYER_X, PLAYER_O)
        if (score == BoardEvaluator.VAL_MAX || score == BoardEvaluator.VAL_MIN) {
            if (_currentPlayer == PLAYER_X) {
                gameState.value = GameState.PLAYER_X_WIN
            } else {
                gameState.value = GameState.PLAYER_O_WIN
            }
            return true
        }

        if (score == BoardEvaluator.VAL_DRAW && _board.isFull) {
            gameState.value = GameState.DRAW
            return true
        }
        return false
    }

    private inline fun getNextPlayer() = if (_currentPlayer == PLAYER_X) PLAYER_O else PLAYER_X
}