package com.company.compliance.client;

import com.company.compliance.domain.ComplianceAttempt;

public interface RestClientApplicationCatalog {
    public boolean validateApplicationCatalog(ComplianceAttempt complianceAttempt);
}
