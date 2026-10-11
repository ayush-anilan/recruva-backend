package com.recruva.web.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class ApplicationStatusHistoryResponse {
    
    private String message;
    private List<ApplicationStatusHistoryItemResponse> history;
    private boolean success;
}
