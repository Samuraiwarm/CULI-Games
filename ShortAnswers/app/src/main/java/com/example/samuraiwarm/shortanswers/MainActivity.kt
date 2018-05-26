package com.example.samuraiwarm.shortanswers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() { //PresenterActivity{

    lateinit var questions : List<String>
    lateinit var answers : List<String>
    lateinit var chronometer: Chronometer
    var questionsIndex : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chronometer = findViewById(R.id.textview_resulttime)
        questions = listOf("1+1=?","1+2=?","1+3=?")
        answers = listOf("2","3","4")
        val newQuestionNumber: String = (questionsIndex+1).toString()+"/"+questions.size
        textview_question.setText(questions.get(questionsIndex))
        textview_questionNumber.setText(newQuestionNumber)
        chronometer.start()
        submit.setOnClickListener {
            val inputAnswer: String? = input?.text.toString()
            if(!(questionsIndex>=questions.size)) {
                if(inputAnswer.equals(answers.get(questionsIndex))) {
                    var score: Int = Integer.parseInt(score_value.text.toString())
                    score+=1
                    score_value.setText(score.toString())
                }
                questionsIndex++
//                val newQuestionNumber: String = (questionsIndex+1).toString()+"/"+questions.size
                textview_questionNumber.setText(newQuestionNumber)
                if(!(questionsIndex>=questions.size)) {
                    textview_question.setText(questions.get(questionsIndex))
                }
                if(questionsIndex>=questions.size) {
                    textview_questionNumber.setText("DONE!")
                    textview_question.setText("DONE!")
                    submit.setText("RESULT")
                    chronometer.stop()
                }
            } else {
                var intent = Intent(this,ResultActivity::class.java)
                intent.putExtra("score",score_value.text.toString())
                intent.putExtra("fullscore",questions.size.toString())
                intent.putExtra("time",chronometer.text.toString())
                startActivityForResult(intent,0)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            questionsIndex = 0
            score_value.setText("0")
            submit.setText("SUBMIT")
            val chronometer : Chronometer = findViewById(R.id.textview_resulttime)
            chronometer.setBase(SystemClock.elapsedRealtime())
            chronometer.start()
            textview_question.setText(questions.get(questionsIndex))
            val newQuestionNumber: String = (questionsIndex+1).toString()+"/"+questions.size
            textview_questionNumber.setText(newQuestionNumber)
        }
    }
}
