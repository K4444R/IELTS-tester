package kz.alishev.payload

import jakarta.validation.constraints.NotBlank

data class TokenRefreshRequest(
    @field:NotBlank
    val refreshToken: String
)