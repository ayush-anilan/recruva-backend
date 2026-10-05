package com.recruva.web.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class ApplicationRequest {
    @NotNull (message = "Candidate ID is required")
    private UUID candidateId;
    @NotNull (message = "Job ID is required")
    private UUID jobId;
}
