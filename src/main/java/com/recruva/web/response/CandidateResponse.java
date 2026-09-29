package com.recruva.web.response;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class CandidateResponse {
    private String message;
    private String candidateId;
    private boolean success;
}
