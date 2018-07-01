package com.aar.app.tictactoe.game

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.aar.app.tictactoe.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PLAY_MODE_KEY = "play_mode"
        const val PLAY_MODE_VS_HUMAN = 1
        const val PLAY_MODE_VS_COMPUTER = 2
    }

    private lateinit var viewModel: TicTacToeGame
    private val board = Array<TextView?>(9, { null })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initViewModel()
        initView()
    }

    private fun initView() {
        buttonReset.setOnClickListener { startGame() }
        button0.setOnClickListener { viewModel.makeMove(0, 0) }
        button1.setOnClickListener { viewModel.makeMove(0, 1) }
        button2.setOnClickListener { viewModel.makeMove(0, 2) }
        button3.setOnClickListener { viewModel.makeMove(1, 0) }
        button4.setOnClickListener { viewModel.makeMove(1, 1) }
        button5.setOnClickListener { viewModel.makeMove(1, 2) }
        button6.setOnClickListener { viewModel.makeMove(2, 0) }
        button7.setOnClickListener { viewModel.makeMove(2, 1) }
        button8.setOnClickListener { viewModel.makeMove(2, 2) }
        board[0] = button0
        board[1] = button1
        board[2] = button2
        board[3] = button3
        board[4] = button4
        board[5] = button5
        board[6] = button6
        board[7] = button7
        board[8] = button8
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TicTacToeGame::class.java)
        viewModel.board.observe(this, Observer {
            button0.text = getToken(it?.data?.get(0))
            button1.text = getToken(it?.data?.get(1))
            button2.text = getToken(it?.data?.get(2))
            button3.text = getToken(it?.data?.get(3))
            button4.text = getToken(it?.data?.get(4))
            button5.text = getToken(it?.data?.get(5))
            button6.text = getToken(it?.data?.get(6))
            button7.text = getToken(it?.data?.get(7))
            button8.text = getToken(it?.data?.get(8))
        })
        viewModel.boardCellChanged.observe(this, Observer {
            it?.let {
                val index = it.row * 3 + it.col
                board[index]?.text = getToken(it.player)
            }
        })
        viewModel.gameState.observe(this, Observer {
            if (it == TicTacToeGame.GameState.PLAYER_X_WIN) {
                Toast.makeText(this, "X Win", Toast.LENGTH_SHORT).show()
                textPlayerX.text = "X Win"
                textPlayerO.text = "Lose"
            } else if (it == TicTacToeGame.GameState.PLAYER_O_WIN) {
                Toast.makeText(this, "O Win", Toast.LENGTH_SHORT).show()
                textPlayerX.text = "Lose"
                textPlayerO.text = "Y Win"
            } else if (it == TicTacToeGame.GameState.DRAW) {
                Toast.makeText(this, "Game Draw", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.currentPlayer.observe(this, Observer {
            it?.let {
                if (it == TicTacToeGame.PLAYER_X) {
                    textPlayerX.text = "Player X (current player)"
                    textPlayerO.text = "Player O"
                } else {
                    textPlayerX.text = "Player X"
                    textPlayerO.text = "Player O (current player)"
                }
            }
        })
        startGame()
    }

    private fun getToken(id: Int?) =
            if (id == TicTacToeGame.PLAYER_X) "X" else if (id == TicTacToeGame.PLAYER_O) "O" else ""

    fun getMode(): Int {
        return intent.getIntExtra(EXTRA_PLAY_MODE_KEY, PLAY_MODE_VS_HUMAN)
    }

    fun startGame() {
        val mode = getMode()
        if (mode == PLAY_MODE_VS_HUMAN) {
            viewModel.playWithHuman()
        } else if (mode == PLAY_MODE_VS_COMPUTER) {
            viewModel.playWithComputer()
        }
    }
}
