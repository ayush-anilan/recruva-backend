package com.recruva.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recruva.db.entities.Job;
import com.recruva.db.repositories.JobRepository;
import com.recruva.enums.JobStatus;
import com.recruva.enums.Permission;
import com.recruva.enums.Role;
import com.recruva.exception.JobNotFoundException;
import com.recruva.web.request.JobRequest;
import com.recruva.web.response.JobListResponse;
import com.recruva.web.response.JobResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

    private final SecurityService securityService;
    private final AuthorizationService authorizationService;
    private final JobRepository jobRepository;

    public JobResponse createJob(UUID organizationId, JobRequest request) {

        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to create a job in the organization
        authorizationService.requirePermission(membership, Permission.CREATE_JOB);

        // Get the authenticated user from the security context
        var currentUser = securityService.getCurrentUser();

        // Create a new job entity and set its properties
        var job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCategory(request.getCategory());
        job.setLocation(request.getLocation());
        job.setCreatedByUser(currentUser);
        job.setOrganization(membership.getOrganization());
        job.setStatus(JobStatus.DRAFT);

        var now = LocalDateTime.now();
        job.setCreatedAt(now);
        job.setUpdatedAt(now);

        // Save the job to the database
        jobRepository.save(job);

        // Return the response
        return JobResponse.builder()
                .message("Job created successfully")
                .jobTitle(request.getTitle())
                .success(true)
                .build();
    }

    public JobListResponse getJobsByOrganizationId(UUID organizationId) {

        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Check permissions for the current user to view jobs in the organization
        authorizationService.requirePermission(membership, Permission.VIEW_JOB);

        // Get Organization entity from the membership
        var organization = membership.getOrganization();

        // Fetch jobs associated with the organization from the database
        var jobs = jobRepository.findByOrganization(organization);
        var jobTitles = jobs.stream().map(Job::getTitle).collect(Collectors.toList());

        // Return the response with the list of jobs
        return JobListResponse.builder()
                .message("Jobs retrieved successfully")
                .jobTitles(jobTitles)
                .success(true)
                .build();
    }

    public JobResponse updateJob(UUID organizationId, UUID jobId , JobRequest request){
        // Get membership of the current user in the organization
        var membership = securityService.getCurrentUserMembership(organizationId);

        // Get organization entity from the membership
        var organization = membership.getOrganization();

        // Find job using jobId + membership.organization
        var job = jobRepository.findByIdAndOrganization(jobId, organization).orElseThrow(() -> new JobNotFoundException("Job not found"));

        // Check role + ownership authorization
        authorizationService.requireJobUpdatePermission(membership, job);

        // Update editable fields
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCategory(request.getCategory());
        job.setLocation(request.getLocation());
        job.setUpdatedAt(LocalDateTime.now());

        jobRepository.save(job);

        return JobResponse.builder().message("Job updated successfully").jobTitle(job.getTitle()).success(true).build();

    }
}
