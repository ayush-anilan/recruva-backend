package com.recruva.db.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recruva.enums.Role;

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
@Table (name = "organization_members", uniqueConstraints = {
    @UniqueConstraint (columnNames = {"user_id", "organization_id"})
})
public class OrganizationMember {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false, updatable = false)
    private UUID id;

    @JoinColumn (name = "user_id", nullable = false)
    @ManyToOne  
    private User user;

    @JoinColumn (name = "organization_id", nullable = false)
    @ManyToOne 
    private Organization organization;

    @Enumerated (EnumType.STRING)
    @Column (name = "role", nullable = false)
    private Role role;

    @Column (name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column (name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
