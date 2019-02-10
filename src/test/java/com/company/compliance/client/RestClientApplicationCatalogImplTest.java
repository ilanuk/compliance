package com.company.compliance.client;

import com.company.compliance.client.dto.ApplicationCatalog;
import com.company.compliance.domain.ComplianceAttempt;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class RestClientApplicationCatalogImplTest {
    private RestClientApplicationCatalogImpl restClientApplicationCatalog;

    @Mock
    private RestTemplate restTemplate;

    private String url = "http://localhost:8083/api/applicationcatalog";

    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        restClientApplicationCatalog = new RestClientApplicationCatalogImpl(url, restTemplate);

    }
    @Test
    public void testRestClientApplicationCatalogWithProdValid() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        ApplicationCatalog applicationCatalog = new ApplicationCatalog(101l, "testApplication");
        given(restTemplate.getForObject(url + "/applications/" + complianceAttempt.getApplicationId(), ApplicationCatalog.class))
                .willReturn(applicationCatalog);

        //when
        boolean result = restClientApplicationCatalog.validateApplicationCatalog(complianceAttempt);
        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testRestClientApplicationCatalogWithProdNotValid() throws Exception {
        //given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        ApplicationCatalog applicationCatalog = null;
        Mockito
                .when(restTemplate.getForObject(url + "/applications/" + complianceAttempt.getApplicationId(), ApplicationCatalog.class))
                .thenReturn(applicationCatalog);

        //when
        boolean result = restClientApplicationCatalog.validateApplicationCatalog(complianceAttempt);
        //then
        assertThat(result).isEqualTo(false);
    }
}
