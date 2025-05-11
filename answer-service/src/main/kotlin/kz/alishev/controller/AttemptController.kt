package kz.alishev.controller

import kz.alishev.dto.AnswerDto
import kz.alishev.model.Answer
import kz.alishev.model.Attempt
import kz.alishev.repository.AttemptRepository
import kz.alishev.service.AttemptService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/attempt")
class AttemptController(
    val attemptService: AttemptService
) {

    @PostMapping
    fun createAttempt(@RequestBody attempt: Attempt): ResponseEntity<Any> {
        return attemptService.createAttempt(attempt)
    }

    @PatchMapping("/finish/{attemptId}")
    fun <T> finishAttempt(@PathVariable attemptId: Long, @RequestBody answerDto: AnswerDto<T>): ResponseEntity<Any> {
        return attemptService.finishAttempt(attemptId, answerDto)
    }

}