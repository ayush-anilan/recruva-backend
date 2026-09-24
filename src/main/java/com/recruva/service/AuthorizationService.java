package com.recruva.service;

import org.springframework.stereotype.Service;

import com.recruva.db.entities.OrganizationMember;
import com.recruva.enums.Permission;
import com.recruva.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthorizationService {

    public void requirePermission(OrganizationMember member, Permission permission){

        switch (member.getRole()){
            case ORGANIZATION_ADMIN:
                if (permission == Permission.CREATE_JOB){
                    return;
                }
                break;
            case RECRUITER:
                if (permission == Permission.CREATE_JOB){
                    return;
                }
                break;
            case HIRING_MANAGER:
            case INTERVIEWER:
                break;
        }

        throw new ForbiddenException("User does not have permission: " + permission);
    }

    public String testAuthorizationCreateJob(OrganizationMember member){
        requirePermission(member, Permission.CREATE_JOB);
        return "User has permission to create a job";
    }
    
}
