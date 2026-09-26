package com.recruva.web.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class JobRequest {

    @NotBlank (message = "Title is required")
    private String title;
    @NotBlank (message = "Description is required")
    private String description;
    private String location;
    private String category;
}
