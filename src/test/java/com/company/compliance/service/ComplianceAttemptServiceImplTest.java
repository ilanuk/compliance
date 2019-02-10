package com.company.compliance.service;

import com.company.compliance.client.RestClientApplicationCatalog;
import com.company.compliance.client.RestClientChangeRecord;
import com.company.compliance.client.RestClientSecurityScan;
import com.company.compliance.domain.ComplianceAttempt;
import com.company.compliance.repository.ComplianceAttemptRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ComplianceAttemptServiceImplTest {
    private ComplianceAttemptServiceImpl complianceService;

    @Mock
    private  RestClientChangeRecord restClientChangeRecord;

    @Mock
    private RestClientSecurityScan restClientSecurityScan;

    @Mock
    private RestClientApplicationCatalog restClientApplicationCatalog;

    @Mock
    private ComplianceAttemptRepository complianceAttemptRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        complianceService = new ComplianceAttemptServiceImpl(restClientChangeRecord,
                restClientSecurityScan,
                restClientApplicationCatalog,
                complianceAttemptRepository);
    }

    @Test
    public void testComplianceServiceImplProdCase1() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        ComplianceAttempt complianceExpectedToPersist
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, true, new Date());
        given(restClientChangeRecord.validateChangeRecord(complianceAttempt))
                .willReturn(true);
        given(restClientSecurityScan.validateSecuirityScan(complianceAttempt))
                .willReturn(true);
        given(restClientApplicationCatalog.validateApplicationCatalog(complianceAttempt))
                .willReturn(true);
        //when
        boolean result = complianceService.checkCompliance(complianceAttempt);
        //then
        assertThat(result).isEqualTo(true);
        Mockito.verify(restClientChangeRecord, times(1)).validateChangeRecord(complianceAttempt);
        Mockito.verify(restClientSecurityScan, times(1)).validateSecuirityScan(complianceAttempt);
        Mockito.verify(restClientApplicationCatalog, times(1)).validateApplicationCatalog(complianceAttempt);
        Mockito.verify(complianceAttemptRepository, times(1)).save(complianceExpectedToPersist);
    }

    @Test
    public void testComplianceServiceImplProdCase2() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        given(restClientChangeRecord.validateChangeRecord(complianceAttempt))
                .willReturn(false);
        given(restClientSecurityScan.validateSecuirityScan(complianceAttempt))
                .willReturn(true);
        given(restClientApplicationCatalog.validateApplicationCatalog(complianceAttempt))
                .willReturn(true);
        //when
        boolean result = complianceService.checkCompliance(complianceAttempt);
        //then
        assertThat(result).isEqualTo(false);
        Mockito.verify(restClientChangeRecord, times(1)).validateChangeRecord(complianceAttempt);
        Mockito.verify(restClientSecurityScan, times(0)).validateSecuirityScan(complianceAttempt);
        Mockito.verify(restClientApplicationCatalog, times(0)).validateApplicationCatalog(complianceAttempt);
        Mockito.verify(complianceAttemptRepository, times(1)).save(complianceAttempt);
    }

    @Test
    public void testComplianceServiceImplNonProd() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "DEV", 101l, 2001l, false, new Date());
        given(restClientChangeRecord.validateChangeRecord(complianceAttempt))
                .willReturn(false);
        given(restClientSecurityScan.validateSecuirityScan(complianceAttempt))
                .willReturn(false);
        given(restClientApplicationCatalog.validateApplicationCatalog(complianceAttempt))
                .willReturn(false);
        //when
        boolean result = complianceService.checkCompliance(complianceAttempt);
        //then
        assertThat(result).isEqualTo(false);
        Mockito.verify(restClientChangeRecord, times(0)).validateChangeRecord(complianceAttempt);
        Mockito.verify(restClientSecurityScan, times(0)).validateSecuirityScan(complianceAttempt);
        Mockito.verify(restClientApplicationCatalog, times(0)).validateApplicationCatalog(complianceAttempt);
        Mockito.verify(complianceAttemptRepository, times(1)).save(complianceAttempt);
    }

}
