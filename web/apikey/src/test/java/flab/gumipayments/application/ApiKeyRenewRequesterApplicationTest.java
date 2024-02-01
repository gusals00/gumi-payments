package flab.gumipayments.application;

import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.ApiKey;
import flab.gumipayments.domain.ApiKeyRepository;
import flab.gumipayments.infrastructure.Sender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static flab.gumipayments.application.ApiKeyRenewCommand.*;
import static flab.gumipayments.domain.ApiKey.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiKeyRenewRequesterApplicationTest {

    private ApiKeyRenewCommandBuilder renewCommandBuilder;
    private ApiKeyBuilder apiKeyBuilder;
    private Account.AccountBuilder accountBuilder;

    @InjectMocks
    private ApiKeyRenewRequesterApplication sut;
    @Mock
    private ApiKeyRepository apiKeyRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Sender sender;

    @BeforeEach
    void setup() {
        renewCommandBuilder = ApiKeyRenewCommand.builder();
        apiKeyBuilder = ApiKey.builder();
        accountBuilder = Account.builder();
    }

    @Test
    @DisplayName("예외: 갱신할 API 키가 존재하지 않으면 API 키 갱신을 실패한다.")
    void renewApiKeyFail01() {
        when(apiKeyRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(()->sut.renew(renewCommandBuilder.build()))
                .isInstanceOf(NotFoundSystemException.class)
                .hasMessage("api키가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("예외: 갱신 요청을 한 계정이 존재하지 않으면 API 키 갱신을 실패한다.")
    void renewApiKeyFail02() {
        LocalDateTime extendDate = LocalDateTime.now().plusDays(30);
        ApiKey apiKey = apiKeyBuilder.expireDate(extendDate.minusDays(1)).build();
        when(apiKeyRepository.findById(any())).thenReturn(Optional.ofNullable(apiKey));
        when(accountRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(()->sut.renew(renewCommandBuilder.extendDate(extendDate).build()))
                .isInstanceOf(NotFoundSystemException.class)
                .hasMessage("account가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("성공: API 키 갱신을 성공한다.")
    void renewApiKey() {
        LocalDateTime extendDate = LocalDateTime.now().plusDays(30);
        ApiKeyRenewCommand renewCommand = renewCommandBuilder.extendDate(extendDate).build();
        ApiKey apiKey = apiKeyBuilder.expireDate(extendDate.minusDays(1)).build();
        when(apiKeyRepository.findById(any())).thenReturn(Optional.ofNullable(apiKey));
        when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(accountBuilder.build()));
        doNothing().when(sender).sendApiKeyRenewRequest(any(),any());

        sut.renew(renewCommand);

        assertThat(renewCommand.getExtendDate().isEqual(apiKey.getExpireDate())).isTrue();
        verify(sender).sendApiKeyRenewRequest(any(),any());
    }
}