package flab.gumipayments.infrastructure.apikey;

import flab.gumipayments.domain.ApiKey;
import flab.gumipayments.domain.ApiKeyRepository;
import flab.gumipayments.domain.ApiKeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiKeyArgumentResolverTest {

    @InjectMocks
    private ApiKeyArgumentResolver sut;

    @Mock
    private ApiKeyRepository apiKeyRepository;
    @Mock
    private KeyEncrypt keyEncrypt;
    @Mock
    private ApiKeyDecoder apiKeyDecoder;

    private ApiKey.ApiKeyBuilder apiKeyBuilder;

    @BeforeEach
    void setup() {
        apiKeyBuilder = ApiKey.builder();
    }

    @Test
    @DisplayName("예외: method 파라미터 타입이 올바르지 않으면, API 키 인증이 실패한다.")
    void methodParameterFail() throws NoSuchMethodException {
        MethodParameter methodParameter1 = getMethodParameter("argumentResolverTest1", ApiKeyInfo.class);
        MethodParameter methodParameter2 = getMethodParameter("argumentResolverTest2", String.class);

        boolean notValidAnnotation = sut.supportsParameter(methodParameter1);
        boolean notValidParameterType = sut.supportsParameter(methodParameter2);

        assertThat(notValidAnnotation).isFalse();
        assertThat(notValidParameterType).isFalse();
    }

    @Test
    @DisplayName("예외: apiKey가 존재하지 않으면, API 키 인증에 실패한다.")
    void resolveFail() throws Exception {
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn("decodedKey");
        when(keyEncrypt.encrypt(any())).thenReturn("encryptedKey");
        when(apiKeyRepository.findBySecretKeyOrClientKey(any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.resolveArgument(null, null, getNativeWebRequestMock(), null))
                .isInstanceOf(ApiKeyNotFoundException.class);
    }

    @Test
    @DisplayName("성공: API 키 인증에 성공한다.")
    void success() throws Exception {
        ApiKey apikey = apiKeyBuilder
                .expireDate(LocalDateTime.now().plusDays(1))
                .type(ApiKeyType.PROD)
                .build();
        when(apiKeyDecoder.decodeApiKey(any())).thenReturn("decodedKey");
        when(keyEncrypt.encrypt(any())).thenReturn("encryptedKey");
        when(apiKeyRepository.findBySecretKeyOrClientKey(any(), any())).thenReturn(Optional.of(apikey));

        boolean isSupportsParameter = sut.supportsParameter(getMethodParameter("argumentResolverTest3", ApiKeyInfo.class));
        ApiKeyInfo apiKeyInfo = (ApiKeyInfo) sut.resolveArgument(null, null, getNativeWebRequestMock(), null);

        assertThat(isSupportsParameter).isTrue();
        assertThat(apiKeyInfo.getApiKeyId()).isEqualTo(apikey.getId());
        assertThat(apiKeyInfo.getType()).isEqualTo(apikey.getType());
    }

    private MethodParameter getMethodParameter(String methodName, Class<?> parameter) throws NoSuchMethodException {
        HandlerMethod handlerMethod = new HandlerMethod(new TestHandler(), TestHandler.class.getMethod(methodName, parameter));
        MethodParameter methodParameter = handlerMethod.getMethodParameters()[0];
        return methodParameter;
    }

    private NativeWebRequest getNativeWebRequestMock() {
        return new ServletWebRequest(new MockHttpServletRequest());
    }

    static class TestHandler {

        public void argumentResolverTest1(ApiKeyInfo keyInfo) {
        }

        public void argumentResolverTest2(@AuthenticatedApiKey String key) {
        }

        public void argumentResolverTest3(@AuthenticatedApiKey ApiKeyInfo keyInfo) {
        }

    }
}