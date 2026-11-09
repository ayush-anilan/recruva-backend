package com.recruva.db.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruva.db.entities.OrganizationMember;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, UUID> {
    Optional<OrganizationMember> findByOrganization_IdAndUser_Id(UUID organizationId, UUID userId);

    List<OrganizationMember> findAllByUser_IdAndIsActiveTrue(UUID userId);
}
