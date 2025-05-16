package kz.alishev.dto

data class TestResultResponse(
    val attemptId: Long,
    val userId: String,
    val results: List<AnswerResponse>,
    val totalScore: Float
)