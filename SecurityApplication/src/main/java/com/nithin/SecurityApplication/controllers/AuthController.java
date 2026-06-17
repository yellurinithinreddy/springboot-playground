package com.nithin.SecurityApplication.controllers;

import com.nithin.SecurityApplication.dto.LoginDTO;
import com.nithin.SecurityApplication.dto.SignUpDTO;
import com.nithin.SecurityApplication.dto.UserDTO;
import com.nithin.SecurityApplication.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO,HttpServletRequest req,HttpServletResponse res){
        String token = authService.login(loginDTO);
        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
