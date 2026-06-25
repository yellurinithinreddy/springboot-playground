package com.nithin.SessionBasedSecurity.services;

import com.nithin.SessionBasedSecurity.dto.LoginDTO;
import com.nithin.SessionBasedSecurity.dto.LoginResponseDTO;
import com.nithin.SessionBasedSecurity.dto.SignUpDTO;
import com.nithin.SessionBasedSecurity.dto.UserDTO;
import com.nithin.SessionBasedSecurity.entities.Session;
import com.nithin.SessionBasedSecurity.entities.User;
import com.nithin.SessionBasedSecurity.exceptions.ResourceNotFoundException;
import com.nithin.SessionBasedSecurity.repositories.SessionRepository;
import com.nithin.SessionBasedSecurity.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;

    public UserDTO signup(SignUpDTO signUpDTO) {
        if(userRepository.existsByEmail(signUpDTO.getEmail())){
            throw new BadCredentialsException("User with Email already exists: "+signUpDTO.getEmail());
        }
        User user = modelMapper.map(signUpDTO,User.class);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        return modelMapper.map(userRepository.save(user),UserDTO.class);

    }


    @Transactional
    public LoginResponseDTO login(LoginDTO loginDTO) {
//        User user = userRepository.findByEmail(loginDTO.getEmail())
//                .orElseThrow(() -> new BadCredentialsException("please signup before login account doesn not exist with this email: "+loginDTO.getEmail()));
//
//        sessionRepository.deleteByUser_Id(user.getId());


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );

        User _user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(_user);
        String refreshToken = jwtService.generateRefreshToken(_user);
        sessionService.generateSession(_user,refreshToken);
//        Session session = Session.builder()
//                .user(_user)
//                .token(token)
//                .build();

//        sessionRepository.save(session);
        return new LoginResponseDTO(_user.getId(),accessToken,refreshToken);

    }

    public LoginResponseDTO refresh(String refreshToken) {
        Long userId = jwtService.getUserIDFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));
        String accessToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDTO(userId,accessToken,refreshToken);

    }

    public void logout(Long userId) {
        sessionService.removeSessions(userId);
    }
}
