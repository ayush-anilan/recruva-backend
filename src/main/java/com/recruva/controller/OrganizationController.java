package com.recruva.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruva.service.OrganizationService;
import com.recruva.web.request.OrganizationRequest;
import com.recruva.web.response.OrganizationResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;
    
    @PostMapping 
    public ResponseEntity<OrganizationResponse> createOrganization(@Valid  @RequestBody OrganizationRequest request) {
        // Implementation for creating an organization
        // This method should handle the logic for creating an organization and associating it with the current user
        OrganizationResponse response = organizationService.createOrganization(request);
        return ResponseEntity.ok(response);
    }
}
