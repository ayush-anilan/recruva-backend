package com.recruva.db.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruva.db.entities.Application;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByOrganizationCandidate_IdAndJob_Id(UUID organizationCandidateId, UUID jobId);
}
