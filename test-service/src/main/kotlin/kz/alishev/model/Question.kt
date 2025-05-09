package kz.alishev.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "questions")
class Question(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, length = 1000)
    var text: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: QuestionType = QuestionType.READING,

    @Column(name = "audio_url")
    var audioUrl: String? = null
)