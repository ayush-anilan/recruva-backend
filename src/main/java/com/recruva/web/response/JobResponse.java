package com.recruva.web.response;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class JobResponse {
    private String message;
    private String jobTitle;
    private boolean success;
}
