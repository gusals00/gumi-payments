package flab.gumipayments.infrastructure.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiKeyDecoderTest {

    @Mock
    private HttpServletRequest request;

    private ApiKeyDecoder sut = new ApiKeyDecoder();

    @Test
    @DisplayName("성공: 헤더로 받은 ApiKey의 디코딩에 성공한다.")
    void decodeSuccess() {
        String apiKey = "sk_931019203_21390912_31223";
        String authorization = "Basic c2tfOTMxMDE5MjAzXzIxMzkwOTEyXzMxMjIzOg==";
        when(request.getHeader(any())).thenReturn(authorization);

        String decodedApiKey = sut.decodeApiKey(request);

        assertThat(decodedApiKey).isEqualTo(apiKey);
    }

    @Test
    @DisplayName("예외: 헤더로 받은 ApiKey의 형식이 올바르지 않으면 디코딩에 실패한다.")
    void apiKeyFormatException() {
        String authorization = "Bearer c2tfOTMxMDE5MjAzXzIxMzkwOTEyXzMxMjIzOg==";
        when(request.getHeader(any())).thenReturn(authorization);

        assertThatThrownBy(() -> sut.decodeApiKey(request))
                .isInstanceOf(ApiKeyFormatException.class);
    }

    @Test
    @DisplayName("예외: 헤더로 받은 ApiKey가 비어있다면 디코딩에 실패한다.")
    void apiKeyHasNoText() {
        String authorization = "";
        when(request.getHeader(any())).thenReturn(authorization);

        assertThatThrownBy(() -> sut.decodeApiKey(request))
                .isInstanceOf(ApiKeyFormatException.class);
    }
}