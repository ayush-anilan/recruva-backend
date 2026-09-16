package com.recruva.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class RegisterRequest {
    @NotBlank (message = "First name is required")
    private String firstName;

    @NotBlank (message = "Last name is required")
    private String lastName;

    @NotBlank
    @Email 
    private String email;

    @NotBlank 
    @Size (min = 8, message = "Password must be at least 8 characters long")
    private String password;
 
    @Size (min = 10, max = 15, message = "Phone number must be between 10 and 15 characters long")
    private String phoneNumber;
}
