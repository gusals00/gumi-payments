package flab.gumipayments.infrastructure.apikey;

import flab.gumipayments.domain.apikey.ApiKey;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static flab.gumipayments.domain.apikey.ApiKey.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiKeyInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApiKeyDecoder apiKeyDecoder;

    @InjectMocks
    private ApiKeyInterceptor sut;

    private ApiKeyBuilder apiKeyBuilder;
    private String secretKey;

    @BeforeEach
    void setup() {
        apiKeyBuilder = ApiKey.builder();
        secretKey = "sk_931019203_21390912_31223";
    }

    @Test
    @DisplayName("예외: 결제관련 API 호출 시 존재하지 않는 ApiKey는 인증에 실패한다.")
    void notFoundApiKey() {
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(secretKey);
        when(apiKeyRepository.findBySecretKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.preHandle(request, null, null))
                .isInstanceOf(ApiKeyNotFoundException.class);
    }

    @Test
    @DisplayName("예외: 결제관련 API 호출 시 만료된 ApiKey는 인증에 실패한다.")
    void expiredApiKey() {
        ApiKey apikey = apiKeyBuilder.expireDate(LocalDateTime.now().minusMonths(1)).build();
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(secretKey);
        when(apiKeyRepository.findBySecretKey(any())).thenReturn(Optional.of(apikey));

        assertThatThrownBy(() -> sut.preHandle(request, null, null))
                .isInstanceOf(ApiKeyExpiredException.class);
    }

    @Test
    @DisplayName("성공: 결제관련 API 호출 시 ApiKey 인증에 성공한다.")
    void preHandleSuccess() {
        ApiKey apikey = apiKeyBuilder.expireDate(LocalDateTime.now().plusDays(1)).build();
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(secretKey);
        when(apiKeyRepository.findBySecretKey(any())).thenReturn(Optional.of(apikey));

        boolean isValid = sut.preHandle(request, null, null);

        assertThat(isValid).isTrue();
    }
}