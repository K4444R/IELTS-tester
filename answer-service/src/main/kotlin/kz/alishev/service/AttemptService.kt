package kz.alishev.service

import kz.alishev.dto.AnswerDto
import kz.alishev.dto.ErrorResponse
import kz.alishev.model.Attempt
import kz.alishev.repository.AttemptRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AttemptService(
    private val attemptRepository: AttemptRepository,
    private val answerService: AnswerService,
    private val resultService: ResultService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun createAttempt(attempt: Attempt): ResponseEntity<Any> {
        try {
            val attemptExists = attemptRepository.findByUserId(attempt.userId!!)
            if (attemptExists != null) {
                if (attemptExists.finishedAt == null) {
                    return ResponseEntity.badRequest().body("Found unfinished attempt")
                }
            }
            attempt.startedAt = Instant.now()
            attemptRepository.save(attempt)
            return ResponseEntity.ok().body("Success")
        } catch (e: Exception) {
            return ResponseEntity(ErrorResponse("error", e.message.toString()), HttpStatus.BAD_REQUEST)
        }
    }

    fun <T> finishAttempt(attemptId: Long, answerDto: AnswerDto<T>): ResponseEntity<Any> {
        try {
            val response = HashMap<String, Any>()
            val attempt = attemptRepository.findById(attemptId).get()
            if (attempt.finishedAt != null) {
                return ResponseEntity.badRequest().body("Already finished attempt")
            }
            attempt.finishedAt = Instant.now()
            attemptRepository.save(attempt)
            for (test in answerDto.answer!!) {
                response[test.testType!!] = answerService.saveAnswer(attempt, test)
            }
            resultService.saveResult(attemptId)
            return ResponseEntity.ok().body(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity(ErrorResponse("error", e.message.toString()), HttpStatus.BAD_REQUEST)
        }
    }

}