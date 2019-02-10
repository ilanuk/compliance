package com.company.compliance.controller;

import com.company.compliance.domain.ComplianceAttempt;
import com.company.compliance.service.ComplianceAttemptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(ComplianceAttemptController.class)
public class ComplianceAttemptControllerTest {
    @MockBean
    private ComplianceAttemptService complianceAttemptService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<Boolean> jsonResult;
    private JacksonTester<ComplianceAttempt> jsonRequestAttempt;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void testAttemptProdSuccess() throws Exception {
        // given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        given(complianceAttemptService.checkCompliance(any(ComplianceAttempt.class)))
                .willReturn(true);
        //when
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/compliance/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(complianceAttempt).getJson()))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(
                        jsonResult.write(true).getJson()
                );
    }

    @Test
    public void testAttemptProdFailure() throws Exception {
        // given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "PROD", 101l, 2001l, false, new Date());
        given(complianceAttemptService.checkCompliance(any(ComplianceAttempt.class)))
                .willReturn(false);
        //when
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/compliance/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(complianceAttempt).getJson()))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString())
                .isEqualTo(
                        jsonResult.write(false).getJson()
                );
    }
    @Test
    public void testAttemptNonProdWarning() throws Exception {
        // given
        ComplianceAttempt complianceAttempt
                = new ComplianceAttempt(1, "DEV", 101l, 2001l, false, new Date());
        given(complianceAttemptService.checkCompliance(any(ComplianceAttempt.class)))
                .willReturn(false);
        //when
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/compliance/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(complianceAttempt).getJson()))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString())
                .isEqualTo(
                        jsonResult.write(false).getJson()
                );
    }
}
