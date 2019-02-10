package com.company.compliance.client;

import com.company.compliance.client.dto.SecurityScan;
import com.company.compliance.domain.ComplianceAttempt;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientSecurityScanImpl implements RestClientSecurityScan{
    private final RestTemplate restTemplate;

    private final String url;

    @Autowired
    public RestClientSecurityScanImpl(@Value("${compliance.changerecord.url}") String url,
                                      RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallbackValidateSecuirityScan")
    @Override
    public boolean validateSecuirityScan(ComplianceAttempt complianceAttempt) {
        SecurityScan securityScan = restTemplate.getForObject(url +"/checksecurity/" + complianceAttempt.getApplicationId(),
                SecurityScan.class);
        return securityScan!=null ? securityScan.isSecurityStatusValid() : false;
    }
    public boolean fallbackValidateSecuirityScan(ComplianceAttempt complianceAttempt) {
        return false;
    }
}
