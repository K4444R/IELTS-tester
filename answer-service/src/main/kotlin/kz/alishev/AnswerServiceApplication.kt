package kz.alishev

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@EnableFeignClients
@SpringBootApplication
@EnableMethodSecurity
class AnswerServiceApplication

fun main(args: Array<String>) {
    runApplication<AnswerServiceApplication>(*args)
}