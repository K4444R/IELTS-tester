package kz.alishev.model

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(unique = true, nullable = false)
    var token: String,

    @Column(nullable = false)
    var expiresAt: Instant
)