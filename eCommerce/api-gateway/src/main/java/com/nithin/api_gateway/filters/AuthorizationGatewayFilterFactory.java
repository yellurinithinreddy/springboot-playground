package com.nithin.api_gateway.filters;

import com.nithin.api_gateway.service.JwtService;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthorizationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring("Bearer ".length());

            Long userId = jwtService.getUserIdFromToken(token);

            List<String> userRoles = jwtService.getRolesFromToken(token);

            List<String> allowedRoles = config.allowed;

            for(String role:userRoles){
                for(String allowed: allowedRoles){
                    if(role.equals(allowed)) {

                        var request = exchange.getRequest()
                                .mutate()
                                .header("X-User-Id", userId.toString())
                                .header("X-Role", allowed)
                                .build();

                        return chain.filter(exchange.mutate().request(request).build());
                    }
                }
            }

            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();


        });
    }

    @Data
    public static class Config{
        private List<String> allowed;
    }
}
