package com.company.compliance.client;

import com.company.compliance.client.dto.SecurityScan;
import com.company.compliance.domain.ComplianceAttempt;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class RestClientSecurityScanImplTest {
    private RestClientSecurityScanImpl restClientSecurityScanImpl;

    @Mock
    private RestTemplate restTemplate;

    private String url = "http://localhost:8082/api/securityscan";

    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        restClientSecurityScanImpl = new RestClientSecurityScanImpl(url, restTemplate);

    }
    @Test
    public void testRestClientSecurityScanWithProdValid() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        SecurityScan securityScan = new SecurityScan(101l, true);
        given(restTemplate.getForObject(url + "/checksecurity/" + complianceAttempt.getApplicationId(), SecurityScan.class))
                .willReturn(securityScan);

        //when
        boolean result = restClientSecurityScanImpl.validateSecuirityScan(complianceAttempt);
        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testRestClientSecurityScanWithProdNotValid() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        SecurityScan securityScan = new SecurityScan(101l, false);
        given(restTemplate.getForObject(url + "/checksecurity/" + complianceAttempt.getApplicationId(), SecurityScan.class))
                .willReturn(securityScan);

        //when
        boolean result = restClientSecurityScanImpl.validateSecuirityScan(complianceAttempt);
        //then
        assertThat(result).isEqualTo(false);
    }

}
