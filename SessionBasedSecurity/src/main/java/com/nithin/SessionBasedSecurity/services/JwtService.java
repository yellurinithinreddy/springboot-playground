package com.nithin.SessionBasedSecurity.services;

import com.nithin.SessionBasedSecurity.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("email","nithin@gmail.com")
                .claim("roles",Set.of("ADMIN"))
                .expiration(new Date(System.currentTimeMillis() + 1000*60))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();

    }

    public String generateRefreshToken(User user){
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("email","nithin@gmail.com")
                .expiration(new Date(System.currentTimeMillis()+1000L*60*60*24*30*6))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }



    public Long getUserIDFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}
