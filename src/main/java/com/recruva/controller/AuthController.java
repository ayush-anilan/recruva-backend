package com.recruva.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruva.db.entities.OrganizationMember;
import com.recruva.service.AuthService;
import com.recruva.service.AuthorizationService;
import com.recruva.service.SecurityService;
import com.recruva.web.request.LoginRequest;
import com.recruva.web.request.RegisterRequest;
import com.recruva.web.response.RegisterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping ("/api/auth")
@RequiredArgsConstructor 
public class AuthController {

    private final AuthService authService;
    private final AuthorizationService authorizationService;
    private final SecurityService securityService;

    @PostMapping ("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        
        RegisterResponse response = authService.registerUser(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping ("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest request) {
        String token = authService.loginUser(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping ("/test")
    public String test()
    {
        return "Authentication request successful";
    }

    @GetMapping ("/test-authorization/{organizationId}")
    public String testAuthorizationCreateJob(@PathVariable UUID organizationId) {
        OrganizationMember member = securityService.getCurrentUserMembership(organizationId);

        return authorizationService.testAuthorizationCreateJob(member);
    }

}
