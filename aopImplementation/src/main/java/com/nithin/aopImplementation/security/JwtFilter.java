package com.nithin.aopImplementation.security;

import com.nithin.aopImplementation.entity.User;
import com.nithin.aopImplementation.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{

            String authHeader = request.getHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return ;
            }

            String token = authHeader.substring("Bearer ".length());

            Claims claims = jwtUtil.validateToken(token);

            User user = userRepository.findByEmail(claims.getSubject())
                    .orElseThrow(() -> new RuntimeException("user not found"));


            if(claims != null && SecurityContextHolder.getContext().getAuthentication() == null){
                var auth = new UsernamePasswordAuthenticationToken(user,null, List.of(
                        new SimpleGrantedAuthority("ROLE_"+claims.get("role"))
                ));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request,response);
        }
        catch(Exception e){
            handlerExceptionResolver.resolveException(request,response,null,e);
        }

    }
}
