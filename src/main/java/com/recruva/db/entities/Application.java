package com.recruva.db.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recruva.enums.ApplicationStatus;

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
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@Table (name = "applications", uniqueConstraints = {
    @UniqueConstraint (columnNames = {"organization_candidate_id", "job_id"})
})
public class Application {

    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne 
    @JoinColumn (name = "organization_candidate_id", nullable = false)
    private OrganizationCandidate organizationCandidate;

    @ManyToOne 
    @JoinColumn (name = "job_id", nullable = false)
    private Job job;

    @Enumerated (EnumType.STRING)
    @Column (name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @Column (name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column (name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
}
