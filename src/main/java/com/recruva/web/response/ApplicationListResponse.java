package com.recruva.web.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class ApplicationListResponse {
    private String message;
    private List<ApplicationListItemResponse> applications;
    private boolean success;
}
