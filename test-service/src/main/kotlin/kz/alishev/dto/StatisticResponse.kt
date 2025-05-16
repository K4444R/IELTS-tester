package kz.alishev.dto

data class StatisticResponse(
    val id: Long?,
    val userId: String?,
    val averageScore: Float?,
    val bestScore: Float?,
    val attemptsCount: Long?
)