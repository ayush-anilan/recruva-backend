package com.recruva.db.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruva.db.entities.OrganizationCandidate;

public interface OrganizationCandidateRepository extends JpaRepository<OrganizationCandidate, UUID> {
    Optional<OrganizationCandidate> findByCandidate_IdAndOrganization_Id(UUID candidateId, UUID organizationId);
}
