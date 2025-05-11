package kz.alishev.controller

import kz.alishev.model.Answer
import kz.alishev.repository.AnswerRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/answer")
class AnswerController(
    private val answerRepository: AnswerRepository
) {

    @GetMapping
    fun getAllByAttemptId(@RequestParam attemptId: Long): List<Answer> {
        return answerRepository.findAllByAttemptId(attemptId)
    }

}