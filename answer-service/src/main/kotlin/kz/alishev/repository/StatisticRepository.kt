package kz.alishev.repository

import kz.alishev.model.Statistic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StatisticRepository: JpaRepository<Statistic, Long> {
}