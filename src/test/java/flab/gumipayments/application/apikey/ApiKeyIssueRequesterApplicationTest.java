package flab.gumipayments.application.apikey;

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

import static flab.gumipayments.application.apikey.ApiKeyIssueCommand.*;
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

    private Condition<ApiKeyIssueCommand> trueCondition = command -> true;
    private Condition<ApiKeyIssueCommand> falseCondition = command -> false;

    private ApiKeyResponseBuilder apiKeyResponseBuilder;

    private ApiKeyIssueCommandBuilder apiKeyIssueCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyResponseBuilder = ApiKeyResponse.builder();
        apiKeyIssueCommandBuilder = ApiKeyIssueCommand.builder();
    }

    @Test
    @DisplayName("예외: 발급 조건을 만족하지 못하면 API 키 발급에 실패한다.")
    void issueApiKeyFailByCondition() {
        sut.setApiKeyIssueCondition(falseCondition);

        assertThatThrownBy(() -> sut.issueApiKey(apiKeyIssueCommandBuilder.build()))
                .isInstanceOf(ApiKeyIssueException.class)
                .hasMessage("api 키 발급 조건이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("성공: API 키 발급을 성공한다.")
    void issueApiKey() {
        when(apiKeyCreatorApplication.create(any())).thenReturn(apiKeyResponseBuilder.build());
        sut.setApiKeyIssueCondition(trueCondition);

        sut.issueApiKey(apiKeyIssueCommandBuilder.build());

        verify(apiKeyRepository).save(any());
    }
}