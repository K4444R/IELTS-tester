package kz.alishev.controller

import kz.alishev.model.Statistic
import kz.alishev.service.StatisticService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/statistics")
class StatisticController(private val statisticService: StatisticService) {

    @GetMapping("/{userId}")
    fun findByUserId(@PathVariable userId: String): Statistic {
        return statisticService.getByUserId(userId)
    }

}