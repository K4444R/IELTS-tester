package kz.alishev.controller

import kz.alishev.payload.*
import kz.alishev.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody signupRequest: SignupRequest): ResponseEntity<MessageResponse> {
        val created = authService.register(
            signupRequest.username,
            signupRequest.email,
            signupRequest.password
        )
        return if (created) {
            ResponseEntity.ok(MessageResponse("Пользователь успешно зарегистрирован"))
        } else {
            ResponseEntity
                .badRequest()
                .body(MessageResponse("Ошибка: Username или Email уже заняты"))
        }
    }

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val tokens = authService.login(
            loginRequest.usernameOrEmail,
            loginRequest.password
        ) ?: return ResponseEntity
            .status(401)
            .body(MessageResponse("Неверный логин или пароль"))

        val (accessToken, refreshToken) = tokens
        return ResponseEntity.ok(
            JwtResponse(accessToken = accessToken, refreshToken = refreshToken)
        )
    }

    @PostMapping("/refresh")
    fun refreshToken(@Valid @RequestBody request: TokenRefreshRequest): ResponseEntity<Any> {
        val tokens = authService.refreshToken(request.refreshToken)
            ?: return ResponseEntity
                .badRequest()
                .body(MessageResponse("Неверный или просроченный refresh token"))

        val (newAccessToken, newRefreshToken) = tokens
        return ResponseEntity.ok(
            JwtResponse(accessToken = newAccessToken, refreshToken = newRefreshToken)
        )
    }

    @PostMapping("/logout")
    fun logoutUser(@Valid @RequestBody request: TokenRefreshRequest): ResponseEntity<MessageResponse> {
        authService.logout(request.refreshToken)
        return ResponseEntity.ok(MessageResponse("Успешный выход"))
    }
}
