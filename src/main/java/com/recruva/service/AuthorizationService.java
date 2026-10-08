package com.recruva.service;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.recruva.db.entities.Application;
import com.recruva.db.entities.Job;
import com.recruva.db.entities.OrganizationMember;
import com.recruva.enums.ApplicationStatus;
import com.recruva.enums.Permission;
import com.recruva.enums.Role;
import com.recruva.exception.ForbiddenException;
import com.recruva.exception.InvalidApplicationStatusTransitionException;


@Service 
public class AuthorizationService {

    private static final Map<Role, Set<Permission>> ROLE_PERMISSIONS = Map.of(
        Role.ORGANIZATION_ADMIN, Set.of(Permission.CREATE_JOB, Permission.VIEW_JOB, Permission.UPDATE_JOB, Permission.MANAGE_JOB_STATUS, Permission.MANAGE_CANDIDATES, Permission.VIEW_CANDIDATES, Permission.MANAGE_APPLICATIONS, Permission.VIEW_APPLICATIONS, Permission.MANAGE_APPLICATION_STATUS),
        Role.RECRUITER, Set.of(Permission.CREATE_JOB, Permission.VIEW_JOB, Permission.UPDATE_JOB, Permission.MANAGE_JOB_STATUS, Permission.MANAGE_CANDIDATES, Permission.VIEW_CANDIDATES, Permission.MANAGE_APPLICATIONS, Permission.VIEW_APPLICATIONS, Permission.MANAGE_APPLICATION_STATUS),
        Role.HIRING_MANAGER, Set.of(Permission.VIEW_JOB, Permission.VIEW_CANDIDATES, Permission.MANAGE_APPLICATIONS, Permission.VIEW_APPLICATIONS, Permission.MANAGE_APPLICATION_STATUS),
        Role.INTERVIEWER, Set.of(Permission.VIEW_JOB, Permission.MANAGE_APPLICATIONS, Permission.VIEW_APPLICATIONS, Permission.MANAGE_APPLICATION_STATUS)
    );

    public void requirePermission(OrganizationMember member, Permission permission){

        Set<Permission> permissions = ROLE_PERMISSIONS.get(member.getRole());

        if(permissions != null && permissions.contains(permission)){
            return; // User has the required permission
        }

        throw new ForbiddenException("User does not have permission: " + permission);
    }

    public void requireJobUpdatePermission(OrganizationMember member, Job job){
        requirePermission(member, Permission.UPDATE_JOB);

        if(member.getRole() == Role.RECRUITER && !job.getCreatedByUser().getId().equals(member.getUser().getId()) ){
            throw new ForbiddenException("Recruiters can only update jobs they created");
        }
    }

    public void requireJobStatusPermission(OrganizationMember member, Job job){
        requirePermission(member, Permission.MANAGE_JOB_STATUS);

        if(member.getRole() == Role.RECRUITER && !job.getCreatedByUser().getId().equals(member.getUser().getId()) ){
            throw new ForbiddenException("Recruiters can only manage status of jobs they created");
        }
    }

    public void requireApplicationStatusPermission(OrganizationMember member, Application application, ApplicationStatus newStatus){
        requirePermission(member, Permission.MANAGE_APPLICATION_STATUS);

        Role role = member.getRole();
        ApplicationStatus currentStatus = application.getStatus();

        // No status change
        if(currentStatus == newStatus){
            throw new InvalidApplicationStatusTransitionException("Application is already in " + newStatus + " status");
        }

        // Terminal status cannot be changed
        if(currentStatus == ApplicationStatus.REJECTED || currentStatus == ApplicationStatus.HIRED){
            throw new InvalidApplicationStatusTransitionException("Application status cannot be changed from " + currentStatus);
        }

        // Any active stage can be rejected by any role with status-management permission
        if(newStatus == ApplicationStatus.REJECTED){
            return;
        }

        // Organization Admin and Recruiter can perform the full pipeline
        if(role == Role.ORGANIZATION_ADMIN || role == Role.RECRUITER){
            if(isValidPipelineTransition(currentStatus, newStatus)){
                return;
            }
        }

        // Hiring Manager can manage recruitment stages
        if(role == Role.HIRING_MANAGER){

            if(currentStatus == ApplicationStatus.SCREENING && newStatus == ApplicationStatus.SHORTLISTED){
                return;
            }

            if(currentStatus == ApplicationStatus.SHORTLISTED && newStatus == ApplicationStatus.INTERVIEW){
                return;
            }

            if(currentStatus == ApplicationStatus.INTERVIEW && newStatus == ApplicationStatus.OFFER){
                return;
            }

            if(currentStatus == ApplicationStatus.OFFER && newStatus == ApplicationStatus.HIRED){
                return;
            }
        }

        if(role == Role.INTERVIEWER){
            if(currentStatus == ApplicationStatus.SHORTLISTED && newStatus == ApplicationStatus.INTERVIEW){
                return;
            }
        }

        throw new InvalidApplicationStatusTransitionException("Role " + role + " cannot change application status from " + currentStatus + " to " + newStatus);
    }

    private boolean isValidPipelineTransition(ApplicationStatus currentStatus, ApplicationStatus newStatus){

        if(currentStatus == ApplicationStatus.APPLIED && newStatus == ApplicationStatus.SCREENING){
            return true;
        }

        if(currentStatus == ApplicationStatus.SCREENING && newStatus == ApplicationStatus.SHORTLISTED){
            return true;
        }

        if(currentStatus == ApplicationStatus.SHORTLISTED && newStatus == ApplicationStatus.INTERVIEW){
            return true;
        }

        if(currentStatus == ApplicationStatus.INTERVIEW && newStatus == ApplicationStatus.OFFER){
            return true;
        }

        if(currentStatus == ApplicationStatus.OFFER && newStatus == ApplicationStatus.HIRED){
            return true;
        }

        return false;
    }

    public String testAuthorizationCreateJob(OrganizationMember member){
        requirePermission(member, Permission.CREATE_JOB);
        return "User has permission to create a job";
    }
    
}
