package com.project.quizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddingQuestionsInQuiz : AppCompatActivity() {
    var quiz_ID: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_questions_in_quiz)



        //Toast.makeText(this,"Reached adding questions $quizID",Toast.LENGTH_SHORT).show()
        //viewQuestionsInQuiz()
        quiz_ID = intent.getStringExtra("quizID").toString()
        viewQuestions(quiz_ID)

        val fab_questionsListInQuiz = findViewById<FloatingActionButton>(R.id.fab_questionsListInQuiz)
        fab_questionsListInQuiz.setOnClickListener{
            val i = Intent(this, AddQuestionActivity::class.java)
            i.putExtra("quizID",quiz_ID)
            startActivity(i)
        }
    }

    fun viewQuestions(quizID: String){
        val questionsList = QuizzesActivity.dbHandler.getQuestions(this,quizID)
        val adapter = QuestionAdapter(this, questionsList)
        val rv : RecyclerView = findViewById(R.id.rv_questions)
        rv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter = adapter



    }

    override fun onResume(){
        viewQuestions(this.quiz_ID.toString())
        super.onResume()
    }

}