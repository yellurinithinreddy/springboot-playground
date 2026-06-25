package com.nithin.SecurityApplication.services.impl;

import com.nithin.SecurityApplication.entities.User;
import com.nithin.SecurityApplication.exceptions.ResourceNotFoundException;
import com.nithin.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with email not found: "+username));
    }

    public User getUser(Long userId){
        return userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with id:"+userId+" not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
