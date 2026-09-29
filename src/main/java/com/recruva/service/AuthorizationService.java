package com.recruva.service;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.recruva.db.entities.Job;
import com.recruva.db.entities.OrganizationMember;
import com.recruva.enums.Permission;
import com.recruva.enums.Role;
import com.recruva.exception.ForbiddenException;


@Service 
public class AuthorizationService {

    private static final Map<Role, Set<Permission>> ROLE_PERMISSIONS = Map.of(
        Role.ORGANIZATION_ADMIN, Set.of(Permission.CREATE_JOB, Permission.VIEW_JOB, Permission.UPDATE_JOB, Permission.MANAGE_JOB_STATUS, Permission.MANAGE_CANDIDATES),
        Role.RECRUITER, Set.of(Permission.CREATE_JOB, Permission.VIEW_JOB, Permission.UPDATE_JOB, Permission.MANAGE_JOB_STATUS, Permission.MANAGE_CANDIDATES),
        Role.HIRING_MANAGER, Set.of(Permission.VIEW_JOB),
        Role.INTERVIEWER, Set.of(Permission.VIEW_JOB)
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

    public String testAuthorizationCreateJob(OrganizationMember member){
        requirePermission(member, Permission.CREATE_JOB);
        return "User has permission to create a job";
    }
    
}
