package com.company.compliance.client;

import com.company.compliance.client.dto.ChangeRecord;
import com.company.compliance.domain.ComplianceAttempt;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientChangeRecordImpl implements RestClientChangeRecord {
    private final RestTemplate restTemplate;

    private final String url;

    @Autowired
    public RestClientChangeRecordImpl(@Value("${compliance.changerecord.url}") String url,
                                            RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }
    @Override
    @HystrixCommand(fallbackMethod = "fallbackValidateChangeRecord")
    public boolean validateChangeRecord(ComplianceAttempt complianceAttempt) {
        ChangeRecord changeRecord = restTemplate.getForObject(url +"/checkchange/" + complianceAttempt.getChangeRecordId(),
                ChangeRecord.class);
        return changeRecord!=null ? changeRecord.isChangeRecordValid() : false;
    }
    public boolean fallbackValidateChangeRecord(ComplianceAttempt complianceAttempt) {
        return false;
    }

}
