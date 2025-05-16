package kz.alishev.dto

data class CompleteTestRequest(
    val userId: String,
    val readingAnswer: String,
    val listeningAnswer: String,
    val writingAnswer: String,
    val speakingAnswer: String
)