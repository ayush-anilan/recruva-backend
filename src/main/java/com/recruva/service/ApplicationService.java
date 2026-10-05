package com.recruva.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recruva.db.entities.Application;
import com.recruva.db.repositories.ApplicationRepository;
import com.recruva.db.repositories.JobRepository;
import com.recruva.db.repositories.OrganizationCandidateRepository;
import com.recruva.enums.ApplicationStatus;
import com.recruva.enums.JobStatus;
import com.recruva.enums.Permission;
import com.recruva.exception.ApplicationAlreadyExistsException;
import com.recruva.exception.ApplicationNotFoundException;
import com.recruva.exception.CandidateNotFoundException;
import com.recruva.exception.JobNotAcceptingApplicationsException;
import com.recruva.exception.JobNotFoundException;
import com.recruva.web.request.ApplicationRequest;
import com.recruva.web.response.ApplicationListItemResponse;
import com.recruva.web.response.ApplicationListResponse;
import com.recruva.web.response.ApplicationResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
@Transactional 
public class ApplicationService {
    
    private final SecurityService securityService;
    private final AuthorizationService authorizationService;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final OrganizationCandidateRepository organizationCandidateRepository;

    public ApplicationResponse createApplication(UUID organizationId, ApplicationRequest request){

        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to create an application in the organization
        authorizationService.requirePermission(membership, Permission.MANAGE_APPLICATIONS);

        // Check if the job exists and belongs to the organization
        var job = jobRepository.findByIdAndOrganization(request.getJobId(), membership.getOrganization())
                .orElseThrow(() -> new JobNotFoundException("Job not found in the organization"));

        // Check if the job is published or not
        if(job.getStatus() != JobStatus.PUBLISHED){
            throw new JobNotAcceptingApplicationsException("Applications can only be submitted for published jobs");
        }

        // Check if the candidate exists and belongs to the organization
        var organizationCandidate = organizationCandidateRepository.findByCandidate_IdAndOrganization_Id(request.getCandidateId(), organizationId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate does not belong to the organization"));
        
        // Already applied check
        boolean existingApplication = applicationRepository.existsByOrganizationCandidate_IdAndJob_Id(organizationCandidate.getId(), job.getId());
        
        if(existingApplication){
            throw new ApplicationAlreadyExistsException("Candidate has already applied for this job");
        }

        // Create a new application entity and set its properties
        var application = new Application();

        application.setOrganizationCandidate(organizationCandidate);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);

        var now = LocalDateTime.now();
        application.setAppliedAt(now);
        application.setCreatedAt(now);
        application.setUpdatedAt(now);

        applicationRepository.save(application);

        return ApplicationResponse.builder()
                .message("Application created successfully")
                .applicationId(application.getId())
                .success(true)
                .build();
    }

    public ApplicationListResponse getApplicationsByJobOrganizationId(UUID organizationId) {
        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to view applications in the organization
        authorizationService.requirePermission(membership, Permission.VIEW_APPLICATIONS);

        List<Application> applications = applicationRepository.findByJob_OrganizationId(organizationId);

        List<ApplicationListItemResponse> applicationResponses = applications.stream()
                .map(application -> {

                    var candidate = application.getOrganizationCandidate().getCandidate();
                    
                    var job = application.getJob();

                return ApplicationListItemResponse.builder()
                        .applicationId(application.getId())
                        .candidateId(candidate.getId())
                        .candidateName(candidate.getFirstName() + " " + candidate.getLastName())
                        .candidateEmail(candidate.getEmail())
                        .jobId(job.getId())
                        .jobTitle(job.getTitle())
                        .status(application.getStatus())
                        .appliedAt(application.getAppliedAt())
                        .createdAt(application.getCreatedAt())
                        .updatedAt(application.getUpdatedAt())
                        .build();
                    }
                ).collect(Collectors.toList());
                        
        return ApplicationListResponse.builder()
                .applications(applicationResponses)
                .message("Applications retrieved successfully")
                .success(true)
                .build();
    }

    public ApplicationListItemResponse getApplicationsByApplicationId(UUID organizationId, UUID applicationId) {
        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to view applications in the
        // organization
        authorizationService.requirePermission(membership, Permission.VIEW_APPLICATIONS);

        var application = applicationRepository.findByIdAndJob_OrganizationId(applicationId, organizationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found in the organization"));

        var candidate = application.getOrganizationCandidate().getCandidate();

        var job = application.getJob();

        return ApplicationListItemResponse.builder()
                .applicationId(application.getId())
                .candidateId(candidate.getId())
                .candidateName(
                        candidate.getFirstName() + " " +
                                candidate.getLastName())
                .candidateEmail(candidate.getEmail())
                .jobId(job.getId())
                .jobTitle(job.getTitle())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }
}
