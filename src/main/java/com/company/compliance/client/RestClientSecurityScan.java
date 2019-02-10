package com.company.compliance.client;

import com.company.compliance.domain.ComplianceAttempt;

public interface RestClientSecurityScan {

    public boolean validateSecuirityScan(ComplianceAttempt complianceAttempt);
}
