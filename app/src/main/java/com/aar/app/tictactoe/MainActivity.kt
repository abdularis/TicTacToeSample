package com.aar.app.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aar.app.tictactoe.game.GameActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonVsHuman.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(GameActivity.EXTRA_PLAY_MODE_KEY, GameActivity.PLAY_MODE_VS_HUMAN)
            startActivity(intent)
        }

        buttonVsComputer.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(GameActivity.EXTRA_PLAY_MODE_KEY, GameActivity.PLAY_MODE_VS_COMPUTER)
            startActivity(intent)
        }
    }
}
