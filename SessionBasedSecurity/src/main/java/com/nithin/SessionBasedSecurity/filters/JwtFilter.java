package com.nithin.SessionBasedSecurity.filters;

import com.nithin.SessionBasedSecurity.entities.Session;
import com.nithin.SessionBasedSecurity.entities.User;
import com.nithin.SessionBasedSecurity.exceptions.InvalidTokenException;
import com.nithin.SessionBasedSecurity.exceptions.ResourceNotFoundException;
import com.nithin.SessionBasedSecurity.exceptions.SessionNotFoundException;
import com.nithin.SessionBasedSecurity.repositories.SessionRepository;
import com.nithin.SessionBasedSecurity.repositories.UserRepository;
import com.nithin.SessionBasedSecurity.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SessionRepository sessionRepository;
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

            String token = authHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIDFromToken(token);

            Session session = sessionRepository.findByUser_Id(userId)
                    .orElseThrow(() -> new SessionNotFoundException("Other user might have logged in with this user id:"+userId));

            if(!session.getToken().equals(token)) throw new SessionNotFoundException("Other user might have logged in with this user id:"+userId);

            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("user with this id not found: "+userId));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user,null,null
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request,response);
        }catch(Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }

    }
}
