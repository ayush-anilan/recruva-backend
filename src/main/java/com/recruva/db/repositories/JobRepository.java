package com.recruva.db.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruva.db.entities.Job;
import com.recruva.db.entities.Organization;
import com.recruva.enums.JobStatus;

public interface JobRepository extends JpaRepository <Job, UUID> {
    Optional <Job> findByIdAndOrganization(UUID id, Organization organization);

    Optional <Job> findByIdAndStatus(UUID id, JobStatus status);

    List<Job> findByOrganization(Organization organization);

    List<Job> findByOrganizationAndStatus(Organization organization, JobStatus status);
}
