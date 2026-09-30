package com.recruva.controller;

import com.recruva.service.SecurityService;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruva.service.CandidateService;
import com.recruva.service.JobService;
import com.recruva.service.OrganizationService;
import com.recruva.web.request.CandidateRequest;
import com.recruva.web.request.CandidateUpdateRequest;
import com.recruva.web.request.JobRequest;
import com.recruva.web.request.JobStatusUpdateRequest;
import com.recruva.web.request.OrganizationRequest;
import com.recruva.web.response.CandidateListItemResponse;
import com.recruva.web.response.CandidateListResponse;
import com.recruva.web.response.CandidateResponse;
import com.recruva.web.response.JobListResponse;
import com.recruva.web.response.JobResponse;
import com.recruva.web.response.OrganizationResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/api/organizations")
public class OrganizationController {

    private final SecurityService securityService;
    private final OrganizationService organizationService;
    private final JobService jobService;
    private final CandidateService candidateService;
    
    @PostMapping 
    public ResponseEntity<OrganizationResponse> createOrganization(@Valid  @RequestBody OrganizationRequest request) {
        // Implementation for creating an organization
        // This method should handle the logic for creating an organization and associating it with the current user
        OrganizationResponse response = organizationService.createOrganization(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping ("/{organizationId}/jobs")
    public ResponseEntity<JobResponse> createJob(@PathVariable UUID organizationId, @Valid @RequestBody JobRequest request) {
        JobResponse response = jobService.createJob(organizationId, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping ("/{organizationId}/jobs")
    public ResponseEntity<JobListResponse> getJobsByOrganizationId(@PathVariable UUID organizationId) {
        JobListResponse response = jobService.getJobsByOrganizationId(organizationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{organizationId}/jobs/{jobId}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable  UUID organizationId,@PathVariable  UUID jobId,@Valid @RequestBody JobRequest request){
        JobResponse response = jobService.updateJob(organizationId, jobId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/{organizationId}/jobs/{jobId}/status")
    public ResponseEntity<JobResponse> updateJobStatus(@PathVariable UUID organizationId, @PathVariable UUID jobId, @Valid @RequestBody JobStatusUpdateRequest request){
        JobResponse response = jobService.updateJobStatus(organizationId, jobId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping ("/{organizationId}/candidates")
    public ResponseEntity<CandidateResponse> createCandidate(@PathVariable UUID organizationId, @Valid @RequestBody CandidateRequest request){
        CandidateResponse response = candidateService.createCandidate(organizationId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/{organizationId}/candidates")
    public ResponseEntity<CandidateListResponse> getCandidatesByOrganizationId(@PathVariable UUID organizationId){
        CandidateListResponse response = candidateService.getCandidatesByOrganizationId(organizationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/{organizationId}/candidates/{candidateId}")
    public ResponseEntity<CandidateListItemResponse> getCandidateById(@PathVariable UUID organizationId, @PathVariable UUID candidateId){
        CandidateListItemResponse response = candidateService.getCandidateById(organizationId, candidateId);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping ("/{organizationId}/candidates/{candidateId}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable UUID organizationId, @PathVariable UUID candidateId, @Valid @RequestBody CandidateUpdateRequest request){
        CandidateResponse response = candidateService.updateCandidate(organizationId, candidateId, request);
        return ResponseEntity.ok(response);
    }

    // temporary endpoint for testing purposes
    @GetMapping ("/{organizationId}/membership")
    public String test(@PathVariable UUID organizationId){
        return securityService.getCurrentUserMembership(organizationId).toString();
    }
}
