package kz.alishev.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kz.alishev.filter.ModifyRequestHeaderGatewayFilterFactory

@Configuration
class GatewayConfig {

    @Bean
    fun modifyRequestHeaderGatewayFilterFactory(): ModifyRequestHeaderGatewayFilterFactory {
        return ModifyRequestHeaderGatewayFilterFactory()
    }
}