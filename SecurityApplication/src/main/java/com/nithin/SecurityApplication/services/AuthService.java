package com.nithin.SecurityApplication.services;

import com.nithin.SecurityApplication.dto.LoginDTO;
import com.nithin.SecurityApplication.dto.SignUpDTO;
import com.nithin.SecurityApplication.dto.UserDTO;
import com.nithin.SecurityApplication.entities.User;
import com.nithin.SecurityApplication.repositories.UserRepository;
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
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDTO signup(SignUpDTO signUpDTO) {

        if(userRepository.existsByEmail(signUpDTO.getEmail())){
            throw new BadCredentialsException("User with this email already exists"+signUpDTO.getEmail());
        }

        User user = modelMapper.map(signUpDTO,User.class);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        return modelMapper.map(userRepository.save(user),UserDTO.class);

    }

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

        User user = (User) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
