package com.project.quizzapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    /*
    This class MainActivity is the entry point of app.

     */
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        The onCreate() function loads the main screen using the function
        setContentView(R.layout.activity_main)

        Then btnStart is for starting the quiz and requires a string of at
        least one character otherwise gives a toast 'Please enter your name'

        After this it takes you to Quiz Questions Activity
        Add name to QuizQuestionsActivity intent
        Then load QuizQuestionsActivity intent, start that activity and
        finish the current main activity

         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Its deprecated but it still works
        //Toast.makeText(this, "Opened the app!", Toast.LENGTH_SHORT).show() // just for checking onCreate func

        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener {
            val et_enterName = findViewById<TextInputEditText>(R.id.et_enterName)

            if(et_enterName.text.toString().isEmpty())
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            else{
                var intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, et_enterName.text.toString())
                startActivity(intent)
                finish() //finish the current activity(MainActivity)
            }

        }

    }
}