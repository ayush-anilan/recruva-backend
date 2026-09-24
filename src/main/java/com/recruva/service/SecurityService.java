package com.recruva.service;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.recruva.db.entities.OrganizationMember;
import com.recruva.db.entities.User;
import com.recruva.db.repositories.OrganizationMemberRepository;
import com.recruva.exception.ForbiddenException;
import com.recruva.exception.UnauthenticatedUserException;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class SecurityService {

    private final OrganizationMemberRepository organizationMemberRepository;
    
    public User getCurrentUser() {
        // Implementation for retrieving the currently authenticated user
        // This can be done using Spring Security's SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
                return (User) principal;
            }
        }
        throw new UnauthenticatedUserException("No authenticated user found");
    }

    public UUID getCurrentUserId(){
        return getCurrentUser().getId();
    }

    public OrganizationMember getCurrentUserMembership(UUID organizationId){
        User currentUser = getCurrentUser();
        return organizationMemberRepository.findByOrganization_IdAndUser_Id(organizationId, currentUser.getId())
                .orElseThrow(() -> new ForbiddenException("User is not a member of the organization"));
    }
}
