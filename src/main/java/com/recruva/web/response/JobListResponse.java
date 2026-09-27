package com.recruva.web.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class JobListResponse {
    private String message;
    private List<JobListItemResponse> jobs;
    private boolean success;
}
