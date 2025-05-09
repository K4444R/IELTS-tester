package kz.alishev.payload

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:NotBlank @field:Size(min = 3, max = 20)
    val username: String,

    @field:NotBlank @field:Email
    val email: String,

    @field:NotBlank @field:Size(min = 6, max = 100)
    val password: String
)

