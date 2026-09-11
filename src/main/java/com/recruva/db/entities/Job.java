package com.recruva.db.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recruva.enums.JobStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@Table (name = "jobs")
public class Job {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne 
    @JoinColumn (name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne 
    @JoinColumn (name = "created_by_user_id", nullable = false)
    private User createdByUser;

    @Column (name="title", nullable = false)
    private String title;

    @Column (name="description", nullable = false)
    private String description;

    @Column (name="location")
    private String location;

    @Column (name="category")
    private String category;    

    @Enumerated (EnumType.STRING)
    @Column (name="status", nullable = false)
    private JobStatus status = JobStatus.DRAFT;

    @Column (name = "posted_at")
    private LocalDateTime postedAt;

    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column (name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
