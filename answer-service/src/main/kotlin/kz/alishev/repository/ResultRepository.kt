package kz.alishev.repository

import kz.alishev.model.Result
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ResultRepository: JpaRepository<Result, Long> {

    fun findFirstByOrderByTotalScoreDesc(): Result

    @Query("select AVG(r.totalScore) from Result r where r.userId = :userId")
    fun findAverageScore(@Param("userId") userId: String): Float

}