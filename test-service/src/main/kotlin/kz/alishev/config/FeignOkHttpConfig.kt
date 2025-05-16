package kz.alishev.config

import feign.Client
import feign.okhttp.OkHttpClient as FeignOkHttpClient
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@ConditionalOnClass(FeignAutoConfiguration::class)
class FeignOkHttpConfig {

    @Bean
    @ConditionalOnMissingBean
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool())
            .build()
    }

    @Bean
    @ConditionalOnMissingBean(Client::class)
    fun feignClient(okHttpClient: OkHttpClient): Client {
        return FeignOkHttpClient(okHttpClient)
    }
}