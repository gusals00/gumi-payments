package flab.gumipayments.domain.apiKey;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApiKeyPairFactory {
    private static final String SECRETE_KEY_PREFIX = "sk_";
    private static final String CLIENT_KEY_PREFIX = "ck";

    public ApiKeyPair generateApiKeyPair() {
        return ApiKeyPair.builder()
                .secretKey(createApiKey(SECRETE_KEY_PREFIX))
                .clientKey(createApiKey(CLIENT_KEY_PREFIX))
                .build();
    }

    private String createApiKey(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }
}
