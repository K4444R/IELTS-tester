package kz.alishev.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class ModifyRequestHeaderGatewayFilter   : GatewayFilter {
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val authHeader = request.headers.getFirst("Authorization")
        val mutatedRequest = if (authHeader != null) {
            request.mutate()
                .header("Authorization", authHeader)
                .build()
        } else {
            request
        }
        return chain.filter(exchange.mutate().request(mutatedRequest).build())
    }
}