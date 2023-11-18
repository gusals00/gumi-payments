package flab.gumipayments.domain.apikey;

import org.springframework.stereotype.Component;

import java.util.UUID;

import static flab.gumipayments.domain.apikey.ApiKeyPairPrefix.CLIENT_KEY_PREFIX;
import static flab.gumipayments.domain.apikey.ApiKeyPairPrefix.SECRET_KEY_PREFIX;

@Component
public class ApiKeyPairFactory {
    public ApiKeyPair generateApiKeyPair() {
        return ApiKeyPair.builder()
                .secretKey(createApiKey(SECRET_KEY_PREFIX))
                .clientKey(createApiKey(CLIENT_KEY_PREFIX))
                .build();
    }

    private String createApiKey(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }
}
