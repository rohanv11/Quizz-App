package com.project.quizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    /*
    The primary function of this class is to load a intent to show the
    result(score) of user and username.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        This functions first loads the result_activity xml.
        Then fetches correctAnswers, totalQuestions, username from previous intent(QuizQuestions)
        and adds to respective textviews in this intent

        Then there is a finish button which takes you back to the main activity intent
        (the homepage of the quiz app).
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Its deprecated but it still works


        val username = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)

        val tv_name = findViewById<TextView>(R.id.tv_name)
        tv_name.text = username

        val tv_score = findViewById<TextView>(R.id.tv_score)
        tv_score.text = "Your score is $correctAnswers/$totalQuestions"

        val btn_finish = findViewById<Button>(R.id.btn_finish)
        btn_finish.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java)) //use back
            /*
            When you don't add finish(), and press back on after going to main activity
            from result activity you can still navigate back to result activity screen
             */
        }

//        btn_finish.setOnClickListener {
//            startActivity(Intent(this,MainActivity::class.java))
//            finish() //use back
//            /*
//            Whereas if you write finish(), it 'destroys/deletes/closes' the result activity once you
//            reach main activity screen and you 'can't' access it again
//            */
//        }

    }
}