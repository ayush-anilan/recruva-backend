package com.recruva.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recruva.enums.ApplicationStatus;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class ApplicationStatusHistoryItemResponse {
    
    private UUID id;
    private ApplicationStatus previousStatus;
    private ApplicationStatus newStatus;
    private UUID changedByUserId;
    private LocalDateTime changedAt;
}
