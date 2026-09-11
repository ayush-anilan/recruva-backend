package com.recruva.db.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@Table (name = "users")
public class User {
    
    @Id 
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private UUID id;

    @Column (name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name", nullable = false)
    private String lastName;

    @Column (name = "email", nullable = false, unique = true)
    private String email;
    
    @Column (name = "password_hash")
    private String passwordHash;

    @Column (name = "phone_number")
    private String phoneNumber;

    @Column (name = "is_active")
    private boolean isActive = true;

    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column (name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
