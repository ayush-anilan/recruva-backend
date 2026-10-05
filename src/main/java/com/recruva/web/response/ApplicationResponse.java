package com.recruva.web.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class ApplicationResponse {
    
    private String message;
    private UUID applicationId;
    private boolean success;
}
