package com.nithin.aopImplementation.service;

import com.nithin.aopImplementation.annotation.ValidateEntity;
import com.nithin.aopImplementation.dto.LoginRequest;
import com.nithin.aopImplementation.dto.LoginResponse;
import com.nithin.aopImplementation.dto.SignUpRequest;
import com.nithin.aopImplementation.dto.SignUpResponse;
import com.nithin.aopImplementation.entity.User;
import com.nithin.aopImplementation.repository.UserRepository;
import com.nithin.aopImplementation.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;



    @Transactional
    @ValidateEntity
    public SignUpResponse signup(SignUpRequest request) {

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("USER")
                .build();

        user = userRepository.save(user);
        SignUpResponse signUpResponse = new SignUpResponse(user.getId(),user.getName(),user.getEmail());
        return signUpResponse;

    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password())
        );

        User user = (User) auth.getPrincipal();
        String token = jwtUtil.generateAccessToken(user);
        user.setAccessToken(token);
        userRepository.save(user);
        return new LoginResponse(token);

    }


}
