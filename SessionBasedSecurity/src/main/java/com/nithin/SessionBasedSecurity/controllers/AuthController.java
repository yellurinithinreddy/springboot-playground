package com.nithin.SessionBasedSecurity.controllers;

import com.nithin.SessionBasedSecurity.dto.LoginDTO;
import com.nithin.SessionBasedSecurity.dto.SignUpDTO;
import com.nithin.SessionBasedSecurity.dto.UserDTO;
import com.nithin.SessionBasedSecurity.services.AuthService;
import com.nithin.SessionBasedSecurity.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginDTO));
    }

}
