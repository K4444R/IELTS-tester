package kz.alishev.service

import kz.alishev.client.AnswerServiceClient
import kz.alishev.dto.*
import kz.alishev.model.QuestionType
import org.springframework.stereotype.Service
import org.springframework.http.ResponseEntity
import java.util.*

@Service
class TestExecutionService(
    private val testService: TestService,
    private val answerServiceClient: AnswerServiceClient
) {
    fun startTest(userId: String): Long {
        val attemptRequest = AttemptRequest(userId)
        val response = answerServiceClient.createAttempt(attemptRequest)
        return (response.body as Map<*, *>)["attemptId"] as Long
    }

    fun getQuestionsForTest(type: QuestionType, count: Int = 1): List<QuestionResponse> {
        return testService.getRandomQuestions(type, count)
            .map { QuestionResponse(it.id!!, it.text, it.audioUrl) }
    }

    fun <T> submitAnswers(attemptId: Long, answers: List<TestDto<T>>): ResponseEntity<Any> {
        val answerDto = AnswerDto<T>()
        answerDto.answer = answers

        return answerServiceClient.finishAttempt(attemptId, answerDto)
    }

    fun getTestResults(attemptId: Long): List<AnswerResponse> {
        return answerServiceClient.getAllAnswersByAttemptId(attemptId)
    }

    fun getUserStatistics(userId: String): StatisticResponse {
        return answerServiceClient.getStatisticsByUserId(userId)
    }

    fun completeFullTest(userId: String,
                         readingAnswer: String,
                         listeningAnswer: String,
                         writingAnswer: String,
                         speakingAnswer: String): TestResultResponse {

        val attemptId = startTest(userId)
        val answers = mutableListOf<TestDto<String>>()

        val readingTestDto = TestDto<String>().apply {
            testType = "reading"
            answer = readingAnswer
            score = calculateScore(QuestionType.READING, readingAnswer)
        }

        val listeningTestDto = TestDto<String>().apply {
            testType = "listening"
            answer = listeningAnswer
            score = calculateScore(QuestionType.LISTENING, listeningAnswer)
        }

        val writingTestDto = TestDto<String>().apply {
            testType = "writing"
            answer = writingAnswer
            score = calculateScore(QuestionType.WRITING, writingAnswer)
        }

        val speakingTestDto = TestDto<String>().apply {
            testType = "speaking"
            answer = speakingAnswer
            score = calculateScore(QuestionType.SPEAKING, speakingAnswer)
        }

        answers.add(readingTestDto)
        answers.add(listeningTestDto)
        answers.add(writingTestDto)
        answers.add(speakingTestDto)

        val result = submitAnswers(attemptId, answers)

        val testResults = getTestResults(attemptId)

        return TestResultResponse(
            attemptId = attemptId,
            userId = userId,
            results = testResults,
            totalScore = testResults.map { it.score ?: 0f }.average().toFloat()
        )
    }

    private fun calculateScore(type: QuestionType, answer: String): Float {

        return when (type) {
            QuestionType.READING -> answer.length.coerceAtMost(100) / 100f * 10
            QuestionType.LISTENING -> answer.length.coerceAtMost(100) / 100f * 10
            QuestionType.WRITING -> answer.length.coerceAtMost(200) / 200f * 10
            QuestionType.SPEAKING -> answer.length.coerceAtMost(150) / 150f * 10
        }
    }
}