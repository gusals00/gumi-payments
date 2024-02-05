package flab.gumipayments.infrastructure.apikey;

import flab.gumipayments.domain.ApiKey;
import flab.gumipayments.domain.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final ApiKeyRepository apiKeyRepository;
    private final KeyEncrypt keyEncrypt;
    private final ApiKeyDecoder apiKeyDecoder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String decodedApiKey = apiKeyDecoder.decodeApiKey(request);
        validateKey(decodedApiKey, handler);

        return true;
    }

    private void validateKey(String decodedApiKey, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            ApiKeyPairType keyPairType = handlerMethod.getMethodAnnotation(ApiKeyPairType.class);

            ApiKey apiKey = getKey(decodedApiKey, keyPairType);

            if (apiKey.getExpireDate().isBefore(LocalDateTime.now())) { // 만료시간 < 현재시간 인 경우
                throw new ApiKeyExpiredException("만료된 ApiKey 입니다.");
            }
        }
    }

    private ApiKey getKey(String decodedApiKey, ApiKeyPairType keyPairType) {
        ApiKey apiKey = null;
        if (isClientKey(keyPairType)) {
            apiKey = apiKeyRepository.findByClientKey(keyEncrypt.encrypt(decodedApiKey))
                    .orElseThrow(() -> new ApiKeyNotFoundException("존재하지 않는 Client ApiKey 입니다."));
        } else {
            apiKey = apiKeyRepository.findBySecretKey(keyEncrypt.encrypt(decodedApiKey))
                    .orElseThrow(() -> new ApiKeyNotFoundException("존재하지 않는 Secret ApiKey 입니다."));
        }
        return apiKey;
    }

    private boolean isClientKey(ApiKeyPairType keyPairType) {
        return keyPairType.type() == KeyPairType.CLIENT_KEY;
    }
}


