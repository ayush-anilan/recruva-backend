package com.recruva.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.recruva.db.entities.Organization;
import com.recruva.db.entities.OrganizationMember;
import com.recruva.db.entities.User;
import com.recruva.db.repositories.OrganizationMemberRepository;
import com.recruva.db.repositories.OrganizationRepository;
import com.recruva.enums.Role;
import com.recruva.web.request.OrganizationRequest;
import com.recruva.web.response.OrganizationListItemResponse;
import com.recruva.web.response.OrganizationResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final SecurityService securityService;
    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    @Transactional
    public OrganizationResponse createOrganization(OrganizationRequest request) {
        // Implementation for creating an organization
        // This method should handle the logic for creating an organization and
        // associating it with the current user
        User currentUser = securityService.getCurrentUser();

        var now = LocalDateTime.now();
        // Logic to create the organization and associate it with the current user
        // Create the organization and associate it with the current user
        var org = new Organization();
        org.setName(request.getName());
        org.setAddress(request.getAddress());
        org.setWebsite(request.getWebsite());
        org.setLogoUrl(request.getLogoUrl());
        org.setCreatedAt(now);
        org.setUpdatedAt(now);
        organizationRepository.save(org);

        // Associate the current user with the organization as a member
        var organizationMember = new OrganizationMember();
        organizationMember.setOrganization(org);
        organizationMember.setUser(currentUser);
        organizationMember.setRole(Role.ORGANIZATION_ADMIN); // Set the role of the user in the organization
        organizationMember.setCreatedAt(now);
        organizationMember.setUpdatedAt(now);
        organizationMemberRepository.save(organizationMember);

        return OrganizationResponse.builder()
                .organizationId(org.getId())
                .message("Organization created successfully")
                .organizationName(org.getName())
                .success(true)
                .build();
    }

    public List<OrganizationListItemResponse> getMyOrganizations() {
        User currentUser = securityService.getCurrentUser();
        List<OrganizationMember> memberships = organizationMemberRepository
                .findAllByUser_IdAndIsActiveTrue(currentUser.getId());

        return memberships.stream()
                .map(membership -> {
                    Organization organization = membership.getOrganization();
                    return OrganizationListItemResponse.builder().organizationId(organization.getId())
                            .organizationName(organization.getName())
                            .address(organization.getAddress())
                            .website(organization.getWebsite())
                            .logoUrl(organization.getLogoUrl())
                            .role(membership.getRole()) // Include the role of the user in the organization
                            .build();
                }).toList();
    }
}