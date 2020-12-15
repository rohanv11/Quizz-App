package com.project.quizzapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddQuestionActivity : AppCompatActivity() {
    private var count :Int = 0
    private var continueChecking: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)


        val btnSaveQuestion = findViewById<Button>(R.id.btnSaveQuestion)
        btnSaveQuestion.setOnClickListener {
            this.count = 0
            this.continueChecking = true

            val et_enterQuestion  = findViewById<EditText>(R.id.et_enterQuestion)
            if(continueChecking){
                editTextValidation(et_enterQuestion)
            }

            val et_enterOptionOne  = findViewById<EditText>(R.id.et_enterOptionOne)
            if(continueChecking){
                editTextValidation(et_enterOptionOne)
            }

            val et_enterOptionTwo  = findViewById<EditText>(R.id.et_enterOptionTwo)
            if(continueChecking){
                editTextValidation(et_enterOptionTwo)
            }


            val et_enterOptionThree  = findViewById<EditText>(R.id.et_enterOptionThree)
            if(continueChecking){
                editTextValidation(et_enterOptionThree)

            }

            val et_enterOptionFour  = findViewById<EditText>(R.id.et_enterOptionFour)

            if(continueChecking){
                editTextValidation(et_enterOptionFour)

            }

            val et_enterCorrectOption  = findViewById<EditText>(R.id.et_enterCorrectOption)
            if(continueChecking){
                correctOptionNumberValidation(et_enterCorrectOption)
            }


            if(count==6){
                var question = QuestionStructure()

                question.question = et_enterQuestion.text.toString()
                question.optionOne = et_enterOptionOne.text.toString()
                question.optionTwo = et_enterOptionTwo.text.toString()
                question.optionThree = et_enterOptionThree.text.toString()
                question.optionFour = et_enterOptionFour.text.toString()
                question.correctAnswer = et_enterCorrectOption.text.toString().toInt()


                var added = QuizzesActivity.dbHandler.addQuestion(this,question, intent.getStringExtra("quizID").toString().toInt())
                clearEditTexts()
                this.count=0
                et_enterQuestion.requestFocus()
            }



        }

        val btnCancelAddingQuestion = findViewById<Button>(R.id.btnCancelAddingQuestion)

        btnCancelAddingQuestion.setOnClickListener {
            clearEditTexts()
            finish()
        }

        //Toast.makeText(this,"reached add question",Toast.LENGTH_SHORT).show()
    }

    fun editTextValidation(et: EditText){
        if(et.text.toString().isEmpty()){
            et.requestFocus()
            Toast.makeText(this,"${et.hint} is a required field",Toast.LENGTH_SHORT).show()
            this.continueChecking = false
        }
        else{
            this.count+=1
        }

    }

    fun correctOptionNumberValidation(et: EditText){
        if(!et.text.toString().isEmpty()){
            try{
                var optionNumber=et.text.toString().toInt()
                if(optionNumber in 1..4){
                    this.count += 1
                }
                else{
                    Toast.makeText(this,"please enter a value from 1 to 4!", Toast.LENGTH_SHORT).show()
                    et.requestFocus()
                    this.continueChecking = false
                }
            }
            catch (e:Exception){
                Toast.makeText(this,"please enter a value from 1 to 4!", Toast.LENGTH_SHORT).show()
                et.requestFocus()
                this.continueChecking = false
            }

        }
        else{
            et.requestFocus()
            Toast.makeText(this,"${et.hint} is a required field",Toast.LENGTH_SHORT).show()
            this.continueChecking = false
        }
    }

    fun clearEditTexts(){

        findViewById<EditText>(R.id.et_enterQuestion).text.clear()
        findViewById<EditText>(R.id.et_enterOptionOne).text.clear()
        findViewById<EditText>(R.id.et_enterOptionTwo).text.clear()
        findViewById<EditText>(R.id.et_enterOptionThree).text.clear()
        findViewById<EditText>(R.id.et_enterOptionFour).text.clear()
        findViewById<EditText>(R.id.et_enterCorrectOption).text.clear()

    }
}