package com.recruva.web.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class OrganizationResponse {
    
    private UUID organizationId;
    private String message;
    private String organizationName;
    private boolean success;
}
