package com.recruva.db.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@Table (name = "candidates")
public class Candidate {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne 
    @JoinColumn (name = "user_id", unique = true)
    private User user;

    @Column (name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name", nullable = false)
    private String lastName;

    @Column (name = "email", nullable = false)
    private String email;

    @Column (name = "phone_number")
    private String phoneNumber;

    @Column (name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column (name="updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
