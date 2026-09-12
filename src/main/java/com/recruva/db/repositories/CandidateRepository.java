package com.recruva.db.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruva.db.entities.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
    
}
