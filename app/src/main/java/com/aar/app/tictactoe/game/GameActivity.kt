package com.aar.app.tictactoe.game

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aar.app.tictactoe.R
import com.aar.app.tictactoe.custom.GridLayout
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.partial_game_board.*

class GameActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PLAY_MODE_KEY = "play_mode"
        const val PLAY_MODE_VS_HUMAN = 1
        const val PLAY_MODE_VS_COMPUTER = 2
    }

    private lateinit var viewModel: TicTacToeGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initViewModel()
        initView()
    }

    private fun initView() {
        buttonReset.setOnClickListener { startGame() }
        board.setOnCellTouchListener { view, row, col ->
            viewModel.makeMove(row, col)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TicTacToeGame::class.java)
        viewModel.board.observe(this, Observer {
            board.removeAllViews()
        })
        viewModel.boardCellChanged.observe(this, Observer {
            it?.let { createView(it.row, it.col, it.player) }
        })
        viewModel.gameState.observe(this, Observer {
            when (it) {
                TicTacToeGame.GameState.PLAYER_X_WIN -> {
                    Toast.makeText(this, "X Win", Toast.LENGTH_SHORT).show()
                    textPlayerX.text = "X Win"
                    textPlayerO.text = "Lose"
                }
                TicTacToeGame.GameState.PLAYER_O_WIN -> {
                    Toast.makeText(this, "O Win", Toast.LENGTH_SHORT).show()
                    textPlayerX.text = "Lose"
                    textPlayerO.text = "Y Win"
                }
                TicTacToeGame.GameState.DRAW -> Toast.makeText(this, "Game Draw", Toast.LENGTH_SHORT).show()
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

    private fun getMode(): Int {
        return intent.getIntExtra(EXTRA_PLAY_MODE_KEY, PLAY_MODE_VS_HUMAN)
    }

    private fun startGame() {
        val mode = getMode()
        if (mode == PLAY_MODE_VS_HUMAN) {
            viewModel.playWithHuman()
        } else if (mode == PLAY_MODE_VS_COMPUTER) {
            viewModel.playWithComputer()
        }
    }

    private fun getColorToken(token: Int) =
            if (token == TicTacToeGame.PLAYER_X) resources.getColor(R.color.colorAccent)
            else resources.getColor(R.color.colorPrimary)

    private fun createView(row: Int, col: Int, token: Int) {
        val v = View(this)
        v.setBackgroundColor(getColorToken(token))
        val layoutParams = GridLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, row, col)
        layoutParams.setMargins(16, 16, 16, 16)
        board.addView(v, layoutParams)
    }
}
