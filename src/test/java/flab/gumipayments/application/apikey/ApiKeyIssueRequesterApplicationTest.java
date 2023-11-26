package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.apikey.ApiKeyResponse;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static flab.gumipayments.domain.apikey.ApiKeyIssueCommand.*;
import static flab.gumipayments.domain.apikey.ApiKeyReIssueCommand.*;
import static flab.gumipayments.domain.apikey.ApiKeyResponse.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiKeyIssueRequesterApplicationTest {

    @InjectMocks
    private ApiKeyIssueRequesterApplication sut;

    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private ApiKeyCreatorApplication apiKeyCreatorApplication;

    private Condition<ApiKeyIssueCommand> trueIssueCondition = command -> true;
    private Condition<ApiKeyIssueCommand> falseIssueCondition = command -> false;


    private Condition<ApiKeyReIssueCommand> trueReIssueCondition = command -> true;
    private Condition<ApiKeyReIssueCommand> falseReIssueCondition = command -> false;

    private ApiKeyResponseBuilder apiKeyResponseBuilder;

    private ApiKeyIssueCommandBuilder apiKeyIssueCommandBuilder;
    private ApiKeyReIssueCommandBuilder apiKeyReIssueCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyResponseBuilder = ApiKeyResponse.builder();
        apiKeyIssueCommandBuilder = ApiKeyIssueCommand.builder();
        apiKeyReIssueCommandBuilder = ApiKeyReIssueCommand.builder();
    }

    @Test
    @DisplayName("예외: 발급 조건을 만족하지 못하면 API 키 발급에 실패한다.")
    void issueApiKeyFailByCondition() {
        sut.setApiKeyIssueCondition(falseIssueCondition);

        assertThatThrownBy(() -> sut.issueApiKey(apiKeyIssueCommandBuilder.build()))
                .isInstanceOf(ApiKeyIssueException.class)
                .hasMessage("api 키 발급 조건이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("성공: API 키 발급을 성공한다.")
    void issueApiKey() {
        when(apiKeyCreatorApplication.create(any())).thenReturn(apiKeyResponseBuilder.build());
        sut.setApiKeyIssueCondition(trueIssueCondition);

        sut.issueApiKey(apiKeyIssueCommandBuilder.build());

        verify(apiKeyRepository).save(any());
    }

    @Test
    @DisplayName("예외: 재발급 조건을 만족하지 못하면 API 키 재발급에 실패한다.")
    void reIssueApiKeyFailByCondition() {
        sut.setApiKeyReIssueCondition(falseReIssueCondition);

        assertThatThrownBy(() -> sut.reIssueApiKey(apiKeyReIssueCommandBuilder.build()))
                .isInstanceOf(ApiKeyIssueException.class)
                .hasMessage("api 키 재발급 조건이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("예외: 이전에 발급받은 API키가 존재하지 않으면 API 키 재발급에 실패한다.")
    void reIssueApiKeyFail02ByApiKeyExist() {
        when(apiKeyRepository.findByAccountIdAndType(any(),any())).thenReturn(Optional.empty());
        sut.setApiKeyReIssueCondition(trueReIssueCondition);


        assertThatThrownBy(() -> sut.reIssueApiKey(apiKeyReIssueCommandBuilder.build()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("api키가 존재하지 않습니다.");
    }
}