package com.dueli.ngbuf.falo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dueli.ngbuf.falo.R
import com.dueli.ngbuf.falo.databinding.ActivitySettingsDuelBoardBinding
import com.dueli.ngbuf.falo.utils.SoundService
import com.dueli.ngbuf.falo.utils.storege.Bucket

class SettingsDuelBoard : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingsDuelBoardBinding
    private var isMusic = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsDuelBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener(this)
        isMusic = Bucket.isMusic
        update()
        binding.btnMusic.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.back -> finish()
            binding.btnMusic -> {
                if (isMusic) {
                    stopService(Intent(this, SoundService::class.java))
                    Bucket.isMusic = false
                } else {
                    startService(Intent(this, SoundService::class.java))
                    Bucket.isMusic = true
                }
                isMusic = !isMusic
                update()
            }
            else -> {}
        }
    }

    private fun update(){
        if (isMusic){
            binding.btnMusic.text = getString(R.string.off)
        } else {
            binding.btnMusic.text = getString(R.string.on)
        }
    }
}