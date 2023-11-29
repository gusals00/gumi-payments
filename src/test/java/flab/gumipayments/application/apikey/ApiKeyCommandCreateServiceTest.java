package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.ApiKeyCreateCommand;
import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;
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
class ApiKeyCommandCreateServiceTest {

    @InjectMocks
    private ApiKeyCommandCreateService commandCreateService;
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

        assertThatThrownBy(() -> commandCreateService.getIssueCommand(createCommand))
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

        ApiKeyIssueCommand issueCommand = commandCreateService.getIssueCommand(createCommand);

        issueCommandCheck(issueCommand, createCommand);
    }


    @Test
    @DisplayName("예외: 올바르지 않은 키 타입으로 재발급 요청 시, api 키 재발급이 실패한다. ")
    void invalidReApiKeyType() {
        ApiKeyReIssueCreateCommand createCommand = createReIssueCommandBuilder.
                type("PROD1").build();

        assertThatThrownBy(() -> commandCreateService.getReIssueCommand(createCommand))
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

        ApiKeyReIssueCommand issueCommand = commandCreateService.getReIssueCommand(createCommand);

        reIssueCommandCheck(issueCommand, createCommand);
    }

    private void issueCommandCheck(ApiKeyIssueCommand issueCommand, ApiKeyIssueCreateCommand createCommand) {
        assertThat(issueCommand.getApiKeyType().name()).isEqualTo(createCommand.getType());
        assertThat(issueCommand.getExpireDate()).isEqualTo(createCommand.getExpireDate());
        assertThat(issueCommand.isApiKeyExist()).isTrue();
        assertThat(issueCommand.isAccountExist()).isTrue();
        assertThat(issueCommand.isContractCompleteExist()).isTrue();
    }


    private void reIssueCommandCheck(ApiKeyReIssueCommand reIssueCommand, ApiKeyReIssueCreateCommand createCommand) {
        assertThat(reIssueCommand.getApiKeyType().name()).isEqualTo(createCommand.getType());
        assertThat(reIssueCommand.getExpireDate()).isEqualTo(createCommand.getExpireDate());
        assertThat(reIssueCommand.isApiKeyExist()).isTrue();
        assertThat(reIssueCommand.isAccountExist()).isTrue();
        assertThat(reIssueCommand.isContractCompleteExist()).isTrue();
    }
}