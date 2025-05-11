package kz.alishev.model

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "results")
class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne
    @JoinColumn(name = "attempt_id")
    var attempt: Attempt? = null

    @Column
    var userId: String? = null

    @Column
    var totalScore: Float? = null

    @Column
    var createdAt: Instant? = null

}