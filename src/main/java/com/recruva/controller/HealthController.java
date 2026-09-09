package com.recruva.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/api")
public class HealthController {
    // This class is responsible for handling health check requests
    // It can be expanded to include more health-related endpoints in the future
    @GetMapping ("/health")
    public String checkHealth() {
        return "Application is healthy";
    }
}