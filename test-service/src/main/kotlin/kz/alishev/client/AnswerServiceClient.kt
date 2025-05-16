package kz.alishev.client

import kz.alishev.dto.AnswerResponse
import kz.alishev.dto.AttemptRequest
import kz.alishev.dto.AnswerDto
import kz.alishev.dto.StatisticResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kz.alishev.config.*

@FeignClient(
    name = "answer-service",
    configuration = [FeignSecurityConfig::class, FeignOkHttpConfig::class]
)
interface AnswerServiceClient {

    @PostMapping("/attempt")
    fun createAttempt(@RequestBody attempt: AttemptRequest): ResponseEntity<Any>

    @PatchMapping("/attempt/finish/{attemptId}")
    fun <T> finishAttempt(@PathVariable attemptId: Long, @RequestBody answerDto: AnswerDto<T>): ResponseEntity<Any>

    @GetMapping("/answer")
    fun getAllAnswersByAttemptId(@RequestParam attemptId: Long): List<AnswerResponse>

    @GetMapping("/statistics/{userId}")
    fun getStatisticsByUserId(@PathVariable userId: String): StatisticResponse
}