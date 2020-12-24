package com.project.quizzapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class QuizzesActivity : AppCompatActivity() {

    companion object{
        lateinit var dbHandler: DBHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes)

        dbHandler = DBHandler(this,null,null,1)



        viewQuizzes()

        val fab_quizList = findViewById<FloatingActionButton>(R.id.fab_quizList)
        fab_quizList.setOnClickListener{
            val i = Intent(this, AddQuizActivity::class.java)
            //Toast.makeText(this,"FAB clicked!!",Toast.LENGTH_SHORT).show()

            startActivity(i)

        }


    }

    fun viewQuizzes(){
        val quizzeslist = dbHandler.getQuizzes(this)
        val adapter = QuizAdapter(this, quizzeslist)
        val rv : RecyclerView = findViewById(R.id.rv_quizzes)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

    override fun onResume(){
        viewQuizzes()
        super.onResume()
    }

    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onIDClick(view: View){
        val tv_quizID = view.findViewById<TextView>(R.id.tv_quizID)
        //Toast.makeText(this,"${tv_quizID.text} IDDD",Toast.LENGTH_SHORT).show()
        var intent = Intent(this, AddingQuestionsInQuiz::class.java)
        intent.putExtra("quizID",tv_quizID.text.toString())
        startActivity(intent)

    }
    fun displayQuiz(mCtx: Context, id: Int){
        var i = Intent(this, DisplayQuiz::class.java)
        //Toast.makeText(this,"FAB clicked!!",Toast.LENGTH_SHORT).show()

        i.putExtra(Constants.USER_NAME, intent.getStringExtra(Constants.USER_NAME))
        i.putExtra(Constants.RANDOM, intent.getStringExtra(Constants.RANDOM))
        i.putExtra("id",id)
        startActivity(i)

    }
    fun configurebtn(view: View){
        
    }

}