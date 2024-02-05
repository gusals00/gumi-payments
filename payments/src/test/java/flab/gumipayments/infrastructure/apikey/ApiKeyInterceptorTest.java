
package flab.gumipayments.infrastructure.apikey;

import flab.gumipayments.domain.ApiKey;
import flab.gumipayments.domain.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;

import static flab.gumipayments.domain.ApiKey.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiKeyInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private KeyEncrypt keyEncrypt;

    @Mock
    private ApiKeyDecoder apiKeyDecoder;

    @InjectMocks
    private ApiKeyInterceptor sut;

    private ApiKeyBuilder apiKeyBuilder;
    private String secretKey;
    private String clientKey;

    @BeforeEach
    void setup() {
        apiKeyBuilder = ApiKey.builder();
        secretKey = "sk_931019203_21390912_31223";
        clientKey = "ck_931019203_21390912_21223";
    }

    @Test
    @DisplayName("예외: 결제 관련 API 호출 시 존재하지 않는 Secret Key는 인증에 실패한다.")
    void notFoundSecretKey() throws NoSuchMethodException {
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(secretKey);
        when(apiKeyRepository.findBySecretKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.preHandle(request, null, getHandlerMethod("useSecretKeyPair")))
                .isInstanceOf(ApiKeyNotFoundException.class)
                .hasMessage("존재하지 않는 Secret ApiKey 입니다.");
    }

    @Test
    @DisplayName("예외: 결제 관련 API 호출 시 존재하지 않는 Client Key는 인증에 실패한다.")
    void notFoundClientKey() throws NoSuchMethodException {
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(clientKey);
        when(apiKeyRepository.findByClientKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.preHandle(request, null, getHandlerMethod("useClientKeyPair")))
                .isInstanceOf(ApiKeyNotFoundException.class)
                .hasMessage("존재하지 않는 Client ApiKey 입니다.");
    }

    @Test
    @DisplayName("예외: 결제 관련 API 호출 시 만료된 Secret Key는 인증에 실패한다.")
    void expiredSecretKey() {
        ApiKey apikey = apiKeyBuilder.expireDate(LocalDateTime.now().minusMonths(1)).build();
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(secretKey);
        when(apiKeyRepository.findBySecretKey(any())).thenReturn(Optional.of(apikey));

        assertThatThrownBy(() -> sut.preHandle(request, null, getHandlerMethod("useSecretKeyPair")))
                .isInstanceOf(ApiKeyExpiredException.class);
    }

    @Test
    @DisplayName("예외: 결제 관련 API 호출 시 만료된 Client Key는 인증에 실패한다.")
    void expiredClientKey() {
        ApiKey apikey = apiKeyBuilder.expireDate(LocalDateTime.now().minusMonths(1)).build();
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(clientKey);
        when(apiKeyRepository.findByClientKey(any())).thenReturn(Optional.of(apikey));

        assertThatThrownBy(() -> sut.preHandle(request, null, getHandlerMethod("useClientKeyPair")))
                .isInstanceOf(ApiKeyExpiredException.class);
    }

    @Test
    @DisplayName("성공: 결제 관련 API 호출 시 Secret Key 인증에 성공한다.")
    void preHandleSuccessSecretKey() throws NoSuchMethodException {
        ApiKey apikey = apiKeyBuilder.expireDate(LocalDateTime.now().plusDays(1)).build();
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(secretKey);
        when(apiKeyRepository.findBySecretKey(any())).thenReturn(Optional.of(apikey));

        boolean isValid = sut.preHandle(request, null, getHandlerMethod("useSecretKeyPair"));

        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("성공: 결제 관련 API 호출 시 Client Key 인증에 성공한다.")
    void preHandleSuccessClientKey() throws NoSuchMethodException {
        ApiKey apikey = apiKeyBuilder.expireDate(LocalDateTime.now().plusDays(1)).build();
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn(clientKey);
        when(apiKeyRepository.findByClientKey(any())).thenReturn(Optional.of(apikey));

        boolean isValid = sut.preHandle(request, null, getHandlerMethod("useClientKeyPair"));

        assertThat(isValid).isTrue();
    }

    private HandlerMethod getHandlerMethod(String methodName) throws NoSuchMethodException {
        return new HandlerMethod(new TestHandler(), TestHandler.class.getMethod(methodName));
    }

    static class TestHandler {

        @ApiKeyPairType(type = KeyPairType.CLIENT_KEY)
        public void useClientKeyPair() {
        }

        @ApiKeyPairType(type = KeyPairType.SECRET_KEY)
        public void useSecretKeyPair() {
        }
    }
}
