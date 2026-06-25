package com.nithin.SecurityApplication.services;

import com.nithin.SecurityApplication.dto.LoginDTO;
import com.nithin.SecurityApplication.dto.LoginResponseDTO;
import com.nithin.SecurityApplication.dto.SignUpDTO;
import com.nithin.SecurityApplication.dto.UserDTO;
import com.nithin.SecurityApplication.entities.User;
import com.nithin.SecurityApplication.repositories.UserRepository;
import com.nithin.SecurityApplication.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;

    public UserDTO signup(SignUpDTO signUpDTO) {

        if(userRepository.existsByEmail(signUpDTO.getEmail())){
            throw new BadCredentialsException("User with this email already exists"+signUpDTO.getEmail());
        }

        User user = modelMapper.map(signUpDTO,User.class);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        return modelMapper.map(userRepository.save(user),UserDTO.class);

    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateSession(user,refreshToken);

        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDTO refresh(String refreshToken) {
        Long userId = jwtService.getId(refreshToken);
        sessionService.validateSession(refreshToken);
        User user = userService.getUser(userId);
        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);

    }
}
