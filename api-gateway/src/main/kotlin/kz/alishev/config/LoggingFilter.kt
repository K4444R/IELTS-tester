package kz.alishev.config

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.Ordered
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class LoggingFilter : GlobalFilter, Ordered {

    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request: ServerHttpRequest = exchange.request

        logger.info("➡️ [API Gateway] Request: {} {}", request.method, request.uri)
        request.headers.forEach { key, value -> logger.info("   {}: {}", key, value) }

        return chain.filter(exchange).then(Mono.fromRunnable {
            val response = exchange.response
            logger.info("⬅️ [API Gateway] Response: {} {}", response.statusCode, request.uri)
        })
    }

    override fun getOrder(): Int {
        return -1
    }
}