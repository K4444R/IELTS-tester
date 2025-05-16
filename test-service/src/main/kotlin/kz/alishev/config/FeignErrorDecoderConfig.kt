package kz.alishev.config

import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

@Configuration
class FeignErrorDecoderConfig {

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return CustomErrorDecoder()
    }

    class CustomErrorDecoder : ErrorDecoder {
        private val logger = LoggerFactory.getLogger(CustomErrorDecoder::class.java)
        private val defaultDecoder = ErrorDecoder.Default()

        override fun decode(methodKey: String, response: Response): Exception {
            val status = HttpStatus.valueOf(response.status())

            if (response.body() != null) {
                try {
                    val reader = BufferedReader(InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))
                    val result = reader.lines().collect(Collectors.joining("\n"))
                    logger.error("Feign error response: $result")
                    return ResponseStatusException(status, result)
                } catch (e: Exception) {
                    logger.error("Error reading Feign response body", e)
                }
            }

            return defaultDecoder.decode(methodKey, response)
        }
    }
}