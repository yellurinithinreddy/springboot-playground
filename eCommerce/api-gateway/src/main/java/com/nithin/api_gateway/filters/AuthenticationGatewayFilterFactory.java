package com.nithin.api_gateway.filters;

import com.nithin.api_gateway.service.JwtService;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;
    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain) -> {
            if(!config.isEnabled) return chain.filter(exchange);
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring("Bearer ".length());

            Long userId = jwtService.getUserIdFromToken(token);

            var request = exchange.getRequest()
                    .mutate()
                    .header("X-User-Id",userId.toString())
                    .build();

            return chain.filter(exchange.mutate().request(request).build());



        };
    }


    @Data
    public static class Config{
        private boolean isEnabled;
    }
}
