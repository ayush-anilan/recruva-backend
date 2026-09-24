package com.recruva.web.response;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class OrganizationResponse {
    
    private String message;
    private String organizationName;
    private boolean success;
}
