package kz.alishev.service

import kz.alishev.dto.TestDto
import kz.alishev.dto.TestType
import kz.alishev.model.Answer
import kz.alishev.model.Attempt
import kz.alishev.repository.AnswerRepository
import kz.alishev.repository.AttemptRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AnswerService(
    private val answerRepository: AnswerRepository,
    private val attemptRepository: AttemptRepository
) {

    fun <T> saveAnswer(attempt: Attempt, testDto: TestDto<T>): Answer {
        val answer = Answer()
        answer.score = testDto.score
        answer.attempt = attempt
        answer.testType = TestType.find(testDto.testType!!).value
        answer.response = testDto.answer.toString()
        answerRepository.save(answer)
        return answer
    }

}