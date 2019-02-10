package com.company.compliance.controller;

import com.company.compliance.domain.ComplianceAttempt;
import com.company.compliance.service.ComplianceAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compliance")
public class ComplianceAttemptController {
    private final ComplianceAttemptService complianceAttemptService;

    @Autowired
    ComplianceAttemptController(final ComplianceAttemptService complianceService) {
        this.complianceAttemptService = complianceService;
    }

    @PostMapping("/attempts")
    public ResponseEntity<Boolean> checkCompliance(@RequestBody ComplianceAttempt complianceAttempt) {
        boolean result = complianceAttemptService.checkCompliance(complianceAttempt);
        ResponseEntity<Boolean> responseEntity;
        if(complianceAttempt.getRegion().equalsIgnoreCase("PROD")) {
            responseEntity = result ?
                    new ResponseEntity(true, HttpStatus.OK) :
                    new ResponseEntity(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            responseEntity =  new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
