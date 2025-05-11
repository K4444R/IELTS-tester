package kz.alishev.model

import jakarta.persistence.*
import org.springframework.boot.autoconfigure.security.SecurityProperties
import java.time.Instant
import java.util.*

@Entity
@Table(name = "attempts")
class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    var userId: String? = null

    @Column
    var startedAt: Instant? = null

    @Column
    var finishedAt: Instant? = null

}