package com.recruva.db.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table (name = "organization_candidates", uniqueConstraints = {
    @UniqueConstraint  (columnNames = {"candidate_id", "organization_id"})
})
public class OrganizationCandidate {

    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne 
    @JoinColumn (name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne 
    @JoinColumn (name = "organization_id", nullable = false)
    private Organization organization;

    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column (name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
