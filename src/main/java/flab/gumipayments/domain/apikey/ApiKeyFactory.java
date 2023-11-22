package flab.gumipayments.domain.apikey;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ApiKeyFactory {
    private final PasswordEncoder encoder;

    public ApiKey create(ApiKeyCreateCommand apiKeyCreateCommand) {
        return ApiKey.builder()
                .secretKey(encoder.encode(apiKeyCreateCommand.getSecretKey()))
                .accountId(apiKeyCreateCommand.getAccountId())
                .type(apiKeyCreateCommand.getApiKeyType())
                .expireDate(apiKeyCreateCommand.getExpireDate())
                .build();
    }
}
