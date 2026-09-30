package com.recruva.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recruva.db.entities.Candidate;
import com.recruva.db.entities.OrganizationCandidate;
import com.recruva.db.repositories.CandidateRepository;
import com.recruva.db.repositories.OrganizationCandidateRepository;
import com.recruva.enums.Permission;
import com.recruva.exception.CandidateAlreadyExistsException;
import com.recruva.exception.CandidateNotFoundException;
import com.recruva.web.request.CandidateRequest;
import com.recruva.web.request.CandidateUpdateRequest;
import com.recruva.web.response.CandidateListItemResponse;
import com.recruva.web.response.CandidateListResponse;
import com.recruva.web.response.CandidateResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
@Transactional 
public class CandidateService {
    
    private final SecurityService securityService;
    private final AuthorizationService authorizationService;
    private final CandidateRepository candidateRepository;
    private final OrganizationCandidateRepository organizationCandidateRepository;

    public CandidateResponse createCandidate(UUID organizationId, CandidateRequest request){

        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to create a candidate in the organization
        authorizationService.requirePermission(membership, Permission.MANAGE_CANDIDATES);

        // Create a new candidate entity and set its properties
        var candidate = candidateRepository.findByEmail(request.getEmail()).orElseGet(() -> {
            var newCandidate = new Candidate();

            newCandidate.setFirstName(request.getFirstName());
            newCandidate.setLastName(request.getLastName());
            newCandidate.setEmail(request.getEmail());
            newCandidate.setPhoneNumber(request.getPhoneNumber());

            var now = LocalDateTime.now();
            newCandidate.setCreatedAt(now);
            newCandidate.setUpdatedAt(now);
            return candidateRepository.save(newCandidate);
        });

        // Check candidate <-> organization relationship
        var organizationCandidate = organizationCandidateRepository.findByCandidate_IdAndOrganization_Id(candidate.getId(), organizationId);
        if(organizationCandidate.isEmpty()){
            // If the relationship does not exist, create it
            var newOrganizationCandidate = new OrganizationCandidate();
            newOrganizationCandidate.setCandidate(candidate);
            newOrganizationCandidate.setOrganization(membership.getOrganization());
            newOrganizationCandidate.setCreatedAt(LocalDateTime.now());
            newOrganizationCandidate.setUpdatedAt(LocalDateTime.now());
            organizationCandidateRepository.save(newOrganizationCandidate);
        } else {
            // If the relationship already exists, you can choose to throw an exception or handle it as needed
            throw new CandidateAlreadyExistsException("Candidate is already associated with the organization");
        }

        return CandidateResponse.builder()
                .message("Candidate created successfully")
                .candidateId(candidate.getId().toString())
                .success(true)
                .build();
    
    }

    public CandidateListResponse getCandidatesByOrganizationId(UUID organizationId) {
        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to view candidates in the organization
        authorizationService.requirePermission(membership, Permission.VIEW_CANDIDATES);

        // Fetch candidates associated with the organization
        List<Candidate> candidates = organizationCandidateRepository.findByOrganizationId(organizationId)
                .stream().map(OrganizationCandidate::getCandidate).toList();

        List<CandidateListItemResponse> candidateResponses = candidates.stream().map( candidate -> CandidateListItemResponse.builder()
                .id(candidate.getId())
                .firstName(candidate.getFirstName())
                .lastName(candidate.getLastName())
                .email(candidate.getEmail())
                .phoneNumber(candidate.getPhoneNumber())
                .createdAt(candidate.getCreatedAt())
                .updatedAt(candidate.getUpdatedAt())
                .build()).collect(Collectors.toList());

        return CandidateListResponse.builder()
                .candidates(candidateResponses)
                .message("Candidates retrieved successfully")
                .success(true)
                .build();
    }

    public CandidateListItemResponse getCandidateById(UUID organizationId, UUID candidateId) {
        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to view candidates in the organization
        authorizationService.requirePermission(membership, Permission.VIEW_CANDIDATES);

        // Fetch candidate by ID
        Candidate candidate = organizationCandidateRepository.findByCandidate_IdAndOrganization_Id(candidateId, organizationId)
                .map(OrganizationCandidate::getCandidate)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found in the organization"));

        return CandidateListItemResponse.builder()
                .id(candidate.getId())
                .firstName(candidate.getFirstName())
                .lastName(candidate.getLastName())
                .email(candidate.getEmail())
                .phoneNumber(candidate.getPhoneNumber())
                .createdAt(candidate.getCreatedAt())
                .updatedAt(candidate.getUpdatedAt())
                .build();
    }

    public CandidateResponse updateCandidate(UUID organizationId, UUID candidateId, CandidateUpdateRequest request) {
        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to manage candidates in the organization
        authorizationService.requirePermission(membership, Permission.MANAGE_CANDIDATES);

        // Fetch candidate by ID
        Candidate candidate = organizationCandidateRepository.findByCandidate_IdAndOrganization_Id(candidateId, organizationId)
                .map(OrganizationCandidate::getCandidate)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found in the organization"));

        // Update candidate details
        candidate.setFirstName(request.getFirstName());
        candidate.setLastName(request.getLastName());
        candidate.setEmail(request.getEmail());
        candidate.setPhoneNumber(request.getPhoneNumber());
        candidate.setUpdatedAt(LocalDateTime.now());

        // Save updated candidate
        candidateRepository.save(candidate);

        return CandidateResponse.builder()
                .message("Candidate updated successfully")
                .candidateId(candidate.getId().toString())
                .success(true)
                .build();
    }
}
