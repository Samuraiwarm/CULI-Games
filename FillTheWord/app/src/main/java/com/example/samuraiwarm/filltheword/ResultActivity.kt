package com.example.samuraiwarm.filltheword

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result)
        val scoreDisplay = intent.getStringExtra("score")+"/"+intent.getStringExtra("fullscore")
        textview_resultscore.text = scoreDisplay
        val timeDisplay = intent.getStringExtra("time")
        textview_resulttimedisplay.text = timeDisplay
        retry.setOnClickListener{
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}