package kz.alishev.service.impl

import kz.alishev.model.*
import kz.alishev.config.JwtTokenProvider
import kz.alishev.repository.*
import kz.alishev.service.AuthService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : AuthService {

    override fun register(username: String, email: String, rawPassword: String): Boolean {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return false
        }

        val roleUser = roleRepository.findByName("ROLE_USER")
            .orElseThrow { RuntimeException("Роль ROLE_USER не найдена") }

        val user = User(
            username = username,
            email = email,
            password = passwordEncoder.encode(rawPassword),
            roles = setOf(roleUser),
            enabled = true
        )

        userRepository.save(user)
        return true
    }

    override fun login(usernameOrEmail: String, password: String): Pair<String, String>? {
        val user = userRepository.findByUsername(usernameOrEmail)
            .orElseGet { userRepository.findByEmail(usernameOrEmail).orElse(null) }
            ?: return null

        if (!passwordEncoder.matches(password, user.password)) {
            return null
        }

        val accessToken = jwtTokenProvider.generateToken(user.username)
        val refreshToken = java.util.UUID.randomUUID().toString()

        refreshTokenRepository.save(
            RefreshToken(
                token = refreshToken,
                user = user,
                expiresAt = Instant.now().plus(7, ChronoUnit.DAYS)
            )
        )

        return Pair(accessToken, refreshToken)
    }

    override fun refreshToken(oldToken: String): Pair<String, String>? {
        val refreshToken = refreshTokenRepository.findByToken(oldToken)
            .filter { it.expiresAt.isAfter(Instant.now()) }
            .orElse(null) ?: return null

        val accessToken = jwtTokenProvider.generateToken(refreshToken.user.username)
        val newRefreshToken = java.util.UUID.randomUUID().toString()

        refreshTokenRepository.delete(refreshToken)
        refreshTokenRepository.save(
            RefreshToken(
                token = newRefreshToken,
                user = refreshToken.user,
                expiresAt = Instant.now().plus(7, ChronoUnit.DAYS)
            )
        )

        return Pair(accessToken, newRefreshToken)
    }

    override fun logout(token: String) {
        refreshTokenRepository.findByToken(token).ifPresent {
            refreshTokenRepository.delete(it)
        }
    }
}