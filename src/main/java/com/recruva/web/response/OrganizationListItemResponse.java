package com.recruva.web.response;

import java.util.UUID;

import com.recruva.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class OrganizationListItemResponse {
    
    private UUID organizationId;
    private String organizationName;
    private String address;
    private String website;
    private String logoUrl;
    private Role role; // Role of the user in the organization
}
