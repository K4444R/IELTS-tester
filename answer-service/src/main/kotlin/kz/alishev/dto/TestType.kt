package kz.alishev.dto

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

enum class TestType(val value: String) {

    LISTENING("listening"),
    READING("reading"),
    WRITING("writing"),
    SPEAKING("speaking");

    companion object {
        fun find(value: String): TestType {
            return entries.find { it.value == value }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown test type")
        }
    }

}