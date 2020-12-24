package com.project.quizzapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class QuizAdapter(mCtx: Context, val quizzes: ArrayList<Quiz>) : RecyclerView.Adapter<QuizAdapter.ViewHolder>(){
    val mCtx = mCtx
    var id : Int = 0

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tv_quizName = itemView.findViewById<TextView>(R.id.tv_quizName)

        var tv_quizID = itemView.findViewById<TextView>(R.id.tv_quizID)

        val tv_questionCount = itemView.findViewById<TextView>(R.id.tv_questionCount)

        val btnConfigureQuiz = itemView.findViewById<Button>(R.id.btnConfigureQuiz)

        val fab_quizList = itemView.findViewById<FloatingActionButton>(R.id.fab_quizList)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lo_quizzes,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz: Quiz = quizzes[position]

        holder.tv_quizID.text = quiz.quizID.toString()
        holder.tv_quizName.text = quiz.quizName
        holder.tv_questionCount.text = QuizzesActivity.dbHandler.getNumberOfQuestionsInQuiz(quiz).toString()+" Q's"


        /*
        holder.btnConfigureQuiz.setOnClickListener{
            Toast.makeText(mCtx, "${quiz.quizID} clicked", Toast.LENGTH_SHORT).show()
            this.id = quiz.quizID

        }


         */




    }

    fun getQuizId(): Int{
        return id
    }
}

class QuestionAdapter(mCtx: Context, val questions: ArrayList<QuestionStructure>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>(){

    val mCtx = mCtx

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tv_question = itemView.findViewById<TextView>(R.id.tv_question)

        var tv_optionOne = itemView.findViewById<TextView>(R.id.tv_optionOne)

        val tv_optionTwo = itemView.findViewById<TextView>(R.id.tv_optionTwo)

        val tv_optionThree = itemView.findViewById<TextView>(R.id.tv_optionThree)

        val tv_optionFour = itemView.findViewById<TextView>(R.id.tv_optionFour)

        val tv_correctAnswer = itemView.findViewById<TextView>(R.id.tv_correctAnswer)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lo_questions,parent,false)
        return QuestionAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question: QuestionStructure = questions[position]

        holder.tv_question.text = question.question
        holder.tv_optionOne.text = question.optionOne
        holder.tv_optionTwo.text = question.optionTwo
        holder.tv_optionThree.text = question.optionThree
        holder.tv_optionFour.text = question.optionFour
        holder.tv_correctAnswer.text = "Option : "+question.correctAnswer.toString()



    }


}


