package com.recruva.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recruva.enums.JobStatus;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class JobListItemResponse {
    
    private UUID id;
    private String title;
    private String description;
    private String location;
    private String category;
    private JobStatus status;
    private LocalDateTime postedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
