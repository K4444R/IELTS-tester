package kz.alishev.config

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Configuration
class FeignSecurityConfig {

    @Bean
    fun bearerAuthInterceptor(): RequestInterceptor = RequestInterceptor { template ->
        // First try to get token from current request
        val context = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request = context?.request
        val authHeader = request?.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            template.header(HttpHeaders.AUTHORIZATION, authHeader)
        } else {
            // If no token in request, try to get from Security Context
            val auth = SecurityContextHolder.getContext().authentication
            if (auth is JwtAuthenticationToken) {
                val token = auth.token.tokenValue
                template.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
        }
    }
}