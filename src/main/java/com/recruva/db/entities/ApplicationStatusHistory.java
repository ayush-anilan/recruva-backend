package com.recruva.db.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recruva.enums.ApplicationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table (name = "application_status_history")
public class ApplicationStatusHistory {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "application_id", nullable = false)
    private Application application;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "organization_id", nullable = false)
    private Organization organization;

    @Enumerated (EnumType.STRING)
    @Column (name = "previous_status")
    private ApplicationStatus previousStatus;

    @Enumerated (EnumType.STRING)
    @Column (name = "new_status", nullable = false)
    private ApplicationStatus newStatus;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "changed_by_user_id", nullable = false)
    private User changedByUser;

    @Column (name = "changed_at", nullable = false)
    private LocalDateTime changedAt;
}
