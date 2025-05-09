package kz.alishev.service.impl

import kz.alishev.model.Question
import kz.alishev.model.QuestionType
import kz.alishev.repository.QuestionRepository
import kz.alishev.service.TestService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class TestServiceImpl(
    private val questionRepository: QuestionRepository
) : TestService {
    override fun getRandomQuestions(type: QuestionType, count: Int): List<Question> {
        val all = questionRepository.findByType(type)
        return all.shuffled(Random(System.currentTimeMillis())).take(count)
    }
}