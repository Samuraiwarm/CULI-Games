package com.example.samuraiwarm.filltheword

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.inputmethodservice.Keyboard
import android.renderscript.ScriptGroup
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.R.attr.maxLength
import android.text.InputFilter




class MainActivity : AppCompatActivity() { //PresenterActivity{

    lateinit var questions : List<List<Char>>
    lateinit var answers : List<String>
    lateinit var chronometer: Chronometer
    var questionsIndex : Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        chronometer = findViewById(R.id.textview_resulttime)
        questions = listOf(listOf('s','_','_','_','e'),listOf('c','h','_','l','_'),listOf('m','e','_','e'),listOf('_','o','_','g','_'),listOf('_','_','_','_'))
        answers = listOf("hib","ua","m","dgo","doge") //"shibe","chula","meme","doggo","doge"
        val newQuestionNumber: String = (questionsIndex+1).toString()+"/"+questions.size
        //textview_question.setText(questions.get(questionsIndex))
        textview_questionNumber.setText(newQuestionNumber)

        var question = questions[questionsIndex].toCharArray()
        var answer: String = answers[questionsIndex].toUpperCase()
        textview_question.setText(question.joinToString("").toUpperCase())
        edittext.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(answer.length)))
        val passwordWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textview_question.visibility = View.VISIBLE
            }
            override fun afterTextChanged(s: Editable) {
                if(!(questionsIndex>=questions.size)) {
                    var inputLengthIndex = 0
                    if (edittext.length() == 0) {
                        textview_question.text = questions[questionsIndex].toCharArray().joinToString("").toUpperCase()
                    } else {
                        while (inputLengthIndex < edittext.length()) {
                            var questionOriginal = questions[questionsIndex].toCharArray()
                            var questionInput = questionOriginal
                            var inputIndex = 0
                            while (inputIndex < edittext.length()) {
                                var firstSpaceIndex: Int = questionOriginal.joinToString("").indexOf('_')
                                val holder = edittext.text.toString().toCharArray()[inputIndex]
                                questionInput[firstSpaceIndex] = holder
                                inputIndex++
                            }
                            inputLengthIndex++
                            textview_question.text = questionInput.joinToString("").toUpperCase()
                        }
                    }
                }
            }
        }
        /* Set Text Watcher listener */
        edittext.addTextChangedListener(passwordWatcher);
        chronometer.start()

        submit.setOnClickListener {
            val inputAnswer = edittext.text.toString().toUpperCase()
            if(!(questionsIndex>=questions.size)) {
                if(inputAnswer.equals(answer)) {
                    var score: Int = Integer.parseInt(score_value.text.toString())
                    score+=1
                    score_value.setText(score.toString())
                }
                questionsIndex++
                val newQuestionNumber: String = (questionsIndex+1).toString()+"/"+questions.size
                textview_questionNumber.setText(newQuestionNumber)
                if(!(questionsIndex>=questions.size)) {
                    question = questions[questionsIndex].toCharArray()
                    answer = answers[questionsIndex].toUpperCase()
                    textview_question.setText(question.joinToString("").toUpperCase())
                    edittext.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(answer.length)))
                }
                if(questionsIndex>=questions.size) {
                    answer = answers[0].toUpperCase()
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
            edittext.setText("")
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
            textview_question.setText(questions.get(0).joinToString("").toUpperCase())
            val newQuestionNumber: String = (questionsIndex+1).toString()+"/"+questions.size
            textview_questionNumber.setText(newQuestionNumber)
            edittext.setText("")
            edittext.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(answers[0].length)))
        }
    }
}
