package com.aar.app.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aar.app.tictactoe.tictactoe.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val HUMAN = 1
        private val COMPUTER = 2
    }

    private var currentPlayer = COMPUTER
    private val board = Board()
    private val ai = GameAi(COMPUTER, HUMAN)
    private val btnGrid: Array<Button?> = Array(9, { null })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButtonGrid()
        runGame()
    }

    private fun runGame() {
        if (currentPlayer == COMPUTER) {
            currentPlayer = HUMAN
        } else {
            val move = ai.findBestMove(board, COMPUTER)
            if (move.isValid) {
                board[move.index] = COMPUTER
                btnGrid[move.index]?.text = "X"
                currentPlayer = COMPUTER
                runGame()
            }
        }
    }

    private fun initButtonGrid() {
        btnGrid[0] = button00
        btnGrid[1] = button01
        btnGrid[2] = button02

        btnGrid[3] = button10
        btnGrid[4] = button11
        btnGrid[5] = button12

        btnGrid[6] = button20
        btnGrid[7] = button21
        btnGrid[8] = button22

        for (i in 0 until btnGrid.size) {
            btnGrid[i]?.setOnClickListener { v ->
                (v as Button).text = "O"
                board[i] = HUMAN
                runGame()
            }
        }
    }
}
