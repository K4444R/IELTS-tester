package kz.alishev.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "roles")
class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(unique = true)
    var name: String? = null
)