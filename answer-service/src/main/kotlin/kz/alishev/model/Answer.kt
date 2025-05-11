package kz.alishev.model

import jakarta.persistence.*

@Entity
@Table(name = "answers")
class   Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne
    @JoinColumn(name = "attempt_id")
    var attempt: Attempt? = null

    @Column
    var testType: String? = null

    @Column
    var score: Float? = null

    @Lob
    @Column(columnDefinition = "text")
    var response: String? = null

}