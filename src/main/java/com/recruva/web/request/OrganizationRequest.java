package com.recruva.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class OrganizationRequest {
    
    @NotBlank(message = "Organization name is required")
    private String name;

    @NotBlank (message = "Address is required")
    private String address;

    private String website;

    private String logoUrl;


}
