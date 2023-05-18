package com.dueli.ngbuf.falo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dueli.ngbuf.falo.R
import com.dueli.ngbuf.falo.databinding.ActivityGameDuelBoardBinding

class GameDuelBoard : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGameDuelBoardBinding
    private var currentId = 0
    private var currentStr = ""
    private var selectArray: MutableList<Int> = mutableListOf(0, 0, 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDuelBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener(this)
        binding.submit.setOnClickListener(this)
        binding.custom1.close.setOnClickListener(this)
        binding.custom2.close.setOnClickListener(this)
        binding.custom3.close.setOnClickListener(this)
        binding.custom1.root.setOnClickListener(this)
        binding.custom2.root.setOnClickListener(this)
        binding.custom3.root.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        startGame()
    }

    private fun startGame() {
        val rand1 = rand()
        val rand2 = rand()
        val rand3 = rand()
        currentId = 0
        selectArray = mutableListOf(0, 0, 0)

        binding.img1.setImageResource(rand1)
        binding.img2.setImageResource(rand2)
        binding.img3.setImageResource(rand3)

        binding.custom1.img.setImageResource(0)
        binding.custom2.img.setImageResource(0)
        binding.custom3.img.setImageResource(0)

        currentStr = "${str(rand1)}${str(rand2)}${str(rand3)}"
    }

    private fun submit(){
        val val1 = selectArray[0]
        val val2 = selectArray[1]
        val val3 = selectArray[2]
        val nStr = "${str(val1)}${str(val2)}${str(val3)}"

        if (nStr == currentStr){
            Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.not_correct, Toast.LENGTH_SHORT).show()
        }
        startGame()
    }

    private fun step(){
        currentId++
        if (currentId > 2){
            currentId = 0
        }
    }

    private fun str(i: Int): String {
        return when (i) {
            R.drawable.img2 -> "J"
            R.drawable.img3 -> "K"
            R.drawable.img4 -> "O"
            else -> ""
        }
    }

    private fun rand(): Int {
        return listOf(R.drawable.img2, R.drawable.img3, R.drawable.img4).random()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.back -> finish()
            binding.custom1.close -> {
                binding.custom1.img.setImageResource(0)
                selectArray[0] = 0
                currentId = 0
            }
            binding.custom2.close -> {
                binding.custom2.img.setImageResource(0)
                currentId = 1
                selectArray[0] = 1
            }
            binding.custom3.close -> {
                binding.custom3.img.setImageResource(0)
                currentId = 2
                selectArray[0] = 2
            }
            binding.custom1.root -> currentId = 0
            binding.custom2.root -> currentId = 1
            binding.custom3.root -> currentId = 2
            binding.btn1 -> {
                when (currentId) {
                    0 -> {
                        binding.custom1.img.setImageResource(R.drawable.img2)
                        selectArray[currentId] = R.drawable.img2
                    }
                    1 -> {
                        binding.custom2.img.setImageResource(R.drawable.img2)
                        selectArray[currentId] = R.drawable.img2
                    }
                    else -> {
                        binding.custom3.img.setImageResource(R.drawable.img2)
                        selectArray[currentId] = R.drawable.img2
                    }
                }
                step()
            }
            binding.btn2 -> {
                when (currentId) {
                    0 -> {
                        binding.custom1.img.setImageResource(R.drawable.img3)
                        selectArray[currentId] = R.drawable.img3
                    }
                    1 -> {
                        binding.custom2.img.setImageResource(R.drawable.img3)
                        selectArray[currentId] = R.drawable.img3
                    }
                    else -> {
                        binding.custom3.img.setImageResource(R.drawable.img3)
                        selectArray[currentId] = R.drawable.img3
                    }
                }
                step()
            }
            binding.btn3 -> {
                when (currentId) {
                    0 -> {
                        binding.custom1.img.setImageResource(R.drawable.img4)
                        selectArray[currentId] = R.drawable.img4
                    }
                    1 -> {
                        binding.custom2.img.setImageResource(R.drawable.img4)
                        selectArray[currentId] = R.drawable.img4
                    }
                    else -> {
                        binding.custom3.img.setImageResource(R.drawable.img4)
                        selectArray[currentId] = R.drawable.img4
                    }
                }
                step()
            }
            binding.submit -> submit()
        }
    }
}