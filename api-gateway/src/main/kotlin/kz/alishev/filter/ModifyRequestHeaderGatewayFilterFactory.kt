package kz.alishev.filter
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory

class ModifyRequestHeaderGatewayFilterFactory
    : AbstractGatewayFilterFactory<ModifyRequestHeaderGatewayFilterFactory.Config>(Config::class.java) {

    class Config

    override fun apply(config: Config): GatewayFilter {
        return ModifyRequestHeaderGatewayFilter()
    }
}