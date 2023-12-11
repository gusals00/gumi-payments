package flab.gumipayments.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gumipayments.apifirst.openapi.apikey.domain.ApiKeyIssueRequest;
import flab.gumipayments.apifirst.openapi.apikey.domain.ApiKeyReIssueRequest;
import flab.gumipayments.application.apikey.ApiKeyFactorCreatorApplication;
import flab.gumipayments.application.apikey.ApiKeyIssueException;
import flab.gumipayments.application.apikey.ApiKeyIssueRequesterApplication;
import flab.gumipayments.application.apikey.ApiKeyReIssueRequesterApplication;
import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyPair;
import flab.gumipayments.domain.apikey.ReIssueFactor;
import flab.gumipayments.infrastructure.WebMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static flab.gumipayments.domain.apikey.ApiKeyPair.*;
import static flab.gumipayments.domain.apikey.IssueFactor.*;
import static flab.gumipayments.domain.apikey.ReIssueFactor.*;
import static flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {ApiKeyController.class},
        excludeFilters =
                {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebMvcConfig.class})}
)
class ApiKeyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ApiKeyIssueRequesterApplication issueRequesterApplication;
    @MockBean
    private ApiKeyReIssueRequesterApplication reIssueRequesterApplication;
    @MockBean
    private ApiKeyFactorCreatorApplication commandCreateService;

    private ApiKeyPairBuilder apiKeyPairBuilder;
    private IssueFactorBuilder issueCommandBuilder;
    private ReIssueFactorBuilder reIssueCommandBuilder;


    @BeforeEach
    void setup() throws JsonProcessingException {
        apiKeyPairBuilder = ApiKeyPair.builder();
        issueCommandBuilder = IssueFactor.builder();
        reIssueCommandBuilder = ReIssueFactor.builder();
    }

    @Test
    @WithMockUser
    @DisplayName("예외: API 키 발급 조건을 만족하지 못하면 API 키 발급 요청은 실패한다.")
    void apiKeyIssueFail01() throws Exception {
        when(commandCreateService.getIssueFactor(any())).thenReturn(issueCommandBuilder.build());
        when(issueRequesterApplication.issueApiKey(any())).thenThrow(new ApiKeyIssueException());

        mockMvc.perform(post("/api/api-key")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(issueRequestBody("TEST", 1L)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(INVALID_STATUS.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: API 키 타입이 비어 있는 경우 API 키 발급 요청은 실패한다.")
    void apiKeyIssueBindFail01() throws Exception {
        mockMvc.perform(post("/api/api-key")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(issueRequestBody("", 1L)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BINDING.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("성공: API 키 발급을 성공한다.")
    void apiKeyIssue() throws Exception {
        when(commandCreateService.getIssueFactor(any())).thenReturn(issueCommandBuilder.build());
        when(issueRequesterApplication.issueApiKey(any())).thenReturn(apiKeyPairBuilder.build());

        mockMvc.perform(post("/api/api-key")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(issueRequestBody("TEST", 1L)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("예외: API 키 재발급 조건을 만족하지 못하면 API 키 재발급 요청은 실패한다.")
    void apiKeyReIssueFail01() throws Exception {
        when(commandCreateService.getReIssueFactor(any())).thenReturn(reIssueCommandBuilder.build());
        when(reIssueRequesterApplication.reIssueApiKey(any())).thenThrow(new ApiKeyIssueException());

        mockMvc.perform(post("/api/api-key/re")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reIssueRequestBody("TEST", 1L)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(INVALID_STATUS.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: API 키 타입이 비어 있는 경우 API 키 재발급 요청은 실패한다.")
    void apiKeyReIssueBindFail01() throws Exception {
        mockMvc.perform(post("/api/api-key/re")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reIssueRequestBody("", 1L)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BINDING.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("성공: API 키 재발급을 성공한다.")
    void apiKeyReIssue() throws Exception {
        when(commandCreateService.getReIssueFactor(any())).thenReturn(reIssueCommandBuilder.build());
        when(reIssueRequesterApplication.reIssueApiKey(any())).thenReturn(apiKeyPairBuilder.build());

        mockMvc.perform(post("/api/api-key/re")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reIssueRequestBody("PROD", 1L)))
                .andExpect(status().isOk());
    }

    private String issueRequestBody(String keyType, Long accountId) throws JsonProcessingException {
        return mapper.writeValueAsString(ApiKeyIssueRequest.builder()
                .keyType(keyType)
                .accountId(accountId).build()
        );
    }

    private String reIssueRequestBody(String keyType, Long accountId) throws JsonProcessingException {
        return mapper.writeValueAsString(ApiKeyReIssueRequest.builder()
                .keyType(keyType)
                .accountId(accountId).build()
        );
    }
}