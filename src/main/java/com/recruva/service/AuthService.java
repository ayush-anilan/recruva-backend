package com.recruva.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recruva.db.entities.User;
import com.recruva.db.repositories.UserRepository;
import com.recruva.exception.EmailAlreadyRegisteredException;
import com.recruva.web.request.RegisterRequest;
import com.recruva.web.response.RegisterResponse;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegisterResponse registerUser(RegisterRequest request) {
        // Implementation for user registration
        
        // Check if the email is already registered
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email is already registered");
        }

        // Encode password before saving the user
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create a new User entity and set its properties
        var user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(encodedPassword);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        
        // Return a success message or perform additional actions if needed
        return RegisterResponse.builder()
                .message("User registered successfully")
                .email(user.getEmail())
                .success(true)
                .build();
    }
}
