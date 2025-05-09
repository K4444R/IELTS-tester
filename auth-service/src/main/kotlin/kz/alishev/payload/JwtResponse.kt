package kz.alishev.payload

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer"
)