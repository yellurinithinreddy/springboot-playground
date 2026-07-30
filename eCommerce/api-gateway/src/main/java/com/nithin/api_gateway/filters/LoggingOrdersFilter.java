package com.nithin.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingOrdersFilter extends AbstractGatewayFilterFactory<LoggingOrdersFilter.Config> implements Ordered {
    public LoggingOrdersFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain) -> {
            log.info("Logging from Orders Filter Pre : {}",exchange.getRequest().getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                log.info("Logging from orders filter post: {}",exchange.getResponse().getStatusCode());
            }));
        };
    }

    @Override
    public int getOrder() {
        return 1;
    }

    public static class Config{}
}
