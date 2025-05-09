package kz.alishev.service

interface AuthService {
    fun register(username: String, email: String, rawPassword: String): Boolean
    fun login(usernameOrEmail: String, password: String): Pair<String, String>?
    fun refreshToken(oldToken: String): Pair<String, String>?
    fun logout(token: String)
}