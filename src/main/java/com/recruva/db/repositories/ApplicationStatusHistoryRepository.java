package com.recruva.db.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruva.db.entities.ApplicationStatusHistory;

public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, UUID> {
    
    List<ApplicationStatusHistory> findByApplication_IdAndOrganization_IdOrderByChangedAtDesc(UUID applicationId, UUID organizationId);
}
