package com.recruva.web.request;

import com.recruva.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class ApplicationStatusUpdateRequest {
    
    @NotNull (message = "Status is required")
    private ApplicationStatus status;
}
