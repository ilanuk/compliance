package com.company.compliance.client;

import com.company.compliance.domain.ComplianceAttempt;

public interface RestClientChangeRecord {

    public boolean validateChangeRecord(ComplianceAttempt complianceAttempt);
}
