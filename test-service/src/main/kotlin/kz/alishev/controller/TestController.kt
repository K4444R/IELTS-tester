package kz.alishev.controller

import kz.alishev.dto.QuestionResponse
import kz.alishev.model.QuestionType
import kz.alishev.service.TestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController(
    private val testService: TestService
) {

    @GetMapping("/questions")
    fun getQuestions(
        @RequestParam type: QuestionType,
        @RequestParam(defaultValue = "1") count: Int
    ): ResponseEntity<List<QuestionResponse>> {
        val questions = testService.getRandomQuestions(type, count)
            .map { QuestionResponse(it.id!!, it.text, it.audioUrl) }
        return ResponseEntity.ok(questions)
    }
}
