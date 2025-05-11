package kz.alishev.model

import jakarta.persistence.*

@Entity
@Table(name = "statistics")
class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    var userId: String? = null

    @Column
    var averageScore: Float? = null

    @Column
    var bestScore: Float? = null

    @Column
    var attemptsCount: Long? = null

}