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
@Table (name = "organizations")
public class Organization {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "address")
    private String address;
    
    @Column (name = "website")
    private String website;

    @Column (name = "logo_url")
    private String logoUrl;

    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column (name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
