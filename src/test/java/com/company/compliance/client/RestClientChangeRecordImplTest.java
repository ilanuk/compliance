package com.company.compliance.client;

import com.company.compliance.client.dto.ChangeRecord;
import com.company.compliance.domain.ComplianceAttempt;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class RestClientChangeRecordImplTest {
    private RestClientChangeRecordImpl restClientChangeRecordImpl;

    @Mock
    private RestTemplate restTemplate;

    private String url = "http://localhost:8081/api/changerecord";

    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        restClientChangeRecordImpl = new RestClientChangeRecordImpl(url, restTemplate);

    }
    @Test
    public void testRestClientChangeRecordWithProdValid() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        ChangeRecord changeRecord = new ChangeRecord(2001l, true);
        given(restTemplate.getForObject(url + "/checkchange/" + complianceAttempt.getChangeRecordId(), ChangeRecord.class))
                .willReturn(changeRecord);

        //when
        boolean result = restClientChangeRecordImpl.validateChangeRecord(complianceAttempt);
        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testRestClientChangeRecordWithProdNotValid() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        ChangeRecord changeRecord = new ChangeRecord(2001l, false);
        given(restTemplate.getForObject(url + "/checkchange/" + complianceAttempt.getChangeRecordId(), ChangeRecord.class))
                .willReturn(changeRecord);

        //when
        boolean result = restClientChangeRecordImpl.validateChangeRecord(complianceAttempt);
        //then
        assertThat(result).isEqualTo(false);
    }
}
