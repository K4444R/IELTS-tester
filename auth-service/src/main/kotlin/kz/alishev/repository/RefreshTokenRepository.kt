package kz.alishev.repository

import kz.alishev.model.RefreshToken
import kz.alishev.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun deleteByUser(user: User)
}