package kz.alishev.repository

import kz.alishev.dto.TestType
import kz.alishev.model.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository: JpaRepository<Answer, Long> {

    fun findAllByAttemptId(attemptId: Long): List<Answer>
    fun findByTestTypeAndAttemptId(testType: String, attemptId: Long): Answer?

}