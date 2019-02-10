package com.company.compliance.service;

import com.company.compliance.client.RestClientApplicationCatalog;
import com.company.compliance.client.RestClientChangeRecord;
import com.company.compliance.client.RestClientSecurityScan;
import com.company.compliance.domain.ComplianceAttempt;
import com.company.compliance.repository.ComplianceAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplianceAttemptServiceImpl implements ComplianceAttemptService {

    private  final RestClientChangeRecord restClientChangeRecord;

    private final RestClientSecurityScan restClientSecurityScan;

    private final RestClientApplicationCatalog restClientApplicationCatalog;

    private final ComplianceAttemptRepository complianceAttemptRepository;

    @Autowired
    ComplianceAttemptServiceImpl(final RestClientChangeRecord restClientChangeRecord,
                                 final RestClientSecurityScan restClientSecurityScan,
                                 final RestClientApplicationCatalog restClientApplicationCatalog,
                                 final ComplianceAttemptRepository complianceAttemptRepository) {
        this.restClientChangeRecord = restClientChangeRecord;
        this.restClientSecurityScan = restClientSecurityScan;
        this.restClientApplicationCatalog = restClientApplicationCatalog;
        this.complianceAttemptRepository = complianceAttemptRepository;
    }

    @Override
    public boolean checkCompliance(ComplianceAttempt attempt) {
        boolean result = false;
        if (attempt != null && attempt.getRegion().equalsIgnoreCase("PROD")) {
            result = (restClientChangeRecord.validateChangeRecord(attempt) &&
                    restClientSecurityScan.validateSecuirityScan(attempt) &&
                    restClientApplicationCatalog.validateApplicationCatalog(attempt));
        }
        ComplianceAttempt updatedComplianceAttempt = new ComplianceAttempt(
                attempt.getId(),
                attempt.getRegion(),
                attempt.getApplicationId(),
                attempt.getChangeRecordId(),
                result,
                attempt.getTimeAttempted());
        complianceAttemptRepository.save(updatedComplianceAttempt);
        return result;
    }
}
