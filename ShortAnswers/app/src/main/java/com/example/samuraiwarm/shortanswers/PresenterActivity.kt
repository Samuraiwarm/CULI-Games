package com.example.samuraiwarm.shortanswers

class PresenterActivity {
    private var score: Int = 0
    private var questions = listOf("1+1=?","1+2=?","1+3=?")
    private var answers =  listOf("2","3","4")
    private var questionsIndex: Int = 0
    fun scoreIncrement(){
        score++
    }
    fun questionsIndexIncrement(){
        questionsIndex++
    }
}
