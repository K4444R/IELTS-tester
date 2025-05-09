package kz.alishev.dto

import java.util.*

data class QuestionResponse(
    val id: UUID,
    val text: String,
    val audioUrl: String? = null
)