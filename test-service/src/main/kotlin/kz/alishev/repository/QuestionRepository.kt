package kz.alishev.repository

import kz.alishev.model.Question
import kz.alishev.model.QuestionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface QuestionRepository : JpaRepository<Question, UUID> {
    fun findByType(type: QuestionType): List<Question>
}