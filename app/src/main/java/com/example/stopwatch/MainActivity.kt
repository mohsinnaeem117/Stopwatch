package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvTime: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnHold: Button

    private var startTime: Long = 0
    private var timeInSeconds: Long = 0
    private var isRunning: Boolean = false
    private val handler = Handler()
    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                timeInSeconds = (System.currentTimeMillis() - startTime) / 1000
                updateDisplay()
                handler.postDelayed(this, 1000)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvTime = findViewById(R.id.tvTime)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnHold = findViewById(R.id.btnHold)

        btnStart.setOnClickListener { startStopwatch() }
        btnStop.setOnClickListener { stopStopwatch() }
        btnHold.setOnClickListener { holdStopwatch() }
    }

    private fun startStopwatch() {
        if (!isRunning) {
            startTime = System.currentTimeMillis()
            handler.post(updateTimeRunnable)
            isRunning = true
        }
    }

    private fun stopStopwatch() {
        if (isRunning) {
            handler.removeCallbacks(updateTimeRunnable)
            isRunning = false
        }
    }

    private fun holdStopwatch() {
        if (isRunning) {
            stopStopwatch()
            // Optionally, save the held time or display it
        }
    }

    private fun updateDisplay() {
        val seconds = (timeInSeconds % 60).toString().padStart(2, '0')
        val minutes = ((timeInSeconds / 60) % 60).toString().padStart(2, '0')
        val hours = (timeInSeconds / 3600).toString().padStart(2, '0')
        tvTime.text = "$hours:$minutes:$seconds"
    } 
}