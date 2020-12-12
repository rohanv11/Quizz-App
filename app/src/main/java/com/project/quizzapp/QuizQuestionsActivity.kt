package com.project.quizzapp

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.lang.Exception
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import java.util.Collections.shuffle

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    /*
    This class displays the quiz questions one by one and keeps track of Users Inputs.

    Variables and their function:
    mCurrentPosition : Denotes current question number
    mQuestionsList : Array List of Questions fetched from Constants
    mSelectedOptionPosition : Denotes option selected 0(not chosen),1,2,3,4
    mCorrectAnswers : keep track of users correct answers (score)
    valueSelected : boolean value to know check user has selected the option or not
    mUsername : username obtained from previous Intent(Main Activity)

     */


    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var valueSelected = false
    private var mUsername : String? = null
    private var randomQuizStatus : Boolean = false
    private var shuffledArrayIndices : IntArray? = null

    //private var answered = false
    //not reqd, used for earlier implementation
    val questionsList = Constants.getQuestions()
    var score = 0
    var selected_option = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        First this functions fetches the username from previous intent and then fetches
        questions from Constants class and sets the very first question

        It also acknowledges the 4 textviews and 1 button and adds OnClickListener to manipulate them later.

         */

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        mUsername = intent.getStringExtra(Constants.USER_NAME)

        randomQuizStatus = intent.getStringExtra(Constants.RANDOM).toBoolean()

        mQuestionsList = Constants.getQuestions()

        shuffleQuestions(mQuestionsList!!.size, randomQuizStatus)

        setQuestion()

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)


        }

    override fun onBackPressed() {
        //super.onBackPressed()
        //Toast.makeText(this, "back pressed!!!!!!", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert!")
        builder.setMessage("Do you want to exit the quiz and return to mainscreen?")
        val dialogClickListener = DialogInterface.OnClickListener{
            _,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    //Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show()

                    startActivity(
                            Intent(this, MainActivity::class.java)
                                    .putExtra(Constants.USER_NAME, mUsername),
                    )
                    finish()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    //Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show()
                }
                /* Button neutral is not really required here
                DialogInterface.BUTTON_NEUTRAL -> {
                    //Toast.makeText(this, "OKKK", Toast.LENGTH_SHORT).show()
                }

                 */
            }
        }
        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("NO",dialogClickListener)
        //builder.setNeutralButton("CANCEL",dialogClickListener)

        builder.create().show()

    }

    private fun shuffleQuestions(size: Int, enabler: Boolean){
        shuffledArrayIndices = IntArray(size) {i->i+1} //size = 5 Array = {1,2,3,4,5}
        if(enabler){
            shuffledArrayIndices!!.shuffle()
        }
    }

    private fun getShuffledQuestion(id: Int) : Question{
        /*
        id begins with 0...
        shuffledArrayIndices has values which begin with 1...(thats why -1)
         */
        return mQuestionsList!![ shuffledArrayIndices!![id] - 1 ]

    }


    private fun setQuestion(){
        /*
        This function sets all the question related data on the screen:
        Question
        Image data
        Progress bar
        options etc
         */

        //val question = mQuestionsList!![mCurrentPosition-1]
        val question = getShuffledQuestion(mCurrentPosition-1)

        defaultOptionsView()
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        if(mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit.text = "FINISH"
        }else{
            btnSubmit.text = "SUBMIT"
        }

        val progressBar =findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = mCurrentPosition

        val tv_progress = findViewById<TextView>(R.id.tv_progress)
        tv_progress.text = (mCurrentPosition).toString() + "/" + progressBar.max

        val tv_question = findViewById<TextView>(R.id.tv_question)
        //tv_question.text = question.id.toString() + ". " + question.question
        tv_question.text = (mCurrentPosition).toString() + ". " + question.question

        val iv_image = findViewById<ImageView>(R.id.iv_image)
        iv_image.setImageResource(question.image)

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        tv_option_one.text = question.optionOne

        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        tv_option_two.text = question.optionTwo

        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        tv_option_three.text = question.optionThree

        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        tv_option_four.text = question.optionFour




    }

    private fun defaultOptionsView(){
        /*
        This function is used to make the background of all the options default.
        It is used :
        if a user selects a option then its background + textstyle changes,
        if the same user decides to change the option then all the other options ui must be default,
        that's where this function comes into use.
         */
        val options = ArrayList<TextView>()
        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        options.add(0,tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        options.add(1,tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        options.add(2,tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        options.add(3,tv_option_four)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"  ))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.default_option_border_background
            )

        }

    }


    override fun onClick(v: View?) {
        /*
        This is quite a big function.
        This is onClick listener which responds whenever a user selects an option or clicks submit button
        It uses when structure which is similar to switch case in Java

        If one of the text view is selected its background is changed and its text is made bold.

        If submit button is selected multiple procedures are taken into consideration
        for eg.
        If more questions available, go to next question.
        If last question, then take to result screen.
        If last question submitted then change submit text to "QUIZ COMPLETED! GET RESULT!"
        If option not selected and user presses on submit, make a toast "please choose one option"
        If option of one question is submitted, then lock all text views and only keep "GO TO NEXT OPTION"
        button clickable etc.

         */
        when(v?.id){
                R.id.tv_option_one ->{
                    //var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
                    //tv_option_one.isClickable = false
                    selectedOptionView(findViewById<TextView>(R.id.tv_option_one),1)
                }
                R.id.tv_option_two ->{
                    selectedOptionView(findViewById<TextView>(R.id.tv_option_two),2)
                }
                R.id.tv_option_three ->{
                    selectedOptionView(findViewById<TextView>(R.id.tv_option_three),3)
                }
                R.id.tv_option_four ->{
                    selectedOptionView(findViewById<TextView>(R.id.tv_option_four),4)
                }
                R.id.btnSubmit ->{
                    if(mSelectedOptionPosition == 0 && !valueSelected){
                        if(mCurrentPosition <= mQuestionsList!!.size) {
                            Toast.makeText(this,"please choose one option",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"QUIZ DONE!",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else if(mSelectedOptionPosition == 0){
                        mCurrentPosition++
                        lockAllOptions(false)

                        when{
                            mCurrentPosition <= mQuestionsList!!.size -> {
                                setQuestion()
                            }else -> {
                                    //Toast.makeText(this,
                                    //         "You have successfully completed the quiz",
                                    //        Toast.LENGTH_LONG).show()
                                    //lockAllOptions(true)
                                    val intent = Intent(this, ResultActivity::class.java)
                                    intent.putExtra(Constants.USER_NAME, mUsername)
                                    intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                                    intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                                    startActivity(intent)
                                    finish()

                            }
                        }
                        valueSelected = false
                    }
                    else {
                        try{
                            //val question = mQuestionsList?.get(mCurrentPosition-1)
                            val question = getShuffledQuestion(mCurrentPosition-1)
                            if(question!!.correctAnswer != mSelectedOptionPosition){
                                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_background)
                            }
                            else{
                                mCorrectAnswers++
                            }
                            answerView(question.correctAnswer, R.drawable.correct_option_border_background)


                            val btnSubmit = findViewById<Button>(R.id.btnSubmit)
                            if(mCurrentPosition == mQuestionsList!!.size) {
                                btnSubmit.text = "QUIZ COMPLETED! GET RESULT!"
                            }else{
                                btnSubmit.text = "GO TO NEXT QUESTION"
                            }

                            lockAllOptions(true)
                            mSelectedOptionPosition = 0
                            valueSelected = true
                        }
                        catch (ex: Exception){
                            ex.printStackTrace()
                        }

                    }


                }
        }
    }

    private fun lockAllOptions(enabler: Boolean){
        /*
        This option simply locks all options once user has selected one option and pressed on submit.

        The function works two ways either to lock all options or unlock all options
        That is achieved by passing a boolean value of the enabler variable.
         */
        val options = ArrayList<TextView>()
        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        options.add(0,tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        options.add(1,tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        options.add(2,tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        options.add(3,tv_option_four)


        if (enabler){
            for (option in options){
                option.isClickable = false
            }
        }
        else {
            for (option in options) {
                option.isClickable = true
            }
        }

    }




    private fun answerView(answer: Int, drawableView: Int){
        /*
        This function changes the option background.
        if the user got the correct answer, it becomes green.
        if the user got the wrong option, it becomes red and the correct one becomes green
         */
        when(answer){
                1 -> {
                    val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
                    changeTextColorAndTypeface(tv_option_one)
                    tv_option_one.background = ContextCompat.getDrawable(
                            this,
                            drawableView)
                }
                2 -> {
                    val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
                    changeTextColorAndTypeface(tv_option_two)
                    tv_option_two.background = ContextCompat.getDrawable(
                            this,
                            drawableView)
                }
                3 -> {
                    val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
                    changeTextColorAndTypeface(tv_option_three)
                    tv_option_three.background = ContextCompat.getDrawable(
                            this,
                            drawableView)
                }
                4 -> {
                    val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
                    changeTextColorAndTypeface(tv_option_four)
                    tv_option_four.background = ContextCompat.getDrawable(
                            this,
                            drawableView)
                }

        }
    }

    private fun changeTextColorAndTypeface(tv: TextView){
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber : Int){
        /*
        This function changes the background of the selected option.
        So user has a clear understanding of which option is selected by him/her/other

        *first it defaults all the options
         */
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNumber

        changeTextColorAndTypeface(tv)
        tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_background_selected
        )

    }
/*
    don't bother the code below, its a previous buggy verison
 */
//======================================Earlier Implementation======================================//
//==================================================================================================//
    fun forEachQuestion(id: Int) {

        //Log.i("Questions :", "${questionsList.size}") // to check if questions fetched properly

        //var count = 1
        var tv_question: TextView
        var iv_image: ImageView

        var progressBar: ProgressBar
        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 10

        var tv_progress: TextView
        var tv_option_one: TextView
        var tv_option_two: TextView
        var tv_option_three: TextView
        var tv_option_four: TextView


        // setting up values for the very first question

        tv_question = findViewById<TextView>(R.id.tv_question)
        tv_question.text = questionsList[id].id.toString() + ". " + questionsList[id].question

        iv_image = findViewById<ImageView>(R.id.iv_image)
        iv_image.setImageResource(questionsList[id].image)

        progressBar.progress = id+1

        tv_progress = findViewById<TextView>(R.id.tv_progress)
        tv_progress.text = (id+1).toString() + "/" + questionsList.size.toString()

        tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        tv_option_one.text = questionsList[id].optionOne

        tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        tv_option_two.text = questionsList[id].optionTwo

        tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        tv_option_three.text = questionsList[id].optionThree

        tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        tv_option_four.text = questionsList[id].optionFour

        tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        tv_option_one.text = questionsList[id].optionOne


    }


    fun onSubmit(view: View){
        if(!(selected_option == 0)){
            val progressBar= findViewById<ProgressBar>(R.id.progressBar)
            val next_q = progressBar.progress
            val answer = questionsList[next_q-1].correctAnswer
            if(selected_option == answer){
                score++
            }



            if(selected_option == 1){
                var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
                tv_option_one.setBackgroundColor(R.drawable.default_option_border_background)
            }
            else if(selected_option == 2){

            }
            else if(selected_option == 3){

            }
            else{

            }

            selected_option = 0
            forEachQuestion(next_q)


        }
        else{
            Toast.makeText(this,"Please select one option",Toast.LENGTH_SHORT).show()
        }


    }

    fun onOptionOneClick(view:View){
        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        selected_option = 1
        tv_option_one.setBackgroundColor(R.drawable.default_option_border_background_selected)


        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        tv_option_two.setBackgroundColor(R.drawable.default_option_border_background)
        tv_option_three.setBackgroundColor(R.drawable.default_option_border_background)
        tv_option_four.setBackgroundColor(R.drawable.default_option_border_background)


    }

    fun onOptionTwoClick(view:View){
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        selected_option = 2
        tv_option_two.setBackgroundColor(R.drawable.default_option_border_background_selected)

        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)

        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        //tv_option_one.setBackgroundColor(R.drawable.default_option_border_background)

        //tv_option_three.setBackgroundColor(R.drawable.default_option_border_background)
        //tv_option_four.setBackgroundColor(R.drawable.default_option_border_background)
    }

    fun onOptionThreeClick(view:View){
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        selected_option = 3
        tv_option_three.setBackgroundColor(R.drawable.default_option_border_background_selected)

        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)

        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        tv_option_one.setBackgroundColor(R.drawable.default_option_border_background)
        tv_option_two.setBackgroundColor(R.drawable.default_option_border_background)

        tv_option_four.setBackgroundColor(R.drawable.default_option_border_background)
    }

    fun onOptionFourClick(view:View){
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        selected_option = 4
        tv_option_four.setBackgroundColor(R.drawable.default_option_border_background_selected)

        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)

        tv_option_one.setBackgroundColor(R.drawable.default_option_border_background)
        tv_option_two.setBackgroundColor(R.drawable.default_option_border_background)
        tv_option_three.setBackgroundColor(R.drawable.default_option_border_background)

    }


}
