package kz.alishev.dto

data class AnswerResponse(
    val id: Long?,
    val attemptId: Long?,
    val testType: String?,
    val score: Float?,
    val response: String?
)