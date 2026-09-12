package com.recruva.db.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruva.db.entities.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    
}
