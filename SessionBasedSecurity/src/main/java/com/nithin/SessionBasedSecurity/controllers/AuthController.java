package com.nithin.SessionBasedSecurity.controllers;

import com.nithin.SessionBasedSecurity.dto.LoginDTO;
import com.nithin.SessionBasedSecurity.dto.LoginResponseDTO;
import com.nithin.SessionBasedSecurity.dto.SignUpDTO;
import com.nithin.SessionBasedSecurity.dto.UserDTO;
import com.nithin.SessionBasedSecurity.services.AuthService;
import com.nithin.SessionBasedSecurity.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody @Valid SignUpDTO signUpDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO, HttpServletResponse res){
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);

        Cookie cookie = new Cookie("refreshToken",loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);

    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("refresh token not found inside the cookies"));

        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(refreshToken));
    }

    @DeleteMapping("/logout/{userId}")
    public ResponseEntity<Void> logout(@PathVariable Long userId){
        authService.logout(userId);
        return ResponseEntity.notFound().build();
    }



}
