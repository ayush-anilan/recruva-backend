package com.recruva.web.request;

import com.recruva.enums.JobStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class JobStatusUpdateRequest {
    @NotNull (message = "Status is required")
    private JobStatus status;
}
