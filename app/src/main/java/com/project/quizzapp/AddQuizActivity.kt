package com.project.quizzapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddQuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quiz)

        val btnSaveQuiz = findViewById<Button>(R.id.btnSaveQuiz)

        btnSaveQuiz.setOnClickListener {
            var et_quizName = findViewById<EditText>(R.id.et_quizName)

            if(et_quizName.text.toString().isEmpty())
                Toast.makeText(this,"Enter quiz name", Toast.LENGTH_SHORT).show()
            else{
                var quiz = Quiz()
                quiz.quizName = et_quizName.text.toString()
                QuizzesActivity.dbHandler.addQuiz(this,quiz)
                clearEditTexts()
                et_quizName.requestFocus()


            }
        }

        val btnCancelAddingQuiz = findViewById<Button>(R.id.btnCancelAddingQuiz)

        btnCancelAddingQuiz.setOnClickListener {
            clearEditTexts()
            finish()
        }
    }

    fun clearEditTexts(){
        findViewById<EditText>(R.id.et_quizName).text.clear()

    }

    override fun onBackPressed() {
        finish()
    }
}