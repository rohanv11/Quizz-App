package com.project.quizzapp

data class Question (
    /*
    Question class: Structure of one question.
    Each question consists of :
    ID (question no)
    The question itself
    The image related to the question
    Four questions
    Correct option number : 1,2,3,4
     */
    val id:Int,
    val question: String,
    val image: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int

)