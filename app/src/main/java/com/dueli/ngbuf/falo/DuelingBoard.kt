package com.dueli.ngbuf.falo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dueli.ngbuf.falo.activity.GameDuelBoard
import com.dueli.ngbuf.falo.activity.SettingsDuelBoard
import com.dueli.ngbuf.falo.databinding.ActivityDuelingBoardBinding
import com.dueli.ngbuf.falo.router.Router

class DuelingBoard : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDuelingBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuelingBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.play.setOnClickListener(this)
        binding.settings.setOnClickListener(this)
        binding.exit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.exit -> finish()
            binding.play -> Router.routeOnPage(this, GameDuelBoard::class.java)
            binding.settings -> Router.routeOnPage(this, SettingsDuelBoard::class.java)
        }
    }
}