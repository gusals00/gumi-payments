package flab.gumipayments.domain.apikey;

import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static flab.gumipayments.domain.apikey.ApiKeyPairPrefix.CLIENT_KEY_PREFIX;
import static flab.gumipayments.domain.apikey.ApiKeyPairPrefix.SECRET_KEY_PREFIX;

@Component
@RequiredArgsConstructor
public class ApiKeyFactory {
    private final PasswordEncoder encoder;

    public ApiKeyResponse create(ApiKeyCreateCommand command) {
        // 시크릿, 클라이언트 키 생성
        ApiKeyPair apiKeyPair = generateApiKeyPair();

        // api 키 생성
        ApiKey apiKey = create(command, apiKeyPair);

        return new ApiKeyResponse(apiKeyPair, apiKey);
    }

    private ApiKeyPair generateApiKeyPair() {
        return ApiKeyPair.builder()
                .secretKey(createApiKey(SECRET_KEY_PREFIX))
                .clientKey(createApiKey(CLIENT_KEY_PREFIX))
                .build();
    }

    private String createApiKey(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }


    private ApiKey create(ApiKeyCreateCommand issueCommand, ApiKeyPair keyPair) {
        return ApiKey.builder()
                .secretKey(encoder.encode(keyPair.getSecretKey()))
                .accountId(issueCommand.getAccountId())
                .type(issueCommand.getApiKeyType())
                .expireDate(issueCommand.getExpireDate())
                .build();
    }
}
