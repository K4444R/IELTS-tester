package kz.alishev.repository

import kz.alishev.model.Attempt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AttemptRepository: JpaRepository<Attempt, Long> {

    fun findByUserId(userId: String): Attempt?
    fun countByUserId(userId: String): Long

}