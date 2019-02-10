package com.company.compliance.service;

import com.company.compliance.domain.ComplianceAttempt;

public interface ComplianceAttemptService {
    public boolean checkCompliance(ComplianceAttempt attempt);

}
