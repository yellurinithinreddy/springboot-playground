package com.nithin.SecurityApplication.controllers;

import com.nithin.SecurityApplication.dto.LoginDTO;
import com.nithin.SecurityApplication.dto.LoginResponseDTO;
import com.nithin.SecurityApplication.dto.SignUpDTO;
import com.nithin.SecurityApplication.dto.UserDTO;
import com.nithin.SecurityApplication.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${deploy.env}")
    private String env;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest req, HttpServletResponse res){
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken",loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
        cookie.setSecure("production".equals(env));
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("refresh token not found inside the cookies"));

        return ResponseEntity.ok(authService.refresh(refreshToken));

    }
}
