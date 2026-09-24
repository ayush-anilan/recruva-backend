package com.recruva.controller;

import com.recruva.service.SecurityService;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final SecurityService securityService;
    private final OrganizationService organizationService;
    
    @PostMapping 
    public ResponseEntity<OrganizationResponse> createOrganization(@Valid  @RequestBody OrganizationRequest request) {
        // Implementation for creating an organization
        // This method should handle the logic for creating an organization and associating it with the current user
        OrganizationResponse response = organizationService.createOrganization(request);
        return ResponseEntity.ok(response);
    }

    // temporary endpoint for testing purposes
    @GetMapping ("/{organizationId}/membership")
    public String test(@PathVariable UUID organizationId){
        return securityService.getCurrentUserMembership(organizationId).toString();
    }
}
