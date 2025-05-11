package kz.alishev.service

import kz.alishev.model.Result
import kz.alishev.repository.AnswerRepository
import kz.alishev.repository.AttemptRepository
import kz.alishev.repository.ResultRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ResultService(
    private val answerRepository: AnswerRepository,
    private val attemptRepository: AttemptRepository,
    private val resultRepository: ResultRepository
) {

    fun saveResult(attemptId: Long) {
        val answers = answerRepository.findAllByAttemptId(attemptId)
        var avgScore = 0.0f
        for (answer in answers) {
            avgScore += answer.score!!
        }
        avgScore /= 4

        val result = Result()
        result.attempt = attemptRepository.findById(attemptId).get()
        result.createdAt = Instant.now()
        result.totalScore = avgScore
        resultRepository.save(result)
    }

}