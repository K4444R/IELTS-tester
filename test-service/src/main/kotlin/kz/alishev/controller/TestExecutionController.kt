package kz.alishev.controller

import kz.alishev.dto.*
import kz.alishev.model.QuestionType
import kz.alishev.service.TestExecutionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test-execution")
class TestExecutionController(
    private val testExecutionService: TestExecutionService
) {
    @PostMapping("/start")
    fun startTest(@RequestParam userId: String): ResponseEntity<Map<String, Long>> {
        val attemptId = testExecutionService.startTest(userId)
        return ResponseEntity.ok(mapOf("attemptId" to attemptId))
    }

    @GetMapping("/questions/{type}")
    fun getQuestions(
        @PathVariable type: QuestionType,
        @RequestParam(defaultValue = "1") count: Int
    ): ResponseEntity<List<QuestionResponse>> {
        val questions = testExecutionService.getQuestionsForTest(type, count)
        return ResponseEntity.ok(questions)
    }

    @PostMapping("/submit/{attemptId}")
    fun <T> submitAnswers(
        @PathVariable attemptId: Long,
        @RequestBody answerDto: AnswerDto<T>
    ): ResponseEntity<Any> {
        return testExecutionService.submitAnswers(attemptId, answerDto.answer ?: emptyList())
    }

    @PostMapping("/submit-text/{attemptId}")
    fun submitTextAnswers(
        @PathVariable attemptId: Long,
        @RequestBody answerDto: AnswerDto<String>
    ): ResponseEntity<Any> {
        return testExecutionService.submitAnswers(attemptId, answerDto.answer ?: emptyList())
    }

    @GetMapping("/results/{attemptId}")
    fun getResults(@PathVariable attemptId: Long): ResponseEntity<List<AnswerResponse>> {
        val results = testExecutionService.getTestResults(attemptId)
        return ResponseEntity.ok(results)
    }

    @GetMapping("/statistics/{userId}")
    fun getUserStatistics(@PathVariable userId: String): ResponseEntity<StatisticResponse> {
        val statistics = testExecutionService.getUserStatistics(userId)
        return ResponseEntity.ok(statistics)
    }

    @PostMapping("/complete-test")
    fun completeFullTest(@RequestBody request: CompleteTestRequest): ResponseEntity<TestResultResponse> {
        val result = testExecutionService.completeFullTest(
            userId = request.userId,
            readingAnswer = request.readingAnswer,
            listeningAnswer = request.listeningAnswer,
            writingAnswer = request.writingAnswer,
            speakingAnswer = request.speakingAnswer
        )
        return ResponseEntity.ok(result)
    }
}