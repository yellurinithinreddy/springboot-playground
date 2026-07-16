package com.nithin.aopImplementation.security;

import com.nithin.aopImplementation.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.master-key}")
    private String masterKey;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(masterKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId",user.getId())
                .claim("role",user.getRole())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(60*100)))
                .signWith(getKey())
                .compact();
    }

    public Claims validateToken(String token){

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
