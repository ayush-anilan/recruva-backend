package com.recruva.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recruva.enums.ApplicationStatus;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class ApplicationListItemResponse {
    private UUID applicationId;
    
    private UUID candidateId;
    private String candidateName;
    private String candidateEmail;
    
    private UUID jobId;
    private String jobTitle;

    private ApplicationStatus status;
    
    private LocalDateTime appliedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
