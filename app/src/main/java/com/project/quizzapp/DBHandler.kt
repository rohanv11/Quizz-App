package com.project.quizzapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.reflect.Array

class DBHandler(context : Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

    companion object{
        private val DATABASE_NAME = "MyQuizAppData.db"
        private val DATABASE_VERSION = 1

        val QUIZZES_TABLE_NAME = "Quizzes"
        val COLUMN_QUIZID = "quizid"
        val COLUMN_QUIZNAME = "quizname"


        val QUESTIONS_TABLE_NAME = "Questions"
        val COLUMN_QUESTIONID =  "questionid"
        val COLUMN_QUESTION = "question"
        val COLUMN_OPTIONONE = "optionone"
        val COLUMN_OPTIONTWO = "optiontwo"
        val COLUMN_OPTIONTHREE = "optionthree"
        val COLUMN_OPTIONFOUR = "optionfour"
        val COLUMN_CORRECT_ANSWER = "correctanswer"
        val COLUMN_QUIZ_ID = "quiz_id"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_QUIZZES_TABLE : String = (
                "CREATE TABLE $QUIZZES_TABLE_NAME(" +
                    "$COLUMN_QUIZID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_QUIZNAME TEXT" +
                        ")"
                )
        db?.execSQL(CREATE_QUIZZES_TABLE)

        val CREATE_QUESTIONS_TABLE : String = (
                "CREATE TABLE $QUESTIONS_TABLE_NAME(" +
                    "$COLUMN_QUESTIONID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_QUESTION TEXT," +
                    "$COLUMN_OPTIONONE TEXT," +
                    "$COLUMN_OPTIONTWO TEXT," +
                    "$COLUMN_OPTIONTHREE TEXT," +
                    "$COLUMN_OPTIONFOUR TEXT," +
                    "$COLUMN_CORRECT_ANSWER INT," +
                    "$COLUMN_QUIZ_ID INT," +
                    "FOREIGN KEY ($COLUMN_QUIZ_ID) REFERENCES $QUIZZES_TABLE_NAME($COLUMN_QUIZID)" +
                        ")"
                )
        db?.execSQL(CREATE_QUESTIONS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getQuizzes(mCtx: Context) : ArrayList<Quiz>{
        val query = "SELECT * FROM $QUIZZES_TABLE_NAME"
        val db: SQLiteDatabase = this.readableDatabase
        val cursor:Cursor = db.rawQuery(query,null)
        val quizzes = ArrayList<Quiz>()

        if(cursor.count == 0){
            Toast.makeText(mCtx,"No quizzes found!",Toast.LENGTH_SHORT).show()
        }
        else{
            while (cursor.moveToNext()){
                val quiz = Quiz()
                quiz.quizID = cursor.getInt(cursor.getColumnIndex(COLUMN_QUIZID))
                quiz.quizName= cursor.getString(cursor.getColumnIndex(COLUMN_QUIZNAME))
                quizzes.add(quiz)
            }
            Toast.makeText(mCtx,"${cursor.count} quizzes found!",Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return quizzes

    }
    fun addQuiz(mCtx: Context, quiz: Quiz): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_QUIZNAME, quiz.quizName)

        var result : Boolean = false

        try{
            db.insert(QUIZZES_TABLE_NAME,null,contentValues)
            Toast.makeText(mCtx,"Quiz added!",Toast.LENGTH_SHORT).show()
            result = true
        }
        catch(e: Exception){
            Log.e(ContentValues.TAG, "Error Updating")
        }
        db.close()
        return result
    }

    fun  getNumberOfQuestionsInQuiz(quiz: Quiz) : Int{
        val query = "SELECT $COLUMN_QUESTIONID FROM $QUESTIONS_TABLE_NAME " +
                "WHERE $COLUMN_QUIZ_ID=${quiz.quizID}"
        val db:SQLiteDatabase = this.writableDatabase
        var result : Boolean = false
        var count: Int =0
        try{
            val cursor = db.rawQuery(query, null)
            count = cursor.count
            result = true
        }
        catch (e: Exception){
            Log.e(ContentValues.TAG, "Error fetching number of questions")

        }
        db.close()
        return count
    }


    fun getQuestions(mCtx: Context, quizID:String) : ArrayList<QuestionStructure>{
        var quizID = quizID.toInt()
        
        val query = "SELECT * FROM $QUESTIONS_TABLE_NAME " +
               "WHERE $COLUMN_QUIZ_ID = $quizID"
        val db:SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        val questions = ArrayList<QuestionStructure>()

        //var cursor = db?.execSQL(query)
        if(cursor.count == 0){
            Toast.makeText(mCtx,"No questions found in the quiz!",Toast.LENGTH_SHORT).show()
        }
        else{
            while (cursor.moveToNext()){
                val question = QuestionStructure()
                question.questionID = cursor.getInt(cursor.getColumnIndex(COLUMN_QUESTIONID))
                question.question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION))
                question.optionOne = cursor.getString(cursor.getColumnIndex(COLUMN_OPTIONONE))
                question.optionTwo = cursor.getString(cursor.getColumnIndex(COLUMN_OPTIONTWO))
                question.optionThree = cursor.getString(cursor.getColumnIndex(COLUMN_OPTIONTHREE))
                question.optionFour = cursor.getString(cursor.getColumnIndex(COLUMN_OPTIONFOUR))
                question.correctAnswer = cursor.getString(cursor.getColumnIndex(
                    COLUMN_CORRECT_ANSWER)).toInt()

                questions.add(question)
            }
            Toast.makeText(mCtx,"${cursor.count} questions found!",Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return questions


    }

    fun addQuestion(mCtx: Context,question: QuestionStructure, quizID: Int) : Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_QUESTION, question.question)
        contentValues.put(COLUMN_OPTIONONE, question.optionOne)
        contentValues.put(COLUMN_OPTIONTWO, question.optionTwo)
        contentValues.put(COLUMN_OPTIONTHREE, question.optionThree)
        contentValues.put(COLUMN_OPTIONFOUR, question.optionFour)
        contentValues.put(COLUMN_CORRECT_ANSWER, question.correctAnswer)
        contentValues.put(COLUMN_QUIZ_ID, quizID)

        var result : Boolean = false

        try{
            db.insert(QUESTIONS_TABLE_NAME,null,contentValues)
            Toast.makeText(mCtx,"Question added!",Toast.LENGTH_SHORT).show()
            result = true
        }
        catch(e: Exception){
            Log.e(ContentValues.TAG, "Error adding question")
        }
        db.close()
        return result
    }
}