package com.company.compliance.repository;

import com.company.compliance.domain.ComplianceAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceAttemptRepository extends JpaRepository<ComplianceAttempt, Integer> {
}

