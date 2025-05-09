package kz.alishev.service

import kz.alishev.model.Question
import kz.alishev.model.QuestionType

interface TestService {
    fun getRandomQuestions(type: QuestionType, count: Int): List<Question>
}