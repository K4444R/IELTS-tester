package kz.alishev.service

import kz.alishev.model.Statistic
import kz.alishev.repository.AttemptRepository
import kz.alishev.repository.ResultRepository
import kz.alishev.repository.StatisticRepository
import org.springframework.stereotype.Service

@Service
class StatisticService(
    private val resultRepository: ResultRepository,
    private val attemptRepository: AttemptRepository,
    private val statisticRepository: StatisticRepository
) {

    fun getByUserId(userId: String): Statistic {
        val statistic = Statistic()
        statistic.userId = userId
        statistic.bestScore = resultRepository.findFirstByOrderByTotalScoreDesc().totalScore
        statistic.averageScore = resultRepository.findAverageScore(userId)
        statistic.attemptsCount = attemptRepository.countByUserId(userId)
        return statisticRepository.save(statistic)
    }

}