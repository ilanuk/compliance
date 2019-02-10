package com.company.compliance.client;

import com.company.compliance.client.dto.ApplicationCatalog;
import com.company.compliance.domain.ComplianceAttempt;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientApplicationCatalogImpl implements RestClientApplicationCatalog{

    private final RestTemplate restTemplate;

    private final String url;


    @Autowired
    public RestClientApplicationCatalogImpl(@Value("${compliance.applicationcatalog.url}") String url,
                                            RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallbackValidateApplicationCatalog")
    @Override
    public boolean validateApplicationCatalog(ComplianceAttempt complianceAttempt) {
        ApplicationCatalog applicationCatalog =
                restTemplate.getForObject(url +"/applications/" + complianceAttempt.getApplicationId(),
                ApplicationCatalog.class);
        return applicationCatalog!=null ? true : false;
    }
    public boolean fallbackValidateApplicationCatalog(ComplianceAttempt complianceAttempt) {
        return false;
    }

}
