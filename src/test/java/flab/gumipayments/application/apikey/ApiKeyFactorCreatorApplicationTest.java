package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.domain.apikey.ReIssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.contract.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static flab.gumipayments.application.apikey.ApiKeyIssueCreateCommand.*;
import static flab.gumipayments.application.apikey.ApiKeyReIssueCreateCommand.*;
import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ApiKeyFactorCreatorApplicationTest {

    @InjectMocks
    private ApiKeyFactorCreatorApplication factorCreateService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ApiKeyRepository apiKeyRepository;
    @Mock
    private ContractRepository contractRepository;

    private ApiKeyIssueCreateCommandBuilder createIssueCommandBuilder;
    private ApiKeyReIssueCreateCommandBuilder createReIssueCommandBuilder;

    @BeforeEach
    void setup() {
        createIssueCommandBuilder = ApiKeyIssueCreateCommand.builder();
        createReIssueCommandBuilder = ApiKeyReIssueCreateCommand.builder();
    }

    @Test
    @DisplayName("예외: 올바르지 않은 키 타입으로 발급 요청 시, api 키 발급이 실패한다. ")
    void invalidApiKeyType() {
        ApiKeyIssueCreateCommand createCommand = createIssueCommandBuilder.type("TEST1").build();

        assertThatThrownBy(() -> factorCreateService.getIssueFactor(createCommand))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공: 올바른 키 타입으로 발급 요청 시, api 키 발급이 성공한다. ")
    void apiKeyType() {
        ApiKeyIssueCreateCommand createCommand = createIssueCommandBuilder
                .type("TEST")
                .accountId(1L)
                .expireDate(now()).build();
        when(accountRepository.existsById(any())).thenReturn(true);
        when(apiKeyRepository.existsByAccountIdAndType(any(), any())).thenReturn(true);
        when(contractRepository.existsByAccountIdAndStatus(any(), any())).thenReturn(true);

        IssueFactor issueFactor = factorCreateService.getIssueFactor(createCommand);

        issueCommandCheck(issueFactor, createCommand);
    }


    @Test
    @DisplayName("예외: 올바르지 않은 키 타입으로 재발급 요청 시, api 키 재발급이 실패한다. ")
    void invalidReApiKeyType() {
        ApiKeyReIssueCreateCommand createCommand = createReIssueCommandBuilder.
                type("PROD1").build();

        assertThatThrownBy(() -> factorCreateService.getReIssueFactor(createCommand))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공: 올바른 키 타입으로 재발급 요청 시, api 키 재발급이 성공한다. ")
    void reApiKeyType() {
        ApiKeyReIssueCreateCommand createCommand = createReIssueCommandBuilder
                .type("TEST")
                .accountId(1L)
                .expireDate(now()).build();
        when(accountRepository.existsById(any())).thenReturn(true);
        when(apiKeyRepository.existsByAccountIdAndType(any(), any())).thenReturn(true);
        when(contractRepository.existsByAccountIdAndStatus(any(), any())).thenReturn(true);

        ReIssueFactor reIssueFactor = factorCreateService.getReIssueFactor(createCommand);

        reIssueCommandCheck(reIssueFactor, createCommand);
    }

    private void issueCommandCheck(IssueFactor issueFactor, ApiKeyIssueCreateCommand createCommand) {
        assertThat(issueFactor.getApiKeyType().name()).isEqualTo(createCommand.getType());
        assertThat(issueFactor.getExpireDate()).isEqualTo(createCommand.getExpireDate());
        assertThat(issueFactor.isApiKeyExist()).isTrue();
        assertThat(issueFactor.isAccountExist()).isTrue();
        assertThat(issueFactor.isContractCompleteExist()).isTrue();
    }


    private void reIssueCommandCheck(ReIssueFactor reIssueFactor, ApiKeyReIssueCreateCommand createCommand) {
        assertThat(reIssueFactor.getApiKeyType().name()).isEqualTo(createCommand.getType());
        assertThat(reIssueFactor.getExpireDate()).isEqualTo(createCommand.getExpireDate());
        assertThat(reIssueFactor.isApiKeyExist()).isTrue();
        assertThat(reIssueFactor.isAccountExist()).isTrue();
        assertThat(reIssueFactor.isContractCompleteExist()).isTrue();
    }
}