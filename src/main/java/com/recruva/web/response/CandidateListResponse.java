package com.recruva.web.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class CandidateListResponse {
    private String message;
    private List<CandidateListItemResponse> candidates;
    private boolean success;
}
